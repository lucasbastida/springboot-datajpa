package com.springboot.app.models.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.app.models.entity.Client;

public interface ClientService {

	Iterable<Client> findAll();
	Page<Client> findAll(Pageable pageable);
	void save(Client client);
	Client find(Long id);
	void delete(Long id);
}
