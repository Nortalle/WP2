package io.AMT.gamification.api.spec.helpers;

import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.RulesApi;
import io.AMT.gamification.api.UsersApi;
import io.AMT.gamification.api.EventsApi;


import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private BadgesApi badgesApi = new BadgesApi();
    private PointScalesApi pointScalesApi = new PointScalesApi();
    private RulesApi rulesApi = new RulesApi();
    private UsersApi usersApi = new UsersApi();
    private EventsApi eventsApi = new EventsApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.AMT.gamification.server.url");
        badgesApi.getApiClient().setBasePath(url);
        pointScalesApi.getApiClient().setBasePath(url);
        rulesApi.getApiClient().setBasePath(url);
        usersApi.getApiClient().setBasePath(url);
        eventsApi.getApiClient().setBasePath(url);

    }

    public BadgesApi getBadgesApi() {
        return badgesApi;
    }

    public PointScalesApi getPointScalesApi() {
        return pointScalesApi;
    }

    public RulesApi getRuleApi() {
        return rulesApi;
    }

    public UsersApi getUsersApi() {
        return usersApi;
    }

    public void setUsersApi(UsersApi usersApi) {
        this.usersApi = usersApi;
    }

    public EventsApi getEventsApi() {
        return eventsApi;
    }

    public void setEventsApi(EventsApi eventsApi) {
        this.eventsApi = eventsApi;
    }
}
