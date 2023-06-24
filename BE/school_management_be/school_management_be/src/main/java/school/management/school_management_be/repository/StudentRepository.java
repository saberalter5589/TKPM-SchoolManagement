package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.SClass;
import school.management.school_management_be.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
    @Query("SELECT st FROM Student st WHERE st.studentId =:studentId AND st.isDeleted = false ")
    Student findByStudentId(Long studentId);

    @Query(value = "SELECT COUNT(std) FROM student std " +
            "WHERE std.class_id =:classId AND std.is_deleted = false", nativeQuery = true)
    Long getStudentCountByClass(Long classId);
}
