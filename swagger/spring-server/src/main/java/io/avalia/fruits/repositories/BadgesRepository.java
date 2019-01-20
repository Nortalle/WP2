package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface BadgesRepository extends CrudRepository<BadgeEntity, Long>{

    List<BadgeEntity> findAllByApiKey(String apiKey);

}
