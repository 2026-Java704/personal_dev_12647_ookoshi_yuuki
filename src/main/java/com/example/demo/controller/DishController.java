package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	//一覧画面表示
	@GetMapping("/dishes/result")
	public String index(Model model) {
		List<Result> resultList = resultRepository.findAll();
		model.addAttribute("resultList", resultList);
		return "dishesResult";
	}

	//食事登録画面表示
	@GetMapping("/dishes/add")
	public String create() {
		return "dishesAdd";
	}

	//食事登録の処理
	@PostMapping("/dishes/add")
	public String add(
			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo,
			Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		Result result = new Result();
		result.setUserId(userId);
		result.setRecordDate(LocalDate.now());
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);

		int achievement = sumAchievement(
				stapleFood,
				sideDish,
				mainDish,
				milkDish,
				fruitCount);
		result.setAchievement(achievement);
		resultRepository.save(result);
		return "redirect:/dishes/result";
	}

	//詳細画面（メモ）の表示
	@GetMapping("/dishes/note")
	public String memo(
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			Model model) {
		model.addAttribute("stapleFood", stapleFood);
		model.addAttribute("sideDish", sideDish);
		model.addAttribute("mainDish", mainDish);
		model.addAttribute("milkDish", milkDish);
		model.addAttribute("fruitCount", fruitCount);

		return "dishesnote";
	}

	//更新画面表示
	@GetMapping("/dishes/{id}/edit")
	public String edit(
			@PathVariable Integer id, Model model) {
		Result result = resultRepository.findById(id).get();
		model.addAttribute("result", result);
		return "dishesEdit";
	}

	@PostMapping("/dishes/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo) {
		Result result = resultRepository.findById(id).get();
		Integer userId = (Integer) session.getAttribute("userId");
		result.setUserId(userId);
		result.setRecordDate(recordDate);
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);
		int achievement = sumAchievement(
				stapleFood,
				sideDish,
				mainDish,
				milkDish,
				fruitCount);
		result.setAchievement(achievement);
		resultRepository.save(result);
		return "redirect:/dishes/result";
	}

	//	//削除処理
	@PostMapping("/dishes/{id}/delete")
	public String delete(@PathVariable Integer id) {
		resultRepository.deleteById(id);
		return "redirect:/dishes/result";
	}

	//点数評価
	private int sumAchievement(Integer stapleFood, Integer sideDish, Integer mainDish, Integer milkDish,
			Integer fruitCount) {
		int achievement = 88;
		if (stapleFood >= 5 && stapleFood <= 7) {
			achievement += 0;
		} else if (stapleFood == 4) {
			achievement -= 4;
		} else if (stapleFood == 3) {
			achievement -= 8;
		} else if (stapleFood == 2) {
			achievement -= 12;
		} else if (stapleFood == 1) {
			achievement -= 16;
		} else {
			achievement -= 28;
		}

		if (sideDish >= 5 && sideDish <= 6) {
			achievement += 0;
		} else if (sideDish == 4) {
			achievement -= 4;
		} else if (sideDish == 3) {
			achievement -= 8;
		} else if (sideDish == 2) {
			achievement -= 12;
		} else if (sideDish == 1) {
			achievement -= 16;
		} else {
			achievement -= 24;
		}

		if (mainDish >= 3 && mainDish <= 5) {
			achievement += 0;
		} else if (mainDish == 7) {
			achievement -= 8;
		} else if (mainDish == 6) {
			achievement -= 4;
		} else if (mainDish == 2) {
			achievement -= 4;
		} else if (mainDish == 1) {
			achievement -= 8;
		} else {
			achievement -= 20;
		}

		if (milkDish == 2) {
			achievement += 0;
		} else if (milkDish == 7) {
			achievement -= 20;
		} else if (milkDish == 6) {
			achievement -= 16;
		} else if (milkDish == 5) {
			achievement -= 12;
		} else if (milkDish == 4) {
			achievement -= 8;
		} else if (milkDish == 3) {
			achievement -= 4;
		} else if (milkDish == 1) {
			achievement -= 4;
		} else {
			achievement -= 8;
		}

		if (fruitCount == 2) {
			achievement += 0;
		} else if (fruitCount == 7) {
			achievement -= 20;
		} else if (fruitCount == 6) {
			achievement -= 16;
		} else if (fruitCount == 5) {
			achievement -= 12;
		} else if (fruitCount == 4) {
			achievement -= 8;
		} else if (fruitCount == 3) {
			achievement -= 4;
		} else if (fruitCount == 1) {
			achievement -= 4;
		} else {
			achievement -= 8;
		}
		return achievement;

	}
}