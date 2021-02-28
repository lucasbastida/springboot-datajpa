package com.springboot.app.controllers;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.entity.InvoiceItem;
import com.springboot.app.models.entity.Product;
import com.springboot.app.models.service.ClientService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		Invoice invoice = clientService.findInvoiceById(id);
		
		if (invoice != null) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "Invoice deleted.");
			return "redirect:/view/" + invoice.getClient().getId();
		}
		
		flash.addFlashAttribute("error", "Could not delete invoice");
		return "redirect:/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {
		
		Invoice invoice = clientService.findInvoiceByIdWithClientWithInvoiceItemWithProduct(id);
		
		if (invoice == null) {
			flash.addFlashAttribute("error", "Invoice doesnt exist.");
			return "redirect:/list";
		}
		
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Invoice: " + invoice.getDescription());
		
		return "invoice/view";
	}

	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(name = "clientId") Long clientId, Model model, RedirectAttributes flash) {

		Client client = clientService.find(clientId);

		if (Objects.isNull(client)) {
			flash.addFlashAttribute("error", "Client doesnt exist in the database");
			return "redirect:/list";
		}

		Invoice invoice = new Invoice();
		invoice.setClient(client);

		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Create new invoice");

		return "invoice/form";
	}

	@GetMapping(value = "/load-products/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
		return clientService.findByName(term);

	}

	@PostMapping("/form")
	public String save(@Valid Invoice invoice, 
			BindingResult result,
			Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemID,
			@RequestParam(name = "amount[]", required = false) Integer[] amount, RedirectAttributes flash,
			SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("title", "Create invoice");
			return "invoice/form";
		}
		
		if(itemID == null || itemID.length == 0) {
			model.addAttribute("title", "Create invoice");
			model.addAttribute("error", "The invoice must cointain products.");
			return "invoice/form";
		}
		
		for (int i = 0; i < itemID.length; i++) {
			Product product = clientService.findProductById(itemID[i]);
			
			InvoiceItem item = new InvoiceItem();
			item.setProduct(product);
			item.setAmount(amount[i]);
			
			invoice.addInvoiceItems(item);
		}
		
		clientService.saveInvoice(invoice);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Invoice created successfully.");
		
		return "redirect:/view/" + invoice.getClient().getId();
	}
}
