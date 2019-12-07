package com.siathus.book.springboot.service.posts;

import com.siathus.book.springboot.domain.posts.Posts;
import com.siathus.book.springboot.domain.posts.PostsRepository;
import com.siathus.book.springboot.web.dto.PostsListResponseDto;
import com.siathus.book.springboot.web.dto.PostsResponseDto;
import com.siathus.book.springboot.web.dto.PostsSaveRequestDto;
import com.siathus.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /*
        1. @Transactional(readOnly = true)
            - 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선된다.
            - 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하면 좋다.
        2. .map(PostsListResponseDto::new)
            - .map(posts -> new PostsListResponseDto(posts))와 같다.
            - postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostListsResponseDto로 변환하여 List로 반환하는 메소드이다.
     */
    @Transactional(readOnly = true) // 1
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // 2
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
