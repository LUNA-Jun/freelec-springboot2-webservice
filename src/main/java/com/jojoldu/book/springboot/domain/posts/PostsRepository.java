package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
MyBatis 등에서 DAO라고 불리는 DB Layer 접근자 임
JPA에서는 Repository라고 부르며 인터페이스로 생성함
JpaRepository<Entity클래스, PK타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성
@Repository를 추가할 필요 없음
주의할점은 Entity 클래스와 기본 Entity Repository는 함께 위치 해야 함
Entity 클래스는 기본 Repository 없이 제대로 역할을 할 수 없기 때문에 도메인 패키지에서 함께 관리 해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    /*
    SpringDataJpa에서 제공하지 않는 메소드는 쿼리로 작성해 됨
    규모가 있는 프로젝트에서의 데이터 조회는 FK의 조인, 복잡한 조건 등으로 인해 이런 Entity클래스만으로
    처리하기 어렵기 때문에 조회용 프레임워크를 추가해서 사용함
    qyerdsl, jooq, Mybatis 등이 있음
    Querdsl를 추천
    1. 타입 안정성 보장
    2. 국내 많은 회사에 사용중
    3. 레퍼런스가 많음
     */
    List<Posts> findAllDesc();
}
