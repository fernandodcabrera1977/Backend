package com.intercorp.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intercorp.challenge.model.entities.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, Integer>{

}
