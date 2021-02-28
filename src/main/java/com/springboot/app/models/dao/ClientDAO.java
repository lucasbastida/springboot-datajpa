package com.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.models.entity.Client;

public interface ClientDAO extends PagingAndSortingRepository<Client, Long> {

	@Query("select c from Client c left join fetch c.invoices i where c.id=?1")
	Client fetchByIdWithInvoices(Long id);
	
}
