package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.management.school_management_be.entity.Rule;
import school.management.school_management_be.entity.SUser;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
