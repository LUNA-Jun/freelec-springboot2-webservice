package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        /*
            Model
            서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
            postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달 함
         */
        model.addAttribute("posts", postsService.findAllDesc());

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
