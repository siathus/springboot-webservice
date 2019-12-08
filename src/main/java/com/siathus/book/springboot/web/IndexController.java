package com.siathus.book.springboot.web;

import com.siathus.book.springboot.config.auth.dto.SessionUser;
import com.siathus.book.springboot.domain.user.User;
import com.siathus.book.springboot.service.posts.PostsService;
import com.siathus.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /*
        1. Model
            - 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
            - postsService.findAllDesc()로 가져온 결과를 "posts"라는 key값으로 index.mustache에 전달한다.
     */
    @GetMapping("/")
    public String index(Model model) {  // 1
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
