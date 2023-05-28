package school.management.school_management_be.dto.response.rule;

import lombok.Data;
import school.management.school_management_be.dto.obj.RuleDto;
import school.management.school_management_be.entity.Rule;

import java.util.List;

@Data
public class GetRuleResponse {
    RuleDto ruleDto;
}
