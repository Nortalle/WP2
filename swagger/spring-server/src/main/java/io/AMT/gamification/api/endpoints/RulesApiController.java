package io.AMT.gamification.api.endpoints;

import io.AMT.gamification.api.RulesApi;
import io.AMT.gamification.api.model.RuleRead;
import io.AMT.gamification.api.model.RuleWrite;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public class RulesApiController implements RulesApi {
    @Override
    public ResponseEntity<String> createRule(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                             @ApiParam(value = "rule to create"  ) @RequestBody RuleWrite body) {



        return null;
    }

    @Override
    public  ResponseEntity<Void> deleteRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<RuleRead> getRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                            @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<List<RuleRead>> getRules(@ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateRule(@ApiParam(value = "",required=true ) @PathVariable("ruleId") Long ruleId,
                                           @ApiParam(value = "" ,required=true ) @RequestHeader(value="authorization", required=true) String authorization,
                                           @ApiParam(value = "rule that needs to be update in the store"  ) @RequestBody RuleWrite body) {
        return null;
    }
}
