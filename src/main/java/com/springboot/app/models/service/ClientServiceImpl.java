package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.ClientDAO;
import com.springboot.app.models.entity.Client;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDAO clientDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	@Override
	@Transactional(readOnly = true)
	public Client find(Long id) {
		return clientDao.find(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.delete(id);
		
	}

}
