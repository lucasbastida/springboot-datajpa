package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Client;

public interface ClientService {

	List<Client> findAll();
	void save(Client client);
	Client find(Long id);
	void delete(Long id);
}
