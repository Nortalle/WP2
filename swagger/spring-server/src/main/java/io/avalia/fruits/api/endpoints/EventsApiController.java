package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.EventsApi;
import io.avalia.fruits.api.model.Event;
import org.springframework.http.ResponseEntity;

public class EventsApiController implements EventsApi {
    @Override
    public ResponseEntity<Void> createEvent(String authorization, Event body) {
        return null;
    }
}
