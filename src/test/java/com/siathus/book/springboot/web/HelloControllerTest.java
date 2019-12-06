package com.siathus.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
    1. @RunWith(SpringRunner.class)
        - 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
        - 스프링 부트 테스트와 JUnit 사이의 연결자 역할
 */
@RunWith(SpringRunner.class)
/*
    2. @WebMvcTest
        - Web(Spring MVC)에 집중할 수 있는 스프링 테스트 어노테이션
        - 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
        - @Service, @Component, @Repository 등은 사용할 수 없다.
 */
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    /*
        3. private MockMvc mvc
            - 웹 API를 테스트할 때 사용한다.
            - 스프링 MVC 테스트의 시작점이 된다.
            - 이 클래스를 통해 HTTP GET, POST등에 대한 API 테스트를 진행할 수 있다.
     */
    private MockMvc mvc;

    @Test
    public void hello_is_returned() throws Exception {
        String hello = "hello";

        /*
            4. mvc.perform(get("/hello"))
                - MockMvc를 통해 /hello 주소로 HTTP GET 요청한다.
                - 체이닝(Chaining)을 지원하기 때문에 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
         */
        mvc.perform(get("/hello"))
                /*
                    5. .andExpect(status().isOk())
                        - mvc.perform의 결과를 검증한다.
                        - HTTP Header의 status를 검증한다.
                        - ex. 200, 404, 500 등
                        - 여기서는 OK, 즉 200인지를 검증한다.
                 */
                .andExpect(status().isOk())
                /*
                    6. .andExpect(content().string(hello))
                        - 응답 본문의 내용을 검증한다.
                        - Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
                 */
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto_is_returned() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
            1. param
                - API 테스트 시 사용될 요청 파라미터를 설정한다.
                - 값은 String만 허용된다. 따라서 숫자 / 날짜 등의 데이터를 등록할 시에는 문자열로 변경해야 한다.
            2. jsonPath
                - JSON 응답값을 필드별로 검증할 수 있는 메소드
                - $를 기준으로 필드명을 명시한다. ($.name, $.amount)
         */
        mvc.perform(get("/hello/dto")
                        .param("name", name).param("amount", String.valueOf(amount)))   // 1
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(name)))    // 2
                        .andExpect(jsonPath("$.amount", is(amount)));

    }
}
