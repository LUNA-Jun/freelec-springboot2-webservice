package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/*
스프링에서 Bean을 주입 받는 방식 @Autowired, setter, 생성자가 있음
이 중에서 가장 권장 받는 방식은 생성자로 주입 받는 방식임
생성자로 Bean 객체를 받도록 하면 @Autowired와 동일한 효과를 볼 수 있음
 */
@RequiredArgsConstructor
/*
final이 선언된 모든 필드를 인자 값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성해 줌
생성자를 직접 안 쓰고 롬복 어노테이션을 사용한 이유는 해당 클래스의 의존선광 관계가 변경될 매다 생성자 코드를 계속해서
수정하는 번거로움을 해결하기 위함임
 */
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
    /*
    쿼리를 날리는 부분이 없는 이유는 JPA의 영속성 컨텍스트 때문
    영속성 컨텍스트 : 엔티티를 영구 저장하는 환경, 논리적 개념
    JPA 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림
    JPA 엔티티 매너지가 활성화 된 상태로 트랜잯현 안에서 데이터베이스에서 데이터를 가져오면 데이터는 영속성 컨테스트가 유지된 상태임
    Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없음 -> 더티 체킹(dirty checking)
     */

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) 
    //트랜잭션 범위는 유지, 조회 기능만 남겨두어 조회 속도가 개선, 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하는걸 추천
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id)  {
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }
}
