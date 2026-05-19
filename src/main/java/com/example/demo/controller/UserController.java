package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

@Controller
public class UserController {
	private final HttpSession session;
	private final UserRepository userRepository;

	public UserController(HttpSession session, UserRepository userRepository) {
		this.session = session;
		this.userRepository = userRepository;
	}

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {

		List<User> user = userRepository.findByEmailAndPassword(email, password);
		if ((email == null || email.length() == 0) && (password == null || password.length() == 0)) {
			model.addAttribute("message", "メールアドレスを入力してください");
			model.addAttribute("message1", "パスワードを入力してください");
			return "login";
		} else if (email == null || email.length() == 0) {
			model.addAttribute("message", "メールアドレスを入力してください");
			return "login";
		} else if (password == null || password.length() == 0) {
			model.addAttribute("message", "パスワードを入力してください");
			return "login";
		} else if (user.size() == 0) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しません");
			return "login";
		}
		model.addAttribute("email", email);
		model.addAttribute("password", password);

		return "redirect:/result";

	}

	//*新規登録画面の表示
	@GetMapping("user/add")
	public String create() {
		return "user";
	}

	//*新規登録処理
	@PostMapping("/user/add")
	public String store(
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") Integer age,
			@RequestParam(defaultValue = "") Integer gender, Model model) {
		
		List<User> user = userRepository.findByEmail(email);
		List<String>errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add( "名前は必須です");
		}
		if (email.length() == 0) {
			errorList.add( "メールアドレスは必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		if (age == null) {
			errorList.add("年齢は必須です");
		}
		if (gender == 0) {
			errorList.add("性別は必須です");
		}else if (user.size() > 0) {
			errorList.add("登録済みのメールアドレスです");
		}
		if(password.length() ==0) {
			errorList.add("パスワードは必須です");
		
		}
			
			
		
		model.addAttribute(email);
		model.addAttribute(name);
		model.addAttribute(password);
		model.addAttribute(age);
		model.addAttribute(gender);
		
		User users = new UserController() 
		return "user";

	}
}