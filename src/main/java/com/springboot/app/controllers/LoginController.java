package com.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {

		System.out.println(principal);
		if (principal != null) {
			flash.addFlashAttribute("info", "Already logged in.");
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute("error", "Invalid username or Password");
		}
		
		if (logout != null) {
			model.addAttribute("success", "Logged out successfully.");
		}

		return "login";
	}
}
