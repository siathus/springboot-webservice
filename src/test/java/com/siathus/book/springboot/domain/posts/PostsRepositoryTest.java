package com.siathus.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /*
        1. @After
            - JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정하는 어노테이션
            - 보통의 경우, 배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용한다.
            - 여러 테스트가 동시에 수행되면 테스트용 데이터베이스(H2)에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있다.
     */
    @After  // 1
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void load_posts_and_save() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /*
            2. postsRepository.save()
                - posts 테이블에 insert / update 쿼리를 실행한다.
                - id값이 있다면 update가, 없으면 insert 쿼리가 실행된다.
         */
        postsRepository.save(Posts.builder()    // 2
                .title(title)
                .content(content)
                .author("sklim0932@gmail.com")
                .build());

        // when
        /*
            3. postsRepository.findAll()
                - posts 테이블의 모든 데이터를 조회하는 메소드
         */
        List<Posts> postsList = postsRepository.findAll();  // 3

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> createDate = " + posts.getCreatedDate() + ", modifiedDate = " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
