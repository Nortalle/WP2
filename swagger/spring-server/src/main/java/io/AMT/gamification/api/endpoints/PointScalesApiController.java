package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.model.PointScaleRead;
import io.AMT.gamification.api.model.PointScaleWrite;
import io.AMT.gamification.entities.PointScaleEntity;
import io.AMT.gamification.repositories.PointScaleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.net.URI;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Override
    public ResponseEntity<String> createPointScale(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                                   @ApiParam(value = "pointScale to create"  ) @RequestBody PointScaleWrite body){

        PointScaleEntity pointScaleEntity = toPointScaleEntity(body, authorization);

        pointScaleRepository.save(pointScaleEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(pointScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deletePointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                 @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){

        pointScaleRepository.delete(pointScaleId);

        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<PointScaleRead> getPointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                        @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        PointScaleEntity badgeEntity = pointScaleRepository.findOne(pointScaleId);

        PointScaleRead pointScaleRead = toPointScaleRead(badgeEntity);

        return ResponseEntity.ok(pointScaleRead);
    }

    @Override
    public ResponseEntity<List<PointScaleRead>> getPointScales(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        List<PointScaleRead> pointScaleReads = new ArrayList<>();

        for(PointScaleEntity pointScaleEntity : pointScaleRepository.findAllByApiKey(authorization)){
            pointScaleReads.add(toPointScaleRead(pointScaleEntity));
        }
        return ResponseEntity.ok(pointScaleReads);
    }

    @Override
    public ResponseEntity<Void> updatePointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                 @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                                 @ApiParam(value = "point scale that needs to be update in the store"  ) @RequestBody PointScaleWrite body){

        PointScaleEntity pointScaleEntity = toPointScaleEntity(body, authorization, pointScaleId);

        pointScaleRepository.save(pointScaleEntity);

        return ResponseEntity.ok().build();
    }

    private PointScaleEntity toPointScaleEntity(PointScaleWrite badge, String apiKey, Long pointScaleId){
        PointScaleEntity pointScaleEntity = toPointScaleEntity(badge, apiKey);
        pointScaleEntity.setId(pointScaleId);
        return pointScaleEntity;
    }

    private PointScaleEntity toPointScaleEntity(PointScaleWrite pointScaleWrite, String apiKey){
        PointScaleEntity pointScaleEntity = new PointScaleEntity();
        pointScaleEntity.setName(pointScaleWrite.getName());
        pointScaleEntity.setDescription(pointScaleWrite.getDescription());
        pointScaleEntity.setApiKey(apiKey);

        return pointScaleEntity;
    }

    private PointScaleRead toPointScaleRead(PointScaleEntity pointScaleEntity){
        PointScaleRead pointScaleRead = new PointScaleRead();
        pointScaleRead.setName(pointScaleEntity.getName());
        pointScaleRead.setDescription(pointScaleEntity.getDescription());
        pointScaleRead.setId(pointScaleEntity.getId());

        return pointScaleRead;
    }
}