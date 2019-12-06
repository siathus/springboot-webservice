package com.siathus.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    1. @Entity
        - 테이블과 링크될 클래스임을 나타낸다.
        - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
        - ex. SalesManager.java -> sales_manager table
 */
@Getter
@NoArgsConstructor
@Entity // 1
public class Posts {

    /*
        2. @Id : 해당 테이블의 PK 필드임을 나타낸다.
        3. @GeneratedValue(strategy = GenerationType.IDENTITY)
            - PK의 생성 규칙을 나타낸다.
            - Spring Boot 2.0 버전부터는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 가능하다.
     */
    @Id // 2
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3
    private Long id;

    /*
        4. @Column
            - 테이블의 칼럼을 나타낸다. 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
            - 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용한다.
            - 문자열의 경우, VARCHAR(255)가 기본값이지만, 500으로 늘리고 싶을 때나 타입을 TEXT로 변경하는 등의 경우에 사용한다.
     */
    @Column(length = 500, nullable = false) // 4
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    /*
        7. @Builder
            - 해당 클래스의 빌더 패턴 클래스를 생성한다.
            - 생성자에 선언 시 생성자에 포함된 필드만 빌더에 포함된다.
     */
    @Builder    // 7
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
