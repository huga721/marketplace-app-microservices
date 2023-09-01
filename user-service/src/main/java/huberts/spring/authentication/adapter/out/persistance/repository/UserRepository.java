package huberts.spring.authentication.adapter.out.persistance.repository;

import huberts.spring.authentication.adapter.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
