package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.EventsApi;
import io.AMT.gamification.api.model.Event;
import io.AMT.gamification.entities.RuleEntity;
import io.AMT.gamification.entities.UserEntity;
import io.AMT.gamification.repositories.RulesRepository;
import io.AMT.gamification.repositories.UsersRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public class EventsApiController implements EventsApi {

    @Autowired
    RulesRepository rulesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public ResponseEntity<Void> createEvent(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                     @ApiParam(value = "event to create"  ) @RequestBody Event body){

        UserEntity userEntity = usersRepository.findOne(body.getUserId());
        if(userEntity == null){
            userEntity = toUserEntity(body.getUserId());
        }


        body.getType();
        body.getPropertyName();
        body.getPropertyDefinition();

        List<RuleEntity> ruleEntityList = rulesRepository.findOneByApiKeyAndType(authorization, body.getType());

        if(ruleEntityList.isEmpty()){

        }

        usersRepository.save(userEntity);

        return ResponseEntity.ok().build();
    }

    private UserEntity toUserEntity(Long userId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        return userEntity;
    }
}
