package com.siathus.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 1. 선언된 모든 필드의 getter 메소드 생성
@RequiredArgsConstructor    // 2. 선언된 모든 final 필드가 포함된 생성자를 생성한다. final이 없는 필드는 포함하지 않는다.
public class HelloResponseDto {

    private final String name;
    private final int amount;
    
}
