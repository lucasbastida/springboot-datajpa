package com.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.ClientService;
import com.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("client") 
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/")
	public String getIndex() {
		return "redirect:list";
	}

	@GetMapping("/view/{id}")
	public String clientInfo(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Client client = clientService.find(id);

		if (client == null) {
			flash.addFlashAttribute("error", "The client id " + id + " doesnt exist in the database");
			return "redirect:/list";
		}
		
		model.addAttribute("client", client);
		model.addAttribute("title", client.getName() + " details:");
		
		return "clientinfo";

	}

	@GetMapping("/list")
	public String list(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, 5);
		Page<Client> clients = clientService.findAll(pageable);

		PageRender<Client> pageRender = new PageRender<>("/list", clients);

		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
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
			@RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Client form");
			return "form";
		}

		if (!photo.isEmpty()) {
			Path photoDir = Paths.get("src//main//resources/static/uploads");
			String rootPath = photoDir.toFile().getAbsolutePath();
			try {
				byte[] bytes = photo.getBytes();
				Path path = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(path, bytes);

				flash.addFlashAttribute("info", "Uploaded " + photo.getOriginalFilename() + " successfully!");

				client.setPhoto(photo.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String message = (client.getId() != null) ? "Client edited successfully!" : "Client created successfully!";

		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", message);
		return "redirect:list";
	}

	@GetMapping("/form/{id}")
	public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

		Client client = clientService.find(id);

		if (!Objects.isNull(client)) {
			model.addAttribute("client", client);
			System.out.println(client.getId());
			model.addAttribute("title", "Edit client");
		} else {
			flash.addFlashAttribute("error", "Cant edit client.");
			return "redirect:/list";
		}

		return "form";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {

		Client client = clientService.find(id);

		if (!Objects.isNull(client)) {
			clientService.delete(id);
			flash.addFlashAttribute("success", "Client deleted successfully!");
		}

		return "redirect:/list";
	}

}
