package com.springbootwithaws.book.springboot.domain.posts;

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
    private PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void postTest() {
        String title = "게시글 저장 테스트";
        String content = "게시글 저장을 테스트해봅니다.";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("handsomekey1123@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void jpaAuditingTest() {
        String title = "jpa auditing 테스트 title";
        String content = "jpa auditing 테스트 content";
        String author = "handsomekey";

        LocalDateTime now = LocalDateTime.of(2019,12,15,0,0);

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build()
        );

        List<Posts> all = postsRepository.findAll();

        Posts post = all.get(0);

        System.out.println(">>>>>>>>> Created Date: " + post.getCreatedDate() + ", Modified Date: " + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}
