package com.siathus.book.springboot.config.auth;

import com.siathus.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /*
        1. supportsParameter(MethodParameter parameter)
            - Controller 메소드의 특정 파라미터를 지원하는지 판단한다.
            - 파라미터에 @LoginUser 어노테이션이 붙어 있으면서, 파라미터 클래스 타입이 SessionUser.class인 경우 true를 반환한다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {   // 1
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    /*
        2. resolveArgument(...)
            - 파라미터에 전달할 객체를 생성한다.
            - 세션에서 객체를 가져오는 역할을 한다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {   // 2
        return httpSession.getAttribute("user");
    }
}
