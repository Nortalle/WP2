package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.RuleEntity;
import io.AMT.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository  extends CrudRepository<UserEntity, Long> {

}
