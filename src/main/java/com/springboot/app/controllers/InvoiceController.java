package com.springboot.app.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.service.ClientService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	
	@Autowired
	private ClientService clientService;
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(name = "clientId") Long clientId, Model model, RedirectAttributes flash) {

		Client client = clientService.find(clientId);
		
		if(Objects.isNull(client)) {
			flash.addFlashAttribute("error", "Client doesnt exist in the database");
			return "redirect:/list";
		}
		
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("title:", "Create new invoice");
		
		return "invoice/form";
	}

}
