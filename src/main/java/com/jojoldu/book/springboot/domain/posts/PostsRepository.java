package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
MyBatis 등에서 DAO라고 불리는 DB Layer 접근자 임
JPA에서는 Repository라고 부르며 인터페이스로 생성함
JpaRepository<Entity클래스, PK타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성
@Repository를 추가할 필요 없음
주의할점은 Entity 클래스와 기본 Entity Repository는 함께 위치 해야 함
Entity 클래스는 기본 Repository 없이 제대로 역할을 할 수 없기 때문에 도메인 패키지에서 함께 관리 해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
