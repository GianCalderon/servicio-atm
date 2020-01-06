package com.springboot.servicioAtm.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.servicioAtm.document.Atm;

public interface AtmRepo extends ReactiveMongoRepository<Atm, String> {

}
