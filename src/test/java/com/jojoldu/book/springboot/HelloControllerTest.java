package com.jojoldu.book.springboot;

import com.jojoldu.book.springboot.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
// JUnit 4 에서는 RunWith(SpringRunner.class) 사용했음
// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴
// SpringRunner, SpringExtension => 스프링 실행자
@WebMvcTest(controllers = HelloController.class)
// WebMvcTest에만 집중할 수 있는 어노테이션
// 컨트롤러만 사용
public class HelloControllerTest {

    @Autowired
    // 스프링이 관리하는 빈(Bean)을 주입 받음
    private MockMvc mvc;
    // 웹 API 테스트할 때 사용
    // 스프링 MVC 테스트이 시작점
    // HTTP GET, POST 등에 대한 API 테스트 할 수 있음

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        /*
        param
        API 테스트할 때ㅔ 사용될 요청 파라미터를 설정
        값은 String만 허용
        숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야 가능

        jsonPath
        JSON 응답값을 필드별로 검증할 수 있는 메소드
        $를 기준으로 필드명 명시

        테스트 모두 통과
         */

    }
}
