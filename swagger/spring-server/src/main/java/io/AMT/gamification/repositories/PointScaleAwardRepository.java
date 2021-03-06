package io.AMT.gamification.repositories;

import io.AMT.gamification.entities.PointScaleAwardEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
        import java.util.List;

public interface PointScaleAwardRepository extends CrudRepository<PointScaleAwardEntity, Long> {


    List<PointScaleAwardEntity> findPointScaleAwardEntitiesByUserEntity_Id(Long id);

    @Transactional
    void deletePointScaleAwardEntityByPointScaleEntity_Id(Long id);
}
