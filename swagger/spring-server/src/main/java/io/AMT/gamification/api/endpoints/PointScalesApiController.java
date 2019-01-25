package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.model.PointScaleRead;
import io.AMT.gamification.api.model.PointScaleWrite;
import io.AMT.gamification.api.services.ConverterService;
import io.AMT.gamification.entities.BadgeEntity;
import io.AMT.gamification.entities.PointScaleEntity;
import io.AMT.gamification.repositories.BadgeAwardRepository;
import io.AMT.gamification.repositories.PointScaleAwardRepository;
import io.AMT.gamification.repositories.PointScaleRepository;
import io.AMT.gamification.repositories.RulesRepository;
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

    @Autowired
    RulesRepository rulesRepository;

    @Autowired
    PointScaleAwardRepository pointScaleAwardRepository;

    @Autowired
    ConverterService converterService;

    @Override
    public ResponseEntity<String> createPointScale(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                                   @ApiParam(value = "pointScale to create"  ) @RequestBody PointScaleWrite body){

        PointScaleEntity pointScaleEntity = pointScaleRepository.findByApiKeyAndName(authorization, body.getName());

        //PointScaleEntity pointScaleEntity = null;

        if(pointScaleEntity == null) {
             pointScaleEntity = converterService.toPointScaleEntity(body, authorization);

            pointScaleRepository.save(pointScaleEntity);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pointScaleEntity.getId()).toUri();

            return ResponseEntity.created(location).build();
        }else {
            return ResponseEntity.noContent().build();
        }


    }

    @Override
    public ResponseEntity<Void> deletePointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                 @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        PointScaleEntity pointScaleEntity = pointScaleRepository.findOne(pointScaleId);

        if(pointScaleEntity == null){
            return ResponseEntity.notFound().build();
        } else if (!pointScaleEntity.getApiKey().equals(authorization)){
            return ResponseEntity.status(401).build();
        }

        rulesRepository.deleteRuleEntitiesByPointScale_Id(pointScaleId);
        pointScaleAwardRepository.deletePointScaleAwardEntityByPointScaleEntity_Id(pointScaleId);
        pointScaleRepository.delete(pointScaleId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<PointScaleRead>> getPointScales(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        List<PointScaleRead> pointScaleReads = new ArrayList<>();

        for(PointScaleEntity pointScaleEntity : pointScaleRepository.findAllByApiKey(authorization)){
            pointScaleReads.add(converterService.toPointScaleRead(pointScaleEntity));
        }
        return ResponseEntity.ok(pointScaleReads);
    }

    @Override
    public ResponseEntity<PointScaleRead> getPointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                        @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        PointScaleEntity pointScaleEntity = pointScaleRepository.findOne(pointScaleId);

        if(pointScaleEntity == null){
            return ResponseEntity.notFound().build();
        } else if (!pointScaleEntity.getApiKey().equals(authorization)){
            return ResponseEntity.status(401).build();
        }
        PointScaleRead pointScaleRead = converterService.toPointScaleRead(pointScaleEntity);

        return ResponseEntity.ok(pointScaleRead);
    }

    @Override
    public ResponseEntity<Void> updatePointScale(@ApiParam(value = "",required=true ) @PathVariable("pointScaleId") Long pointScaleId,
                                                 @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                                 @ApiParam(value = "point scale that needs to be update in the store"  ) @RequestBody PointScaleWrite body){
        PointScaleEntity pointScaleEntity = pointScaleRepository.findOne(pointScaleId);

        if(pointScaleEntity == null){
            return ResponseEntity.notFound().build();//404
        } else if (!pointScaleEntity.getApiKey().equals(authorization)){
            return ResponseEntity.status(401).build();
        }

        pointScaleEntity = converterService.toPointScaleEntity(body, authorization, pointScaleId);

        pointScaleRepository.save(pointScaleEntity);

        return ResponseEntity.status(204).build();
    }
}
