package com.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.app.models.dao.ClientDAO;
import com.springboot.app.models.entity.Client;

@Controller
public class ClientController {

	@Autowired
	private ClientDAO clientDao;
	
	@GetMapping("/")
	public String getIndex() {
	    return "redirect:list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clientDao.findAll());
		return "list";
	}
	
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		
		Client client = new Client();
		model.put("client", client);
		model.put("title", "Client form");
		return "form";
	}
	

	@PostMapping("/form")
	public String save(Client client) {
		clientDao.save(client);
		return "redirect:list";
	}
	
}
