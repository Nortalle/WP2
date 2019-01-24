package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.RulesApi;
import io.AMT.gamification.api.model.RuleRead;
import io.AMT.gamification.api.model.RuleWrite;
import io.AMT.gamification.entities.RuleEntity;
import io.AMT.gamification.repositories.BadgesRepository;
import io.AMT.gamification.repositories.RulesRepository;
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
public class RulesApiController implements RulesApi {
    
    @Autowired
    RulesRepository rulesRepository;

    @Autowired
    BadgesRepository badgesRepository;

    @Override
    public ResponseEntity<String> createRule(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                             @ApiParam(value = "rule to create"  ) @RequestBody RuleWrite body) {

        RuleEntity ruleEntity = toRuleEntity(body, authorization);

        rulesRepository.save(ruleEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ruleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public  ResponseEntity<Void> deleteRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        rulesRepository.delete(ruleId);

        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<RuleRead> getRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
       RuleEntity ruleEntity = rulesRepository.findOne(ruleId);

       RuleRead ruleRead = toRuleRead(ruleEntity);

       return ResponseEntity.ok(ruleRead);
    }

    @Override
    public ResponseEntity<List<RuleRead>> getRules(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        List<RuleRead> ruleReads = new ArrayList<>();

        for(RuleEntity ruleEntity : rulesRepository.findAllByApiKey(authorization)){
            ruleReads.add(toRuleRead(ruleEntity));
        }
        return ResponseEntity.ok(ruleReads);
    }

    @Override
    public ResponseEntity<Void> updateRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                           @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                           @ApiParam(value = "rule that needs to be update in the store"  ) @RequestBody RuleWrite body) {

        RuleEntity ruleEntity = toRuleEntity(body, authorization, ruleId);
        rulesRepository.save(ruleEntity);

        return ResponseEntity.ok().build();
    }

    private RuleEntity toRuleEntity(RuleWrite ruleWrite, String apiKey, Long ruleId){
        RuleEntity ruleEntity = toRuleEntity(ruleWrite, apiKey);
        ruleEntity.setId(ruleId);

        return ruleEntity;
    }

    private RuleEntity toRuleEntity(RuleWrite ruleWrite, String authorization) {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setApiKey(authorization);
        ruleEntity.setIfEventType(ruleWrite.getIfEventType());
        ruleEntity.setIfPropertyName(ruleWrite.getIfPropertyName());
        ruleEntity.setIfPropertyCondition(ruleWrite.getIfPropertyCondition());
        ruleEntity.setBadge(badgesRepository.findOne(ruleWrite.getThenBadgeId()));
        ruleEntity.setThenAwardPoint(ruleWrite.getThenAwardPoint());
        ruleEntity.setThenPointScaleId(ruleWrite.getThenPointScaleId());

        return ruleEntity;
    }

    private RuleRead toRuleRead(RuleEntity ruleEntity){
        RuleRead ruleRead = new RuleRead();
        ruleRead.setId(ruleEntity.getId());
        ruleRead.setIfEventType(ruleEntity.getIfEventType());
        ruleRead.setIfPropertyName(ruleEntity.getIfPropertyName());
        ruleRead.setIfPropertyCondition(ruleEntity.getIfPropertyCondition());
        ruleRead.setThenBadgeId(ruleEntity.getBadge().getId());
        ruleRead.setThenPointScaleId(ruleEntity.getThenPointScaleId());
        ruleRead.setThenAwardPoint(ruleEntity.getThenAwardPoint());

        return ruleRead;
    }
}
