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
	public String login(@RequestParam(value = "error", required = false) String error, Model model, Principal principal,
			RedirectAttributes flash) {

		System.out.println(principal);
		if (principal != null) {
			flash.addFlashAttribute("info", "Already logged in.");
			return "redirect:/";
		}
		
		if (error != null) {
			model.addAttribute("error", "Invalid username or Password");
		}
		
		return "login";
	}
}
