package OauthJWT.OauthJWT.controller;


import OauthJWT.OauthJWT.dto.PrincipalDetails;
import OauthJWT.OauthJWT.dto.ResourceNotFoundExceptionDto;
import OauthJWT.OauthJWT.model.UserMaster;
import OauthJWT.OauthJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "<h1>Welcome Home</h1>";
    }

    @GetMapping("/user")
    public UserMaster getCurrentUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUserUuid());
        return userRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new ResourceNotFoundExceptionDto("User", "id", principalDetails.getEmail()));
    }
}
