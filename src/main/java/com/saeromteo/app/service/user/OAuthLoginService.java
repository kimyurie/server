package com.saeromteo.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.saeromteo.app.dao.user.UserDAO;
import com.saeromteo.app.dto.user.GoogleUserInfo;
import com.saeromteo.app.dto.user.KakaoUserInfo;
import com.saeromteo.app.dto.user.OAuth2UserInfo;
import com.saeromteo.app.dto.user.PrincipalDetail;
import com.saeromteo.app.dto.user.UserDTO;

@Service("oAuthService")
public class OAuthLoginService extends DefaultOAuth2UserService{
    @Autowired
    UserDAO userDao;
    
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuthLoginService: loadUser 호출됨");
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes(), String.valueOf(oAuth2User.getAttributes().get("id")));
        } else {
            throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        UserDTO userEntity = userDao.loadUserByUsername(email);
        if (userEntity == null) {
            System.out.println("신규 oAuth 로그인 유저");
        } else {
            System.out.println(provider + " 로그인을 이미 한 적이 있습니다.");
        }

        return new PrincipalDetail(userEntity, oAuth2User.getAttributes());
    }
}
