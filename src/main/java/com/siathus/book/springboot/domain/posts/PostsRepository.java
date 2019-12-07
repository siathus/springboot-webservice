package com.siathus.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
    - MyBatis 등에서 DAO로 불리는 DB Layer 접근자.
    - JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다.
    - @Repository 어노테이션을 추가할 필요가 없다.
    - 주의점 : Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    /*
        1. @Query
            - Spring Data Jpa에서 제공하지 않는 메소드를 쿼리로 작성할 수 있게 해주는 어노테이션.
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // 1
    List<Posts> findAllDesc();

}
