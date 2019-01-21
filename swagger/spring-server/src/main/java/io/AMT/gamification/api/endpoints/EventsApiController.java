package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.EventsApi;
import io.AMT.gamification.api.model.Event;
import org.springframework.http.ResponseEntity;

public class EventsApiController implements EventsApi {
    @Override
    public ResponseEntity<Void> createEvent(String authorization, Event body) {
        return null;
    }
}
