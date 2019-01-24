package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.model.BadgeRead;
import io.AMT.gamification.api.model.BadgeWrite;
import io.AMT.gamification.api.services.ConverterService;
import io.AMT.gamification.entities.BadgeEntity;
import io.AMT.gamification.repositories.BadgesRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgesRepository badgesRepository;

    @Autowired
    ConverterService converterService;

    @Override
    public ResponseEntity<String> createBadge(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                              @ApiParam(value = "badge to create"  ) @RequestBody BadgeWrite body) {

        BadgeEntity badgeEntity = converterService.toBadgeEntity(body, authorization);

        badgesRepository.save(badgeEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(badgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                     @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){

        badgesRepository.delete(badgeId);

        return ResponseEntity.accepted().build();
    }


    @Override
    public ResponseEntity<List<BadgeRead>> getBadges(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        List<BadgeRead> badgeReads = new ArrayList<>();

        for(BadgeEntity badgeEntity : badgesRepository.findAllByApiKey(authorization)){
            badgeReads.add(converterService.toBadgeRead(badgeEntity));
        }
        return ResponseEntity.ok(badgeReads);
    }
    @Override
    public ResponseEntity<BadgeRead> getBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                       @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {

        BadgeEntity badgeEntity = badgesRepository.findByApiKeyAndId(authorization,badgeId);

        if(badgeEntity == null){
            return ResponseEntity.status(401).build();
        }

        BadgeRead badgeRead = converterService.toBadgeRead(badgeEntity);

        return ResponseEntity.ok(badgeRead);

    }

    @Override
    public ResponseEntity<Void> updateBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                            @ApiParam(value = "badge that needs to be update in the store"  ) @RequestBody BadgeWrite body){

        BadgeEntity badgeEntity = converterService.toBadgeEntity(body, authorization, badgeId);

        badgesRepository.save(badgeEntity);

        return ResponseEntity.status(204).build();
    }
}
