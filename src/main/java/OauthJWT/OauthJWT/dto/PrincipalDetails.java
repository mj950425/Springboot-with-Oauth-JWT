package OauthJWT.OauthJWT.dto;

import OauthJWT.OauthJWT.model.UserMaster;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements OAuth2User, UserDetails {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String userUuid;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public PrincipalDetails( String email, String password, String userUuid, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.userUuid = userUuid;
    }

    public static PrincipalDetails create(UserMaster user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()));

        return new PrincipalDetails(
                user.getEmail(),
                user.getPassword(),
                user.getUserUuid(),
                authorities
        );
    }

    public static PrincipalDetails create(UserMaster user, Map<String, Object> attributes) {
        PrincipalDetails userPrincipal = PrincipalDetails.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return userUuid;
    }

    public String getUserUuid() { return userUuid;}
}