package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	//메인페이지 이동
	@GetMapping("/")
    public String home() {
        return "index";
    }
}
