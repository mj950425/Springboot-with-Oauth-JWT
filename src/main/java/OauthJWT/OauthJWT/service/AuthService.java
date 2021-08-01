package OauthJWT.OauthJWT.service;

import OauthJWT.OauthJWT.model.UserMaster;
import OauthJWT.OauthJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    @Qualifier("AU-UserRepository")
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public UserMaster join(UserMaster user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setUserType("user");
        user.setUserUuid(UUID.randomUUID().toString());
        UserMaster userDTO = userRepository.save(user);
        return userDTO;
    }
}
