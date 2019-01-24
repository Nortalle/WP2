package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface BadgesRepository extends CrudRepository<BadgeEntity, Long>{

    List<BadgeEntity> findAllByApiKey(String apiKey);
    BadgeEntity findByApiKeyAndId(String apiKey, Long id);

}
