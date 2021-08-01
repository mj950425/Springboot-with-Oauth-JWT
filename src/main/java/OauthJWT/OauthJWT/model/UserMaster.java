package OauthJWT.OauthJWT.model;




import javax.persistence.*;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="USER_MASTER")
@Builder
public class UserMaster {
    @Id
    private String userUuid;
    private String userType;

    private String name;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email
    @Column(nullable = false)
    private String email;

    private String gender;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

}
