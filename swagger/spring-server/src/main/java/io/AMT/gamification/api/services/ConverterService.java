package io.AMT.gamification.api.services;

import io.AMT.gamification.api.endpoints.BadgesApiController;
import io.AMT.gamification.api.model.*;
import io.AMT.gamification.entities.*;
import io.AMT.gamification.repositories.BadgesRepository;
import io.AMT.gamification.repositories.PointScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    @Autowired
    BadgesRepository badgesRepository;

    @Autowired
    PointScaleRepository pointScaleRepository;


    public BadgeEntity toBadgeEntity(BadgeWrite badge, String apiKey, Long badgeId){
        BadgeEntity badgeEntity = toBadgeEntity(badge, apiKey);
        badgeEntity.setId(badgeId);
        return badgeEntity;
    }



    public BadgeEntity toBadgeEntity(BadgeWrite badge, String apiKey){
        BadgeEntity badgeEntity = new BadgeEntity();
        badgeEntity.setName(badge.getName());
        badgeEntity.setImage(badge.getImage());
        badgeEntity.setVisible(badge.getVisible());
        badgeEntity.setApiKey(apiKey);

        return badgeEntity;
    }



    public BadgeRead toBadgeRead(BadgeEntity badgeEntity){
        BadgeRead badgeRead = new BadgeRead();
        badgeRead.setName(badgeEntity.getName());
        badgeRead.setImage(badgeEntity.getImage());
        badgeRead.setVisible(badgeEntity.isVisible());
        badgeRead.setId(badgeEntity.getId());

        return badgeRead;
    }


    public UserEntity toUserEntity(Long userId, String apiKey){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setApiKey(apiKey);

        return userEntity;
    }

    public BadgeAwardEntity toBadgeAwardEntity(BadgeEntity badgeEntity, UserEntity userEntity){
        BadgeAwardEntity badgeAwardEntity = new BadgeAwardEntity();
        badgeAwardEntity.setBadgeEntity(badgeEntity);
        badgeAwardEntity.setUserEntity(userEntity);

        return badgeAwardEntity;
    }



    public PointScaleEntity toPointScaleEntity(PointScaleWrite badge, String apiKey, Long pointScaleId){
        PointScaleEntity pointScaleEntity = toPointScaleEntity(badge, apiKey);
        pointScaleEntity.setId(pointScaleId);
        return pointScaleEntity;
    }

    public PointScaleEntity toPointScaleEntity(PointScaleWrite pointScaleWrite, String apiKey){
        PointScaleEntity pointScaleEntity = new PointScaleEntity();
        pointScaleEntity.setName(pointScaleWrite.getName());
        pointScaleEntity.setDescription(pointScaleWrite.getDescription());
        pointScaleEntity.setApiKey(apiKey);

        return pointScaleEntity;
    }

    public PointScaleRead toPointScaleRead(PointScaleEntity pointScaleEntity){
        PointScaleRead pointScaleRead = new PointScaleRead();
        pointScaleRead.setName(pointScaleEntity.getName());
        pointScaleRead.setDescription(pointScaleEntity.getDescription());
        pointScaleRead.setId(pointScaleEntity.getId());

        return pointScaleRead;
    }

    public RuleEntity toRuleEntity(RuleWrite ruleWrite, String apiKey, Long ruleId){
        RuleEntity ruleEntity = toRuleEntity(ruleWrite, apiKey);
        ruleEntity.setId(ruleId);

        return ruleEntity;
    }

    public RuleEntity toRuleEntity(RuleWrite ruleWrite, String authorization) {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setApiKey(authorization);
        ruleEntity.setIfEventType(ruleWrite.getIfEventType());
        ruleEntity.setIfPropertyName(ruleWrite.getIfPropertyName());
        ruleEntity.setIfPropertyCondition(ruleWrite.getIfPropertyCondition());
        ruleEntity.setBadge(badgesRepository.findOne(ruleWrite.getThenBadgeId()));
        ruleEntity.setThenAwardPoint(ruleWrite.getThenAwardPoint());
        ruleEntity.setPointScale(pointScaleRepository.findOne(ruleWrite.getThenPointScaleId()));

        return ruleEntity;
    }

    public RuleRead toRuleRead(RuleEntity ruleEntity){
        RuleRead ruleRead = new RuleRead();
        ruleRead.setId(ruleEntity.getId());
        ruleRead.setIfEventType(ruleEntity.getIfEventType());
        ruleRead.setIfPropertyName(ruleEntity.getIfPropertyName());
        ruleRead.setIfPropertyCondition(ruleEntity.getIfPropertyCondition());
        if(ruleEntity.getBadge() != null) {
            ruleRead.setThenBadgeId(ruleEntity.getBadge().getId());
        }
        if(ruleEntity.getPointScale() != null) {
            ruleRead.setThenPointScaleId(ruleEntity.getPointScale().getId());
        }
        ruleRead.setThenAwardPoint(ruleEntity.getThenAwardPoint());

        return ruleRead;
    }

    public PointScaleAwardEntity toPointScaleAwardEntity(PointScaleEntity pointScaleToWin, UserEntity userEntity, int amount) {

        PointScaleAwardEntity pointScaleAwardEntity = new PointScaleAwardEntity();
        pointScaleAwardEntity.setPointScaleEntity(pointScaleToWin);
        pointScaleAwardEntity.setUserEntity(userEntity);
        pointScaleAwardEntity.setAmount(amount);

            return pointScaleAwardEntity;
        }
}
