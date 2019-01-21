package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.model.BadgeRead;
import io.AMT.gamification.api.model.BadgeWrite;
import io.AMT.gamification.entities.BadgeEntity;
import io.AMT.gamification.repositories.BadgesRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgesRepository badgesRepository;

    @Override
    public ResponseEntity<String> createBadge(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                              @ApiParam(value = "badge to create"  ) @RequestBody BadgeWrite body) {
        BadgeEntity badgeEntity = toBadgeEntity(body, authorization);

        badgesRepository.save(badgeEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(badgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                     @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){

        badgesRepository.delete(badgeId);

        return ResponseEntity.accepted().build();
    }


    @Override
    public ResponseEntity<List<BadgeRead>> getBadges(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization){
        List<BadgeRead> badgeReads = new ArrayList<>();

        for(BadgeEntity badgeEntity : badgesRepository.findAllByApiKey(authorization)){
            badgeReads.add(toBadgeRead(badgeEntity));
        }
        return ResponseEntity.ok(badgeReads);
    }
    @Override
    public ResponseEntity<BadgeRead> getBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                       @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {

        BadgeEntity badgeEntity = badgesRepository.findOne(badgeId);

        BadgeRead badgeRead = toBadgeRead(badgeEntity);

        return ResponseEntity.ok(badgeRead);

    }

    @Override
    public ResponseEntity<Void> updateBadge(@ApiParam(value = "",required=true ) @PathVariable("badgeId") Long badgeId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                            @ApiParam(value = "badge that needs to be update in the store"  ) @RequestBody BadgeWrite body){

        BadgeEntity badgeEntity = toBadgeEntity(body, authorization, badgeId);

        badgesRepository.save(badgeEntity);

        return ResponseEntity.ok().build();
    }

    private BadgeEntity toBadgeEntity(BadgeWrite badge, String apiKey, Long badgeId){
        BadgeEntity badgeEntity = toBadgeEntity(badge, apiKey);
        badgeEntity.setId(badgeId);
        return badgeEntity;
    }



    private BadgeEntity toBadgeEntity(BadgeWrite badge, String apiKey){
        BadgeEntity badgeEntity = new BadgeEntity();
        badgeEntity.setName(badge.getName());
        badgeEntity.setImage(badge.getImage());
        badgeEntity.setVisible(badge.getVisible());
        badgeEntity.setApiKey(apiKey);

        return badgeEntity;
    }



    private BadgeRead toBadgeRead(BadgeEntity badgeEntity){
        BadgeRead badgeRead = new BadgeRead();
        badgeRead.setName(badgeEntity.getName());
        badgeRead.setImage(badgeEntity.getImage());
        badgeRead.setVisible(badgeEntity.isVisible());
        badgeRead.setId(badgeEntity.getId());

        return badgeRead;
    }
//    @Autowired
//    FruitRepository fruitRepository;
//
//    public ResponseEntity<Object> createFruit(@ApiParam(value = "", required = true) @Valid @RequestBody Fruit fruit) {
//        FruitEntity newFruitEntity = toFruitEntity(fruit);
//        fruitRepository.save(newFruitEntity);
//        Long id = newFruitEntity.getId();
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newFruitEntity.getId()).toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//
//    public ResponseEntity<List<Fruit>> getFruits() {
//        List<Fruit> fruits = new ArrayList<>();
//        for (FruitEntity fruitEntity : fruitRepository.findAll()) {
//            fruits.add(toFruit(fruitEntity));
//        }
//        /*
//        Fruit staticFruit = new Fruit();
//        staticFruit.setColour("red");
//        staticFruit.setKind("banana");
//        staticFruit.setSize("medium");
//        List<Fruit> fruits = new ArrayList<>();
//        fruits.add(staticFruit);
//
//        return ResponseEntity.ok(fruits);
//    }
//
//
//    private FruitEntity toFruitEntity(Fruit fruit) {
//        FruitEntity entity = new FruitEntity();
//        entity.setColour(fruit.getColour());
//        entity.setKind(fruit.getKind());
//        entity.setSize(fruit.getSize());
//        return entity;
//    }
//
//    private Fruit toFruit(FruitEntity entity) {
//        Fruit fruit = new Fruit();
//        fruit.setColour(entity.getColour());
//        fruit.setKind(entity.getKind());
//        fruit.setSize(entity.getSize());
//        return fruit;
//    }

}
