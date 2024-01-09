package tech.brenoepic;

import tech.brenoepic.commands.ReloadRareValueCommand;
import tech.brenoepic.commands.ReloadRewardsCommand;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.commands.CmdCommand;
import tech.brenoepic.commands.RareValueCommand;
import tech.brenoepic.javascript.commands.YoutubeCommand;
import tech.brenoepic.javascript.communication.outgoing.audio.DisposePlaylistComposer;
import tech.brenoepic.javascript.communication.outgoing.audio.PlaySongComposer;
import tech.brenoepic.javascript.communication.outgoing.audio.PlayStopComposer;
import tech.brenoepic.javascript.communication.outgoing.audio.PlaylistComposer;
import tech.brenoepic.javascript.communication.outgoing.common.SessionDataComposer;
import tech.brenoepic.javascript.communication.outgoing.common.UpdateCreditsComposer;
import tech.brenoepic.javascript.interactions.InteractionSlotMachine;
import tech.brenoepic.javascript.interactions.InteractionYoutubeJukebox;
import tech.brenoepic.javascript.override_packets.incoming.JavascriptCallbackEvent;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import tech.brenoepic.rarevalue.RareValueManager;
import tech.brenoepic.rewards.RewardsManager;
import tech.brenoepic.timer.RewardScheduler;
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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class Main extends HabboPlugin implements EventListener {
    public static final int JS_CALLBACK_EVENT_HEADER = 314;
    @Getter
    @Setter
    private static RewardsManager rewardsManager;

    @Setter
    @Getter
    private static RewardScheduler rewardScheduler;

    @Getter
    @Setter
    private static RareValueManager rareValueManager;

    public void onEnable() {
        Emulator.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        rewardsManager.dispose();
        log.info("[Custom-Interface 1.0] was successfully Unloaded!");
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) throws Exception {
        registerTexts();
        registerConfig();
        registerCommands();
        initializeFeatures();

        log.info("[Custom-Interface 1.0] was successfully Loaded! Discord: brenoepic");
    }

    private void initializeFeatures() throws Exception {
        JSPlugin.init();
        setRewardsManager(new RewardsManager());
        setRewardScheduler(new RewardScheduler());
        setRareValueManager(new RareValueManager());
        Emulator.getThreading().run(rewardScheduler);
        registerPackets();
    }

    private void registerPackets() throws Exception {
        Emulator.getGameServer().getPacketManager().registerHandler(JS_CALLBACK_EVENT_HEADER, JavascriptCallbackEvent.class);
    }

    private void registerConfig() {
        Emulator.getConfig().register("javascript.cmd.commands.enabled", "0");
        Emulator.getConfig().register("hotel.auto.rewards.max_claimable", "60");
        Emulator.getConfig().register("hotel.auto.rewards.enabled", "1");
        Emulator.getConfig().register("hotel.auto.rewards.interval", "600");
        Emulator.getConfig().register("hotel.auto.rewards.ignore.hotelview", "0");
        Emulator.getConfig().register("hotel.auto.rewards.ignore.idled", "0");
        Emulator.getConfig().register("hotel.auto.rewards.hc_modifier", "1.0");
        Emulator.getConfig().register("rarevalue.categories_sql", "");
        Emulator.getConfig().register("rarevalue.rares_sql", "");
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
        Emulator.getTexts().register("commands.keys.cmd_reload_rare_value", "reload_rare_value;update_rare_value;update_rarevalue");
        Emulator.getTexts().register("rarevalue.frontpage.text", "Welcome to the Rare Value! Quickly check the value of your rares!");
    }

    private void registerCommands() {
        CommandHandler.addCommand(new YoutubeCommand());
        CommandHandler.addCommand(new ReloadRewardsCommand("cmd_reload_timed_rewards", Emulator.getTexts().getValue("commands.keys.cmd_reload_timed_rewards").split(";")));
        CommandHandler.addCommand(new RareValueCommand("cmd_rare_value", Emulator.getTexts().getValue("commands.keys.cmd_rare_value").split(";")));
        CommandHandler.addCommand(new ReloadRareValueCommand("cmd_reload_rare_value", Emulator.getTexts().getValue("commands.keys.cmd_reload_rare_value").split(";")));

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
