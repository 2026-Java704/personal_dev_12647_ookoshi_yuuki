package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.DishRepository;
import com.example.demo.repository.ResultRepository;

@Controller
public class DishController {
	private final HttpSession session;
	private final DishRepository dishRepository;
	private final ResultRepository resultRepository;

	public DishController(HttpSession session, DishRepository dishRepository, ResultRepository resultRepository) {
		this.session = session;
		this.dishRepository = dishRepository;
		this.resultRepository = resultRepository;
	}

	@GetMapping("/result")
	public String index() {
		return "dishesresult";
	}

	@GetMapping("/dishes/add")
	public String add() {
		return "dishesadd";
	}

	@GetMapping("/dishes/note")
	public String note() {
		return "dishesnote";
	}

}