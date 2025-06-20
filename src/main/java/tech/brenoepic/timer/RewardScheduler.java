package tech.brenoepic.timer;

import tech.brenoepic.Main;
import tech.brenoepic.javascript.communication.outgoing.common.RewardAvailableComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import tech.brenoepic.rewards.RewardItem;
import com.eu.habbo.Emulator;
import com.eu.habbo.core.Scheduler;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.map.hash.THashMap;

public class RewardScheduler extends Scheduler {
    private boolean ignoreHotelView;
    private boolean ignoreIdled;

    public RewardScheduler() {
        super(Emulator.getConfig().getInt("hotel.auto.rewards.interval"));
        this.reloadConfig();
    }

    public void reloadConfig() {
        if (Emulator.getConfig().getBoolean("hotel.auto.rewards.enabled", true)) {
            interval = Emulator.getConfig().getInt("hotel.auto.rewards.interval");
            ignoreHotelView = Emulator.getConfig().getBoolean("hotel.auto.rewards.ignore.hotelview");
            ignoreIdled = Emulator.getConfig().getBoolean("hotel.auto.rewards.ignore.idled");
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
        reloadConfig();

        THashMap<Integer, Habbo> canClaim = new THashMap<>();
        Main.getRewardsManager().updateReward();
        RewardItem rewardItem = Main.getRewardsManager().getCurrentReward();
        if(rewardItem == null) return;

        Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().forEach(((integer, habbo) -> {
            if (alertUser(habbo, rewardItem)) canClaim.put(integer, habbo);
        }));

        Main.getRewardsManager().updateClaim(canClaim);

        super.run();
    }

    private boolean alertUser(Habbo habbo, RewardItem item) {
        if (habbo == null || (habbo.getHabboInfo().getCurrentRoom() == null && ignoreHotelView) || (habbo.getRoomUnit().isIdle() && ignoreIdled))
            return false;

        if(item.getBadge().length() > 1 && habbo.getInventory().getBadgesComponent().hasBadge(item.getBadge())) return false;

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
