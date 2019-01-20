package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface PointScaleRepository extends CrudRepository<PointScaleEntity, Long>{

    List<PointScaleEntity> findAllByApiKey(String apiKey);

}