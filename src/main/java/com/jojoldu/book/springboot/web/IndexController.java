package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
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

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        /*
            Model
            서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
            postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달 함

            LoginUser
            기존에 httpSession.getAttribute("user)로 가져오던 세션 정보 값이 개선
            어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 됨
         */
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    /*
    머스테치 URL 매핑
    매핑은 controller에서 진행 됨
    머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨

    @GetMapping("/")

    public String index() {
        return "index";
        // index을 반환하므로 src/main/resources/templates/index.mustache로 전환 되어 View Resolver가 처리하게 됨
    }
    */
}
