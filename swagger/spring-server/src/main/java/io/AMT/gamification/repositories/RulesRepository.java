package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RulesRepository extends CrudRepository<RuleEntity, Long> {

    List<RuleEntity> findAllByApiKey(String apiKey);

    List<RuleEntity> findAllByApiKeyAndIfEventType(String apiKey, String type);
}