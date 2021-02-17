package com.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.models.entity.Client;

public interface ClientDAO extends PagingAndSortingRepository<Client, Long> {

}
