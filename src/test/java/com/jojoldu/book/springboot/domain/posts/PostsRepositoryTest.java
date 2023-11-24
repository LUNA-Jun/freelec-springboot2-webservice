package com.jojoldu.book.springboot.domain.posts;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//Junit4에서 RunWith(SpringRunner.class)
@SpringBootTest
//별다른 설정이 없으면 H2 데이터베이스를 자동으로 실행해 줌
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    /*
    Junit4에서 After
    Junit에서 단위 테스트가 끝날대마다 수행되는 메소드를 지정
    배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용
    여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패 할 수 있음
     */

    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());
        /*
        테이블 posts에 insert/update 쿼리를 실행 함
        id 값이 있다면 update가 없다면 insert 쿼리가 실행
         */

        //when
        List<Posts> postsList = postsRepository.findAll();
        //테이블 posts에 있는 모든 데이터를 조회하는 메소드

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}