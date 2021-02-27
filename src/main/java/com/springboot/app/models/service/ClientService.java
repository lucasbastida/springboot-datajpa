package com.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.entity.Product;

public interface ClientService {

	Iterable<Client> findAll();

	Page<Client> findAll(Pageable pageable);

	void save(Client client);

	Client find(Long id);

	void delete(Long id);

	List<Product> findByName(String term);
	
	Product findProductById(Long id);

	void saveInvoice(Invoice invoice);
	
	
}
