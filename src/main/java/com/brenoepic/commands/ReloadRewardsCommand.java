package com.brenoepic.commands;

import com.brenoepic.TimedRewards;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class ReloadRewardsCommand extends Command {

    public ReloadRewardsCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    private static final String SUCCESS = "commands.cmd_reload_timed_rewards.successfully";
    private static final String ERROR = "commands.cmd_reload_timed_rewards.error";

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        boolean loaded = TimedRewards.getRewardsManager().load();
        gameClient.getHabbo().whisper(Emulator.getTexts().getValue(loaded ? SUCCESS : ERROR));
        return true;
    }
}
