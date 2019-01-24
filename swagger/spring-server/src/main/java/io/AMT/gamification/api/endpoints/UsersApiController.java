package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.UsersApi;
import io.AMT.gamification.api.model.BadgeRead;
import io.AMT.gamification.api.model.PointScaleRead;
import io.AMT.gamification.api.services.ConverterService;
import io.AMT.gamification.entities.BadgeAwardEntity;
import io.AMT.gamification.entities.BadgeEntity;
import io.AMT.gamification.entities.PointScaleAwardEntity;
import io.AMT.gamification.repositories.BadgeAwardRepository;
import io.AMT.gamification.repositories.PointScaleAwardRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    BadgeAwardRepository badgeAwardRepository;

    @Autowired
    PointScaleAwardRepository pointScaleAwardRepository;

    @Autowired
    ConverterService converterService;

    @Override
    public ResponseEntity<List<BadgeRead>> getBadgesFromUser(@ApiParam(value = "",required=true ) @PathVariable("userId") Long userId,
                                                      @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        List<BadgeRead> badgeReads = new ArrayList<>();

        for(BadgeAwardEntity badgeAwardEntity : badgeAwardRepository.findBadgeAwardEntitiesByUserEntity_Id(userId)){
            badgeReads.add(converterService.toBadgeRead(badgeAwardEntity.getBadgeEntity()));
        }
        return ResponseEntity.ok(badgeReads);
    }

    @Override
    public ResponseEntity<List<PointScaleRead>> getPointScalesFromUser(@ApiParam(value = "",required=true ) @PathVariable("userId") Long userId,
                                                                       @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        List<PointScaleRead> pointScaleReads = new ArrayList<>();

        for(PointScaleAwardEntity pointScaleAwardEntity : pointScaleAwardRepository.findPointScaleAwardEntitiesByUserEntity_Id(userId)){
            pointScaleReads.add(converterService.toPointScaleRead(pointScaleAwardEntity.getPointScaleEntity()));
        }

        return ResponseEntity.ok(pointScaleReads);
    }
}
