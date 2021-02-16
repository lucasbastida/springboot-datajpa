package com.springboot.app.controllers;

import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.ClientService;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/")
	public String getIndex() {
		return "redirect:list";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clientService.findAll());
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
	public String save(@Valid @ModelAttribute("client") Client client, BindingResult result, Model model,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Client form");
			return "form";
		}

		clientService.save(client);
		status.setComplete();
		return "redirect:list";
	}

	@GetMapping("/form/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {

		Client client = clientService.find(id);

		if (!Objects.isNull(client)) {
			model.addAttribute("client", client);
			System.out.println(client.getId());
			model.addAttribute("title", "Edit client");
		}

		return "form";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {

		Client client = clientService.find(id);

		if (!Objects.isNull(client)) {
			clientService.delete(id);
		}

		return "redirect:/list";
	}

}
