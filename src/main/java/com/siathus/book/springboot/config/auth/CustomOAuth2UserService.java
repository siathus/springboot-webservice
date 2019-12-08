package com.siathus.book.springboot.config.auth;

import com.siathus.book.springboot.config.auth.dto.OAuthAttributes;
import com.siathus.book.springboot.config.auth.dto.SessionUser;
import com.siathus.book.springboot.domain.user.User;
import com.siathus.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
            1. registrationId
                - 현재 로그인 진행 중인 서비스를 구분하는 코드
                - 이후 네이버 로그인 연동 시 네이버 로그인인지, 구글 로그인지 구분하기 위해 사용한다.
            2. userNameAttributeName
                - OAuth2 로그인 진행 시 key가 되는 필드값을 이야기한다. PK와 비슷한 의미.
                - 구글의 경우 기본적으로 코드를 지원한다.(디폴트값 : "sub")
                - 이후 네이버와 구글 로그인을 동시 지원할 때 사용한다.
            3. OAuthAttributes
                - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
            4. SessionUser
                - 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.
                - User클래스를 그대로 쓰지 않고 따로 생성해서 쓰는 이유는 세션에 저장될 클래스는 직렬화(Serialization)되어야 하기 때문이다.
                  하지만 User 클래스는 Entity이기 때문에 성능 이슈, 부수 효과가 발생할 확률이 높다. 때문에 직렬화 기능을 가진 세션 Dto를 추가로 만드는 것이
                  이후 운영 및 유지보수 때 많은 도움이 된다.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 1
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();   // 2

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // 3

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));   // 4

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
