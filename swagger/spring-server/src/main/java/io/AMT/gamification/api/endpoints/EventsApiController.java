package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.EventsApi;
import io.AMT.gamification.api.model.Event;
import io.AMT.gamification.entities.BadgeAwardEntity;
import io.AMT.gamification.entities.BadgeEntity;
import io.AMT.gamification.entities.RuleEntity;
import io.AMT.gamification.entities.UserEntity;
import io.AMT.gamification.repositories.BadgeAwardRepository;
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

    @Autowired
    RulesRepository rulesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BadgeAwardRepository badgeAwardRepository;

    @Override
    public ResponseEntity<Void> createEvent(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                     @ApiParam(value = "event to create"  ) @RequestBody Event body){

        UserEntity userEntity = usersRepository.findOne(body.getUserId());
        if(userEntity == null){
            userEntity = toUserEntity(body.getUserId(), authorization);
            usersRepository.save(userEntity);
        }

        List<RuleEntity> ruleEntityList = rulesRepository.findAllByApiKeyAndIfEventType(authorization, body.getType());
        //List<RuleEntity> ruleEntityList = rulesRepository.findAllByApiKey(authorization, body.getType());

        if(!ruleEntityList.isEmpty()){
            BadgeEntity badgeToWin = ruleEntityList.get(0).getBadge();
            if(badgeToWin != null){
                BadgeAwardEntity badgeAwardEntity = toBadgeAwardEntity(badgeToWin, userEntity);
                badgeAwardRepository.save(badgeAwardEntity);
            }
        }

        return ResponseEntity.ok().build();
    }

    private UserEntity toUserEntity(Long userId, String apiKey){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setApiKey(apiKey);

        return userEntity;
    }

    private BadgeAwardEntity toBadgeAwardEntity(BadgeEntity badgeEntity, UserEntity userEntity){
        BadgeAwardEntity badgeAwardEntity = new BadgeAwardEntity();
        badgeAwardEntity.setBadgeEntity(badgeEntity);
        badgeAwardEntity.setUserEntity(userEntity);

        return badgeAwardEntity;
    }
}
