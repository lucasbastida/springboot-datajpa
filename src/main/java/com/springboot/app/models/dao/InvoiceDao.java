package com.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

	@Query("select i from Invoice i join fetch i.client c join fetch i.items l join fetch l.product where i.id=?1")
	Invoice fetchByIdWithClientWithInvoiceItemWithProduct(Long id);
}
