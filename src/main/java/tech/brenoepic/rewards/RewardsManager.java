package tech.brenoepic.rewards;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.map.hash.THashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@Setter
public class RewardsManager {
    private final List<RewardItem> rewards = new ArrayList<>();
    private THashMap<Integer, Habbo> canClaim = new THashMap<>();
    private RewardItem currentReward;
    private Date maxClaimableDate = new Date();

    public RewardsManager() {
        this.load();
    }

    public boolean load() {
        this.dispose();

        try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection(); final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `items_timed_rewards`"); final ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                final RewardItem item = new RewardItem(set);
                this.rewards.add(item);
            }
        } catch (SQLException e) {
            log.error("[Timed-Rewards]", e);
            return false;
        }

        log.info("[Timed-Rewards] Loaded {} rewards successfully!", this.rewards.size());
        return true;
    }

    public void claimReward(Habbo habbo, boolean wantReward) {
        if (!this.canClaim.containsKey(habbo.getHabboInfo().getId())) {
            return;
        }

        if (!wantReward) {
            this.canClaim.remove(habbo.getHabboInfo().getId());
            return;
        }

        if (this.maxClaimableDate.before(new Date()) || this.currentReward == null) {
            habbo.alert(Emulator.getTexts().getValue("timed_rewards.reward_expired"));
            this.canClaim.remove(habbo.getHabboInfo().getId());
            return;
        }


        this.currentReward.give(habbo);
        this.canClaim.remove(habbo.getHabboInfo().getId());
    }

    public void updateReward() {
        RewardItem reward = this.getRandReward();
        if (reward == null) return;

        this.setCurrentReward(reward);
    }

    public void updateClaim(THashMap<Integer, Habbo> users) {
        canClaim.clear();
        this.setCanClaim(users);
        this.setMaxClaimableDate(new java.util.Date(System.currentTimeMillis() + (Emulator.getConfig().getInt("hotel.auto.rewards.max_claimable", 60) * 1000L)));
    }

    public RewardItem getRandReward() {
        AtomicInteger totalChance = new AtomicInteger();
        Map<RewardItem, Map.Entry<Integer, Integer>> prizes = new HashMap<>();

        this.rewards.forEach(reward -> {
            prizes.put(reward, new AbstractMap.SimpleEntry<>(totalChance.get(), totalChance.get() + reward.getChance()));
            totalChance.addAndGet(reward.getChance());
        });

        int random = Emulator.getRandom().nextInt(totalChance.get());

        RewardItem notFound = null;
        for (Map.Entry<RewardItem, Map.Entry<Integer, Integer>> set : prizes.entrySet()) {
            notFound = set.getKey();
            if (random >= set.getValue().getKey() && random < set.getValue().getValue()) {
                return set.getKey();
            }
        }

        return notFound;
    }

    public void dispose() {
        this.rewards.clear();
    }
}
