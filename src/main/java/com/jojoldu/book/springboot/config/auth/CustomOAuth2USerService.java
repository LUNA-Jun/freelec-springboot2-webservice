package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
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
public class CustomOAuth2USerService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();
        /*
            현재 로그인 진행 중인 서비스를 구분하는 코드
            지금은 구글만 사용하는 불필요한 값 이지만 이후 네이버 로그인 연동 시에 네이버 로그인인지, 구글로그인인지 구분하기 위해 사용
         */

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        /*
            OAuth2 로그인 진행 시 키가 되는 필드값, Primary Key와 같은 의미
            구글의 경우 기본적으로 코드(기본코드 "sub")를 지원함, 네이버 카카오 등은 기본 지원 하지 않음
            네이버 로그인과 구글 로그인을 동시 지원할 때 사용
         */

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User
                        .getAttributes());
        /*
            OAuth2UserService를 통해 OAuth2User의 attribute를 담은 클래스
            네이버 등 다른 소셜로그인도 이 클래스를 사용함
         */

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));
        /*
             세션에 사용자 정보를 저장하기 위한 Dto 클래스
             User 클래스를 사용하지 않은 이유는 직렬화를 구현하지 않았다는 에러가 발생
             User 클래스가 엔티티이기 때문에 언제 다른 엔티티와 관계가 형될지 모름
             직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수효과가 발생할 확률이 높음
         */

        return new DefaultOAuth2User(Collections
                .singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes
                .getEmail())
                .map(entity->entity.update(attributes
                        .getName(), attributes.getPicture()
                )).orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

