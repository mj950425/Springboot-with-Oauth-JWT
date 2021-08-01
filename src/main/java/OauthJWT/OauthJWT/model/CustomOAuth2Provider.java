package OauthJWT.OauthJWT.model;//package com.tmax.eTest.Auth.model;
//
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//
//public enum CustomOAuth2Provider {
//    KAKAO {
//        @Override public ClientRegistration.Builder getBuilder(String registrationId) {
//            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//            builder.scope("email");
//            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
//            builder.tokenUri("https://kauth.kakao.com/oauth/token");
//            builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
//            builder.userNameAttributeName("id");
//            builder.clientName("Kakao");
//            return builder; } },
//    NAVER {
//        @Override public ClientRegistration.Builder getBuilder(String registrationId) {
//        ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//        builder.scope("email");
//        builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
//        builder.tokenUri("https://nid.naver.com/oauth2.0/token");
//        builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
//        builder.userNameAttributeName("id");
//        builder.clientName("Naver");
//        return builder; } };
//
//
//    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";
//
//    protected final ClientRegistration.Builder getBuilder(
//            String registrationId, ClientAuthenticationMethod method, String redirectUri) {
//
//        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId)
//                .clientAuthenticationMethod(method)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUriTemplate(redirectUri);
//
//        return builder;
//    }
//
//    public abstract ClientRegistration.Builder getBuilder(String registrationId);
//}