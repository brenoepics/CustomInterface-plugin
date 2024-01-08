package tech.brenoepic.rarevalue;

import tech.brenoepic.javascript.communication.outgoing.common.RareValueBuilder;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import gnu.trove.set.hash.THashSet;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
@Slf4j
public class RareValueManager {
    private final HashMap<Integer, Rare> rares = new HashMap<>();
    private final HashMap<Integer, RareCategory> categories = new HashMap<>();
    protected String categoriesSQL = Emulator.getConfig().getValue("rarevalue.categories_sql");
    protected String raresSQL = Emulator.getConfig().getValue("rarevalue.rares_sql");
    private String frontPage = Emulator.getTexts().getValue("rarevalue.frontpage.text");

    public RareValueManager() {
        reloadCategories();
        reloadRares();
    }


    public void reload() {
        categoriesSQL = Emulator.getConfig().getValue("rarevalue.categories_sql");
        raresSQL = Emulator.getConfig().getValue("rarevalue.rares_sql");
        frontPage = Emulator.getTexts().getValue("rarevalue.frontpage.text");
        reloadCategories();
        reloadRares();
    }

    private void reloadCategories() {
        if (categoriesSQL.isEmpty()) {
            log.error("[Rare-Value] Categories SQL is empty");
            return;
        }

        this.categories.clear();
        try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection(); final PreparedStatement statement = connection.prepareStatement(categoriesSQL); final ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                final RareCategory category = new RareCategory(set.getInt("id"), set.getString("name"));
                this.categories.putIfAbsent(category.id(), category);
            }
        } catch (SQLException e) {
            log.error("[Rare-Value] Error while loading categories", e);
        }
    }

    private void reloadRares() {
        if (raresSQL.isEmpty()) {
            log.error("[Rare-Value] Rares SQL is empty");
            return;
        }

        this.rares.clear();
        try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection(); final PreparedStatement statement = connection.prepareStatement(raresSQL); final ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                final Rare rare = loadRare(set);
                if (rare == null) continue;
                this.rares.putIfAbsent(rare.getId(), rare);
            }
        } catch (SQLException e) {
            log.error("[Rare-Value] Error while loading rares", e);
        }
    }

    private Rare loadRare(ResultSet set) {
        try {
            final RareCost cost = new RareCost(set.getInt("credits"), set.getInt("points_type"), set.getInt("points"));
            return new Rare(set.getInt("id"), set.getInt("item_id"), set.getInt("category"), cost);
        } catch (NullPointerException | SQLException e) {
            log.error("[Rare-Value] Error while loading rares", e);
        }

        return null;
    }

    public Collection<Rare> getRares() {
        return rares.values();
    }

    public Collection<Integer> getHabboRares(Habbo habbo) {
        THashSet<HabboItem> habboItems = habbo.getInventory().getItemsComponent().getItemsAsValueCollection();
        Collection<Integer> rareIds = new ArrayList<>();
        for(HabboItem item : habboItems) {
            Rare rare = getRare(item);
            if(rare != null) {
                rareIds.add(rare.getId());
            }
        }

        return rareIds;
    }

    public RareValueBuilder getBuilder(Habbo habbo) {
        return new RareValueBuilder(getFrontPage()).setRares(getRares()).setCategories(getCategories()).setMyRares(getHabboRares(habbo));
    }

    public String getFrontPage() {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.STYLES).and(Sanitizers.BLOCKS);
        return policy.sanitize(this.frontPage);
    }

    public Rare getRare(HabboItem item) {
        return this.rares.values().stream().filter(rare -> rare.getItemId() == item.getBaseItem().getId()).findAny().orElse(null);
    }

    public Collection<RareCategory> getCategories() {
        return categories.values();
    }
}
