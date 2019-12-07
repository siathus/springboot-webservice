package com.siathus.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
    1. @MappedSuperClass
        - JPA Entity 클래스들이 해당 클래스를 상속할 경우 필드(createdDate, modifiedDate)도 칼럼으로 인식하도록 한다.
    2. @EntityListeners(AuditingEntityListener.class)
        - 해당 클래스에 Auditing 기능을 포함시킨다.
 */
@Getter
@MappedSuperclass   // 1
@EntityListeners(AuditingEntityListener.class)  // 2
public class BaseTimeEntity {

    /*
        3. @CreatedDate
            - Entity가 생성되어 저장될 때 시간이 자동으로 저장된다.
     */
    @CreatedDate    // 3
    private LocalDateTime createdDate;

    /*
        4. @LastModifiedDate
            - 조회한 Entity값을 변경할 때 시간이 자동 저장된다.
     */
    @LastModifiedDate   // 4
    private LocalDateTime modifiedDate;
}
