package io.AMT.gamification.api.spec.helpers;

import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.RulesApi;


import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private BadgesApi badgesApi = new BadgesApi();
    private PointScalesApi pointScalesApi = new PointScalesApi();

    private RulesApi rulesApi = new RulesApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.AMT.gamification.server.url");
        badgesApi.getApiClient().setBasePath(url);
        pointScalesApi.getApiClient().setBasePath(url);
        rulesApi.getApiClient().setBasePath(url);


    }

    public BadgesApi getBadgesApi() {
        return badgesApi;
    }

    public PointScalesApi getPointScalesApi() {
        return pointScalesApi;
    }

    public RulesApi getRulesApi() {
        return rulesApi;
    }

}
