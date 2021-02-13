package com.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.app.models.dao.ClientDAO;

@Controller
public class ClientController {

	@Autowired
	private ClientDAO clientDao;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clientDao.findAll());
		return "list";
	}
	
	
	@GetMapping("/")
	public String getIndex() {
	    return "redirect:/list";
	}
	
}
