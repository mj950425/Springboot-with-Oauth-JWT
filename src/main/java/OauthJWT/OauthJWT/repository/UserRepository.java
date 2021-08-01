package OauthJWT.OauthJWT.repository;

import OauthJWT.OauthJWT.model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserMaster, Long> {
    Optional<UserMaster> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<UserMaster> findByUserUuid(String userUuid);
}
