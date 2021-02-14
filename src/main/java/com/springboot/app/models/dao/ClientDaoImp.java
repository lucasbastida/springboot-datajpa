package com.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.entity.Client;

@Repository
public class ClientDaoImp implements ClientDAO {

	//injects h2 db unless specified in application.properties
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Client").getResultList();
	}

	@Override
	@Transactional
	public void save(Client client) {
		// TODO Auto-generated method stub
		em.persist(client);
	}

}
