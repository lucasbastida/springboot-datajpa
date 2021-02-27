package com.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Product;

public interface ProductDao extends CrudRepository<Product, Long>{
	
	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String term);
	
	/* alternative */	
	public List<Product> findByNameLikeIgnoreCase(String term);

}
