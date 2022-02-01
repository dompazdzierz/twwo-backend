package pl.pwr.dissertation.config;

import java.util.HashMap;
import java.util.Map;

public class DataIntegrityExceptions {

    public static final String USER_UNIQUE_USERNAME = "user_unique_username";

    public static Map<String, String> MESSAGES;

    static {
        MESSAGES = new HashMap<>();
        MESSAGES.put(USER_UNIQUE_USERNAME, "Duplicated username value");
    }

}

