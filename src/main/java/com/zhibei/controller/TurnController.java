package com.zhibei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/controller/")
public class TurnController {
	
	@RequestMapping("index")
	public String toIndex() {
		System.out.println("321");
		while(true) {
			System.out.println("hello");
		}

	}
}
