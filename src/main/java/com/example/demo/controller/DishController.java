package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Result;
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

	@GetMapping("/dishes/result")
	public String index(Model model) {
		List<Result> resultList = resultRepository.findAll();
		model.addAttribute("resultList", resultList);
		return "dishesResult";
	}

	@GetMapping("/dishes/add")
	public String create() {
		return "dishesAdd";
	}
	//
	//	@PostMapping("/dishes/add")
	//	public String add(
	//			@RequestParam(defaultValue = "")LocalDate recordDate,
	//			@RequestParam(defaultValue = "")Integer stapleFood,
	//			@RequestParam(defaultValue = "")Integer sideDish,
	//			@RequestParam(defaultValue = "")Integer mainDish,
	//			@RequestParam(defaultValue = "")Integer milkDish,
	//			@RequestParam(defaultValue = "")Integer fruitCount,
	//			@RequestParam(defaultValue = "")String detalMemo,
	//			Model model) {
	//		Result results = resultRepository.findById(id),get();
	//		Result result =  new Result(recordDate, stapleFood,sideDish,mainDish,milkDish,fruitCount,detalMemo);
	//	}
	//	

	@GetMapping("/dishes/note")
	public String note() {
		return "dishesnote";
	}

}