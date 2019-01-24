package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.RulesApi;
import io.AMT.gamification.api.model.RuleRead;
import io.AMT.gamification.api.model.RuleWrite;
import io.AMT.gamification.api.services.ConverterService;
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

    @Autowired
    ConverterService converterService;

    @Override
    public ResponseEntity<String> createRule(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                             @ApiParam(value = "rule to create"  ) @RequestBody RuleWrite body) {

        RuleEntity ruleEntity = converterService.toRuleEntity(body, authorization);

        rulesRepository.save(ruleEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ruleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public  ResponseEntity<Void> deleteRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        RuleEntity ruleEntity = rulesRepository.findOne(ruleId);

        if(ruleEntity == null){
            return ResponseEntity.notFound().build();//404
        } else if (!ruleEntity.getApiKey().equals(authorization)){
            return ResponseEntity.status(401).build();
        }

        rulesRepository.delete(ruleId);

        return ResponseEntity.accepted().build();//200
    }

    @Override
    public ResponseEntity<RuleRead> getRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
       RuleEntity ruleEntity = rulesRepository.findOne(ruleId);

        if(ruleEntity == null){
            return ResponseEntity.notFound().build();//404
        } else if (!ruleEntity.getApiKey().equals(authorization)){
            return ResponseEntity.status(401).build();
        }

       RuleRead ruleRead = converterService.toRuleRead(ruleEntity);

       return ResponseEntity.ok(ruleRead);//200
    }

    @Override
    public ResponseEntity<List<RuleRead>> getRules(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        List<RuleRead> ruleReads = new ArrayList<>();

        for(RuleEntity ruleEntity : rulesRepository.findAllByApiKey(authorization)){
            ruleReads.add(converterService.toRuleRead(ruleEntity));
        }
        return ResponseEntity.ok(ruleReads); //200
    }

    @Override
    public ResponseEntity<Void> updateRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                           @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                           @ApiParam(value = "rule that needs to be update in the store"  ) @RequestBody RuleWrite body) {

        RuleEntity ruleEntity = converterService.toRuleEntity(body, authorization, ruleId);
        rulesRepository.save(ruleEntity);

        return ResponseEntity.status(204).build();
    }

}
