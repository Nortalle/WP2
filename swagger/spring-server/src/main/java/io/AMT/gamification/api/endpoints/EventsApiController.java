package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.EventsApi;
import io.AMT.gamification.api.model.Event;
import io.AMT.gamification.api.services.ConverterService;
import io.AMT.gamification.entities.*;
import io.AMT.gamification.repositories.BadgeAwardRepository;
import io.AMT.gamification.repositories.PointScaleAwardRepository;
import io.AMT.gamification.repositories.RulesRepository;
import io.AMT.gamification.repositories.UsersRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class EventsApiController implements EventsApi {

    private final
    RulesRepository rulesRepository;

    private final
    UsersRepository usersRepository;

    private final
    BadgeAwardRepository badgeAwardRepository;

    private final
    PointScaleAwardRepository pointScaleAwardRepository;

    private final
    ConverterService converterService;

    @Autowired
    public EventsApiController(RulesRepository rulesRepository, UsersRepository usersRepository, BadgeAwardRepository badgeAwardRepository, PointScaleAwardRepository pointScaleAwardRepository, ConverterService converterService) {
        this.rulesRepository = rulesRepository;
        this.usersRepository = usersRepository;
        this.badgeAwardRepository = badgeAwardRepository;
        this.pointScaleAwardRepository = pointScaleAwardRepository;
        this.converterService = converterService;
    }

    @Override
    public ResponseEntity<Void> createEvent(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                     @ApiParam(value = "event to create"  ) @RequestBody Event body){

        UserEntity userEntity = usersRepository.findOne(body.getUserId());
        if(userEntity == null){
            userEntity = converterService.toUserEntity(body.getUserId(), authorization);
            usersRepository.save(userEntity);
        }

        List<RuleEntity> ruleEntityList = rulesRepository.findAllByApiKeyAndIfEventType(authorization, body.getType());

        for(RuleEntity ruleEntity: ruleEntityList){

            BadgeEntity badgeToWin = ruleEntity.getBadge();

            if(badgeToWin != null) {
                BadgeAwardEntity badgeAwardEntity = converterService.toBadgeAwardEntity(badgeToWin, userEntity);
                badgeAwardRepository.save(badgeAwardEntity);
            }

            PointScaleEntity pointScaleToWin = ruleEntity.getPointScale();

            if(pointScaleToWin != null) {
                PointScaleAwardEntity pointScaleAwardEntity = converterService.toPointScaleAwardEntity(pointScaleToWin, userEntity, ruleEntity.getThenAwardPoint());
                pointScaleAwardRepository.save(pointScaleAwardEntity);
            }
        }


        return ResponseEntity.ok().build();
    }

}
