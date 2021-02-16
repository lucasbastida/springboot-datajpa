package com.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.springboot.app.models.entity.Client;

@Repository
public class ClientDaoImp implements ClientDAO {

	// injects h2 db unless specified in application.properties
	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAll() {
		return em.createQuery("from Client").getResultList();
	}

	@Override
	public Client find(Long id) {
		return em.find(Client.class, id);
	}

	@Override
	public void save(Client client) {
		System.out.println(client.getId());
		if (client.getId() != null && find(client.getId()) != null) {
			em.merge(client);
		} else {
			em.persist(client);
		}
	}

	@Override
	public void delete(Long id) {
		em.remove(find(id));
	}

}
