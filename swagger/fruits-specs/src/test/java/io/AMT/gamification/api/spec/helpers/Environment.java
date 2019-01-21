package io.AMT.gamification.api.spec.helpers;

import io.AMT.gamification.api.BadgesApi;


import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private BadgesApi badgeApi = new BadgesApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.AMT.gamification.server.url");
        badgeApi.getApiClient().setBasePath(url);

    }

    public BadgesApi getBadgesApi() {
        return badgeApi;
    }


}
