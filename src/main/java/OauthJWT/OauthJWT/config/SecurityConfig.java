package OauthJWT.OauthJWT.config;

import OauthJWT.OauthJWT.jwt.JwtCommonAuthorizationFilter;
import OauthJWT.OauthJWT.jwt.JwtTokenProvider;
import OauthJWT.OauthJWT.repository.UserRepository;
import OauthJWT.OauthJWT.service.OAuth2DetailsService;
import OauthJWT.OauthJWT.service.PrincipalDetailsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Autowired
    private OAuth2DetailsService oAuth2DetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf ??????
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();

        http.addFilter(new JwtCommonAuthorizationFilter(authenticationManager(), tokenProvider, userRepository));

        http.authorizeRequests()

                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2DetailsService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        String token = tokenProvider.create(authentication);
                        response.addHeader("Authorization", "Bearer " +  token);
                        String targetUrl = "/auth/success";
                        RequestDispatcher dis = request.getRequestDispatcher(targetUrl);
                        dis.forward(request, response);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }
                });
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(encodePWD());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties clientProperties,
//             @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
//             @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
//             @Value("${custom.oauth2.naver.client-id}") String naverClientId,
//             @Value("${custom.oauth2.naver.client-secret}") String naverClientSecret)
//
//{
//        System.out.println(naverClientId);
//        List<ClientRegistration> registrations = clientProperties.getRegistration().keySet().stream()
//                        .map(provider -> getRegistration(clientProperties, provider))
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.toList());
//    registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
//            .clientId(kakaoClientId) .clientSecret(kakaoClientSecret)
//            .jwkSetUri("http://localhost:8080") .build());
//    registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
//            .clientId(naverClientId) .clientSecret(naverClientSecret)
//            .jwkSetUri("http://localhost:8080") .build());
//
//    return new InMemoryClientRegistrationRepository(registrations);
//    }
//
//
//    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String provider) {
//        if("google".equals(provider)) {
//            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
//                    .get("google");
//
//            return CommonOAuth2Provider.GOOGLE.getBuilder(provider)
//                    .clientId(registration.getClientId())
//                    .clientSecret(registration.getClientSecret())
//                    .scope("email", "profile")
//                    .build();
//        }
//
//        if("facebook".equals(provider)) {
//            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
//                    .get("facebook");
//
//            return CommonOAuth2Provider.FACEBOOK.getBuilder(provider)
//                    .clientId(registration.getClientId())
//                    .clientSecret(registration.getClientSecret())
//                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
//                    .scope("email")
//                    .build();
//        }
//
//
//        return null;
//
//    }

}
