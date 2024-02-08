package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") // url 요청 접수
    public String niceToMeetYou(Model model) { // Model: 데이터를 관리하는 역할
        // model.addAttribute("변수명", 변숫값): 모델에서 변수를 등록할 때 사용
        // model 객체가 변숫값을 변수명(username)에 연결해 웹 브라우저로 보냄
        model.addAttribute("username", "승호");
        return "greetings"; // 서버가 알아서 templates 디렉토리에서 greetings.mustache 파일을 찾아 웹 브라우저로 전송
    }
}
