package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
// 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줌
// 예전에는 @ResponseBody로 사용
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    //HTTP Method인 GET의 요청을 받을 수 있는 API를 만들어 줌
    //예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
        //@RequestParam은 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션임
    }
}
