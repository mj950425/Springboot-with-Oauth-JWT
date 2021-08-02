package OauthJWT.OauthJWT.dto;

import OauthJWT.OauthJWT.model.Role;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class SignUpRequestDto {
    private String name;
    private String gender;
    private String email;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
}