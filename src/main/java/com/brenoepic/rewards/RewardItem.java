package com.brenoepic.rewards;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.Achievement;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.inventory.AddHabboItemComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Getter
public class RewardItem {
    private final int id;
    private final int chance;
    private final int itemId;
    private final int credits;
    private final int points;
    private final int pointsType;
    private final int duckets;
    private final String badge;

    public RewardItem(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.chance = result.getInt("chance");
        this.itemId = result.getInt("item_id");
        this.credits = result.getInt("credits");
        this.points = result.getInt("points");
        this.pointsType = result.getInt("points_type");
        this.duckets = result.getInt("duckets");
        this.badge = result.getString("badge");
    }

    public String getMessage() {
        if(this.itemId > 0) {
            Item item = Emulator.getGameEnvironment().getItemManager().getItem(this.itemId);
            if (item != null) {
                return Emulator.getTexts().getValue("timed_rewards.reward_message_item").replace("%item_name%", item.getName());
            }
        }

        if (this.points > 0) {
           return replaceVariable("points", this.points);
        }

        if (this.duckets > 0) {
            return replaceVariable("duckets", this.duckets);
        }

        if (this.credits > 0) {
            return replaceVariable("credits", this.credits);
        }

        if (!this.badge.isEmpty()) {
            return Emulator.getTexts().getValue("timed_rewards.reward_message_badge").replace("%badge%", this.badge);
        }

        return Emulator.getTexts().getValue("timed_rewards.reward_message");
    }

    private String replaceVariable(String type, int value){
        return Emulator.getTexts().getValue("timed_rewards.reward_message_" + type, "You have an available reward!")
                .replace("%amount%", String.valueOf(value));
    }

    public void give(Habbo habbo) {
        if (this.credits > 0) habbo.giveCredits(this.credits);
        if (this.points > 0) habbo.givePoints(this.pointsType, this.points);
        if (this.duckets > 0) habbo.givePixels(this.duckets);
        if (!this.badge.isEmpty()) habbo.addBadge(this.badge);
        Achievement achievement = Emulator.getGameEnvironment().getAchievementManager().getAchievement("timed_reward");
        if (achievement != null) {
            AchievementManager.progressAchievement(habbo, achievement, 1);
        }
        giveItem(habbo);
    }

    private void giveItem(Habbo habbo) {
        if (this.itemId < 1) return;

        Item rewardItem = Emulator.getGameEnvironment().getItemManager().getItem(this.itemId);
        if (rewardItem == null) {
            log.error("Could not find item with id {}", this.itemId);
            return;
        }

        HabboItem newItem = Emulator.getGameEnvironment().getItemManager().createItem(habbo.getHabboInfo().getId(), rewardItem, 0, 0, "");
        if (newItem != null) {
            habbo.getInventory().getItemsComponent().addItem(newItem);
            habbo.getClient().sendResponse(new AddHabboItemComposer(newItem));
            habbo.getClient().sendResponse(new InventoryRefreshComposer());
        }
    }

}
