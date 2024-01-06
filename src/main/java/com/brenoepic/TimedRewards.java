package com.brenoepic;

import com.brenoepic.commands.ReloadRewardsCommand;
import com.brenoepic.javascript.JSPlugin;
import com.brenoepic.javascript.audio.RoomPlaylist;
import com.brenoepic.javascript.commands.CmdCommand;
import com.brenoepic.javascript.commands.RareValueCommand;
import com.brenoepic.javascript.commands.YoutubeCommand;
import com.brenoepic.javascript.communication.outgoing.audio.DisposePlaylistComposer;
import com.brenoepic.javascript.communication.outgoing.audio.PlaySongComposer;
import com.brenoepic.javascript.communication.outgoing.audio.PlayStopComposer;
import com.brenoepic.javascript.communication.outgoing.audio.PlaylistComposer;
import com.brenoepic.javascript.communication.outgoing.common.SessionDataComposer;
import com.brenoepic.javascript.communication.outgoing.common.UpdateCreditsComposer;
import com.brenoepic.javascript.interactions.InteractionSlotMachine;
import com.brenoepic.javascript.interactions.InteractionYoutubeJukebox;
import com.brenoepic.javascript.override_packets.incoming.JavascriptCallbackEvent;
import com.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import com.brenoepic.rarevalue.RareValueManager;
import com.brenoepic.rewards.RewardsManager;
import com.brenoepic.timer.RewardScheduler;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.items.ItemInteraction;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.rooms.RoomUncachedEvent;
import com.eu.habbo.plugin.events.users.UserCreditsEvent;
import com.eu.habbo.plugin.events.users.UserEnterRoomEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class TimedRewards extends HabboPlugin implements EventListener {
    public static final int JS_CALLBACK_EVENT_HEADER = 314;
    @Getter
    public static final RewardsManager rewardsManager = new RewardsManager();

    @Getter
    public static final RewardScheduler rewardScheduler = new RewardScheduler();

    @Getter
    public static final RareValueManager rareValueManager = new RareValueManager();
    public void onEnable() {
        Emulator.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        rewardsManager.dispose();
        log.info("[Timed-Rewards 1.0] was successfully Unloaded!");
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) throws Exception {
        Emulator.getGameServer().getPacketManager().registerHandler(JS_CALLBACK_EVENT_HEADER, JavascriptCallbackEvent.class);
        registerTexts();
        registerConfig();
        registerCommands();
        Emulator.getThreading().run(rewardScheduler);
        JSPlugin.init();

        log.info("[Timed-Rewards 1.0] was successfully Loaded! Discord: brenoepic");
    }

    private void registerConfig() {
        Emulator.getConfig().register("javascript.cmd.commands.enabled", "0");
        Emulator.getConfig().register("hotel.auto.rewards.max_claimable", "60");
        Emulator.getConfig().register("hotel.auto.rewards.enabled", "1");
        Emulator.getConfig().register("hotel.auto.rewards.interval", "600");
        Emulator.getConfig().register("hotel.auto.rewards.ignore.hotelview", "0");
        Emulator.getConfig().register("hotel.auto.rewards.ignore.idled", "0");
        Emulator.getConfig().register("hotel.auto.rewards.hc_modifier", "1.0");
    }

    private void registerTexts() {
        Emulator.getTexts().register("javascript.cmd.youtube.keys", "roomvideo;youtube");
        Emulator.getTexts().register("javascript.cmd.youtube.usage", "Usage: :roomvideo youtube.com/watch?v=videoId");
        Emulator.getTexts().register("javascript.cmd.youtube.invalid", "Invalid youtube video url");
        Emulator.getTexts().register("commands.description.cmd_youtube", ":youtube <VideoUrl>");
        Emulator.getTexts().register("commands.cmd_reload_timed_rewards.successfully", "Successfully updated the rewards!");
        Emulator.getTexts().register("commands.cmd_reload_timed_rewards.error", "Error updating the rewards!");
        Emulator.getTexts().register("commands.keys.cmd_reload_timed_rewards", "update_rewards;reloadrewards");
        Emulator.getTexts().register("commands.description.cmd_reload_timed_rewards", ":update_rewards");
        Emulator.getTexts().register("timed_rewards.reward_expired", "This reward has expired!");
        Emulator.getTexts().register("timed_rewards.reward_message_points", "You have %amount% points available!");
        Emulator.getTexts().register("timed_rewards.reward_message_duckets", "You have %amount% duckets available!");
        Emulator.getTexts().register("timed_rewards.reward_message_credits", "You have %amount% credits available!");
        Emulator.getTexts().register("timed_rewards.reward_message_badge", "You have the badge %badge% available!");
        Emulator.getTexts().register("timed_rewards.reward_message_item", "You have the item %item_name% available!");
        Emulator.getTexts().register("timed_rewards.reward_message", "You have an available reward!");
        Emulator.getTexts().register("commands.keys.cmd_rare_value", "rare_value");
    }

    private void registerCommands() {
        CommandHandler.addCommand(new YoutubeCommand());
        CommandHandler.addCommand(new ReloadRewardsCommand("cmd_reload_timed_rewards", Emulator.getTexts().getValue("commands.keys.cmd_reload_timed_rewards").split(";")));
        CommandHandler.addCommand(new RareValueCommand("cmd_rare_value", Emulator.getTexts().getValue("commands.keys.cmd_rare_value").split(";")));
        if (Emulator.getConfig().getBoolean("javascript.cmd.commands.enabled", true)) {
            CommandHandler.addCommand(new CmdCommand());
        }
    }

    @EventHandler
    public void onLoadItemsManager(EmulatorLoadItemsManagerEvent e) {
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("slots_machine", InteractionSlotMachine.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("yt_jukebox", InteractionYoutubeJukebox.class));
    }

    @EventHandler
    public void onUserEnterRoomEvent(UserEnterRoomEvent e) {
        RoomPlaylist playlist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(e.room.getId());
        // here send the playlist to the user
        if (!playlist.getPlaylist().isEmpty()) {
            e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlaylistComposer(playlist)));
            if (playlist.isPlaying()) {
                e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlaySongComposer(playlist.getCurrentIndex())));
                e.habbo.getClient().sendResponse(playlist.getNowPlayingBubbleAlert());
            }
        }
    }

    @EventHandler
    public void onUserExitRoomEvent(UserExitRoomEvent e) {
        // here empty the playlist
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new PlayStopComposer(false)));
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(new DisposePlaylistComposer()));
    }

    @EventHandler
    public void onRoomUncachedEvent(RoomUncachedEvent e) {
        JSPlugin.getInstance().getRoomAudioManager().dispose(e.room.getId());
    }

    @EventHandler
    public void onUserLoginEvent(UserLoginEvent e) {
        SessionDataComposer sessionDataComposer = new SessionDataComposer(e.habbo.getHabboInfo().getId(), e.habbo.getHabboInfo().getUsername(), e.habbo.getHabboInfo().getLook(), e.habbo.getHabboInfo().getCredits());
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(sessionDataComposer));
    }

    @EventHandler
    public void onUserCreditsEvent(UserCreditsEvent e) {
        UpdateCreditsComposer creditsComposer = new UpdateCreditsComposer(e.habbo.getHabboInfo().getCredits());
        e.habbo.getClient().sendResponse(new JavascriptCallbackComposer(creditsComposer));
    }

    public static void main(String[] args) {
        log.error("This plugin cannot be run as a standalone application.");
    }

}
