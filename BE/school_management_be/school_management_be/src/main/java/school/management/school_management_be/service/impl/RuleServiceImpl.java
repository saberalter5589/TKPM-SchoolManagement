package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.school_management_be.dto.obj.RuleDto;
import school.management.school_management_be.dto.request.rule.GetRuleRequest;
import school.management.school_management_be.dto.request.rule.UpdateRuleRequest;
import school.management.school_management_be.dto.response.rule.GetRuleResponse;
import school.management.school_management_be.entity.Rule;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.repository.RuleRepository;
import school.management.school_management_be.service.RuleService;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public GetRuleResponse getRule(GetRuleRequest request) {
        List<Rule> ruleList = ruleRepository.findAll();
        Rule rule = ruleList.get(0);
        RuleDto ruleDto = new RuleDto();
        ruleDto.setMinAge(rule.getMinAge());
        ruleDto.setMaxAge(rule.getMaxAge());
        ruleDto.setTest15Rate(rule.getTest15Rate());
        ruleDto.setTest45Rate(rule.getTest45Rate());
        ruleDto.setFinalExamRate(rule.getFinalExamRate());
        ruleDto.setFirstSemRate(rule.getFirstSemRate());
        ruleDto.setSecondSemRate(rule.getSecondSemRate());

        GetRuleResponse response = new GetRuleResponse();
        response.setRuleDto(ruleDto);
        return response;
    }

    @Override
    public void updateRule(UpdateRuleRequest request) {
        List<Rule> ruleList = ruleRepository.findAll();
        Rule rule = ruleList.get(0);

        rule.setMinAge(request.getMinAge());
        rule.setMaxAge(request.getMaxAge());
        rule.setTest15Rate(request.getTest15Rate());
        rule.setTest45Rate(request.getTest45Rate());
        rule.setFinalExamRate(request.getFinalExamRate());
        rule.setFirstSemRate(request.getFirstSemRate());
        rule.setSecondSemRate(request.getSecondSemRate());

        EntityDxo.preUpdate(0L, rule);
        ruleRepository.save(rule);
    }
}
