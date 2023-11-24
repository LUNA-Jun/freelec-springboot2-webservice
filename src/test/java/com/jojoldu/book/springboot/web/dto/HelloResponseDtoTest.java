package com.jojoldu.book.springboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 룸북_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        /*
        assertThat
        assertj라는 테스트 검증 라이브러리의 검증 메소드
        검증하고 싶은 대상 메소드 인자로 받음

        isEqualTo
        assertj의 동등비교 메소드
        assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공
         */
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

/*
assertj도 Junit에서 자동으로 라이브러리 등록 해줌

assertj의 장점 - 백기선님 assertJ 유튜브 참고
CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않음
자동완성이 좀 더 확실하게 지원
 */