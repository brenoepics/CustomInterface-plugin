package tech.brenoepic.javascript.utils;

import com.google.gson.Gson;

public class JsonFactory {
    private static final Gson gson = new Gson();

    public static Gson getInstance() {
        return gson;
    }
}
