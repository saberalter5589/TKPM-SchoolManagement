package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.rule.GetRuleRequest;
import school.management.school_management_be.dto.request.rule.UpdateRuleRequest;
import school.management.school_management_be.dto.response.rule.GetRuleResponse;

public interface RuleService {
    GetRuleResponse getRule(GetRuleRequest request);
    void updateRule(UpdateRuleRequest request);
}
