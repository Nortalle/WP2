package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.BadgeAwardEntity;
import io.AMT.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeAwardRepository extends CrudRepository<BadgeAwardEntity, Long> {

    public BadgeAwardEntity findBadgeAwardEntitiesByBadgeEntity(Long id);


    public List<BadgeAwardEntity> findBadgeAwardEntitiesByUserEntity_Id(Long id);


}
