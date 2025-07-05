package simsimbooks.couponserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simsimbooks.couponserver.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
