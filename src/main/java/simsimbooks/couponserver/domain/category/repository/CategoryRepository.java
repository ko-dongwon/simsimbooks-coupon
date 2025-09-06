package simsimbooks.couponserver.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simsimbooks.couponserver.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
