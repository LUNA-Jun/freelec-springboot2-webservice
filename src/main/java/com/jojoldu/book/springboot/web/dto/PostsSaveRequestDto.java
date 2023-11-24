package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
Entity 클래스를 Request/Response 클래스로 사용해서는 안됨
Entity 클래스는 데이터베이스와 가까운 핵심 클래스임
View Layer와 DB Layer의 역할을 분리를 철저하게 하는게 좋음
Entity 클래스와 Controller에서 쓸 DTO는 분리해서 사용해야 함
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
