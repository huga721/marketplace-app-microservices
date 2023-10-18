package huberts.spring.user.adapter.out.persistance.repository;

import huberts.spring.user.adapter.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
