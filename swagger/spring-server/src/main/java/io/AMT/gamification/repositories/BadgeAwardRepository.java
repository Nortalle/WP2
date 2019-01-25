package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.BadgeAwardEntity;
import io.AMT.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BadgeAwardRepository extends CrudRepository<BadgeAwardEntity, Long> {



    List<BadgeAwardEntity> findBadgeAwardEntitiesByUserEntity_Id(Long id);

    @Transactional
    void deleteBadgeAwardEntitiesByBadgeEntity_Id(Long id);

}
