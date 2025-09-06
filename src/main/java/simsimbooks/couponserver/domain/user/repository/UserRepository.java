package simsimbooks.couponserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import simsimbooks.couponserver.domain.user.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    @Query("select u.id from User u where MONTH(u.birth) = :month")
    List<Long> findUserIdsByBirthdayMonth(@Param("month") int month);
}
