package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 주요 어노스테이션을 클래스에 가깝게 둠
// 코틀린 등의 새 언어 전환으로 롬복이 더 이상 필요 없을 경우 삭제
@Getter // 클래스 내 모든 필드의 Getter 메소들 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타냄
/*
웬만하면 Entity의 PK는 Long 타입의 Auto_increment를 추천함 (MySQL 기준으로 bigint 타입)
- FK를 맺을 떼 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야하는 상황이 발생함
- 인덱스에 좋은 영향을 끼치지 못함
- 유니크한 조건이 변경될 경우 PK 전체를 수정해야 하는 일이 발생
 */
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타냄
    private Long id;

    @Column(length = 500, nullable = false)
    /*
    테이블의 컬럼을 나타내며 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨
    사용하는 이윤는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
     */

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

/*
getter/setter를 무작정 생성하는 경우 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지
코드상으로 명확하게 구분할 수 없어 차후 기능 변경시 복잡해짐
Entity 클래스에서는 절대 Setter 메소드를 만들지 않음
 */