package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.management.school_management_be.entity.SUser;

public interface SUserRepository extends JpaRepository<SUser, Long>, SUserRepositoryCustom {
    @Query("SELECT u FROM SUser u WHERE u.userName =:userName AND u.isDeleted = false ")
    SUser findByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM SUser u WHERE u.userId =:userId AND u.password =:password AND u.isDeleted = false ")
    SUser findByUserIdAndPassword(Long userId, String password);

    @Query("SELECT u FROM SUser u WHERE u.userName =:userName AND u.password =:password AND u.isDeleted = false ")
    SUser findByUserNameAndPassword(String userName, String password);

    @Query("SELECT u FROM SUser u WHERE u.userId =:userId AND u.isDeleted = false ")
    SUser findByUserId(Long userId);
}
