package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.PointScalesApi;
import io.avalia.fruits.api.model.PointScaleRead;
import io.avalia.fruits.api.model.PointScaleWrite;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PointScalesApiController implements PointScalesApi {
    @Override
    public ResponseEntity<String> createPointScale(String authorization, PointScaleWrite body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePointScale(String pointScaleId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<PointScaleRead> getPointScale(String pointScaleId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<List<PointScaleRead>> getPointScales(String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updatePointScale(String pointScaleId, String authorization, PointScaleWrite body) {
        return null;
    }
}
