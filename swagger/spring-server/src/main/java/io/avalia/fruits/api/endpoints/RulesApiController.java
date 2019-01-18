package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.RulesApi;
import io.avalia.fruits.api.model.RuleRead;
import io.avalia.fruits.api.model.RuleWrite;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class RulesApiController implements RulesApi {
    @Override
    public ResponseEntity<String> createRule(String authorization, RuleWrite body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRule(String ruleId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<RuleRead> getRule(String ruleId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<List<RuleRead>> getRules(String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateRule(String ruleId, String authorization, RuleWrite body) {
        return null;
    }
}
