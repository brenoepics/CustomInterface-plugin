package com.brenoepic.timer;

import com.brenoepic.TimedRewards;
import com.brenoepic.javascript.communication.outgoing.common.RewardAvailableComposer;
import com.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import com.brenoepic.rewards.RewardItem;
import com.eu.habbo.Emulator;
import com.eu.habbo.core.Scheduler;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.map.hash.THashMap;

import java.sql.Time;

public class RewardScheduler extends Scheduler {

    private boolean ignoreHotelView;
    private boolean ignoreIdled;
    private double hcModifier;

    public RewardScheduler() {
        super(Emulator.getConfig().getInt("hotel.auto.rewards.interval"));
        this.reloadConfig();
    }

    public void reloadConfig() {
        if (Emulator.getConfig().getBoolean("hotel.auto.rewards.enabled", true)) {
            ignoreHotelView = Emulator.getConfig().getBoolean("hotel.auto.rewards.ignore.hotelview");
            ignoreIdled = Emulator.getConfig().getBoolean("hotel.auto.rewards.ignore.idled");
            hcModifier = Emulator.getConfig().getDouble("hotel.auto.rewards.hc_modifier", 1.0);

            if (this.disposed) {
                this.disposed = false;
                this.run();
            }
        } else {
            this.disposed = true;
        }
    }

    @Override
    public void run() {
        super.run();
        THashMap<Integer, Habbo> canClaim = new THashMap<>();
        TimedRewards.getRewardsManager().updateReward();
        RewardItem rewardItem = TimedRewards.getRewardsManager().getCurrentReward();
        if(rewardItem == null) return;

        Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().forEach(((integer, habbo) -> {
            if (alertUser(habbo, rewardItem)) canClaim.put(integer, habbo);
        }));

        TimedRewards.getRewardsManager().updateClaim(canClaim);

    }

    private boolean alertUser(Habbo habbo, RewardItem item) {
        if (habbo == null || (habbo.getHabboInfo().getCurrentRoom() == null && ignoreHotelView) || (habbo.getRoomUnit().isIdle() && ignoreIdled))
            return false;

        RewardAvailableComposer composer = new RewardAvailableComposer(Emulator.getConfig().getInt("hotel.auto.rewards.max_claimable", 60), item.getMessage());
        habbo.getClient().sendResponse(new JavascriptCallbackComposer(composer));
        return true;
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override
    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }
}
