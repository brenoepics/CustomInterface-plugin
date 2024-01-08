package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.rarevalue.Rare;
import tech.brenoepic.rarevalue.RareCategory;

import java.util.ArrayList;
import java.util.Collection;

public class RareValueBuilder {
    private Collection<Rare> rares = new ArrayList<>();
    private Collection<RareCategory> categories = new ArrayList<>();
    private Collection<Integer> myRares = new ArrayList<>();
    private String frontPage = "";

    public RareValueBuilder(String frontPage) {
        this.frontPage = frontPage;
    }
    public RareValueBuilder() {
    }

    public RareValueBuilder setRares(Collection<Rare> rares) {
        this.rares = rares;
        return this;
    }

    public RareValueBuilder setCategories(Collection<RareCategory> categories) {
        this.categories = categories;
        return this;
    }

    public RareValueBuilder setMyRares(Collection<Integer> myRares) {
        this.myRares = myRares;
        return this;
    }

    public RareValueBuilder setFrontPage(String frontPage) {
        this.frontPage = frontPage;
        return this;
    }

    public RareValueComposer build() {
        return new RareValueComposer(frontPage, rares, categories, myRares);
    }
}