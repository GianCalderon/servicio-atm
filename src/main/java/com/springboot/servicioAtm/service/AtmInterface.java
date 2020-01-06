package com.springboot.servicioAtm.service;

import com.springboot.servicioAtm.document.Atm;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AtmInterface {
	
	public Flux<Atm> findAll();
	public Mono<Atm> findById(String id);
	public Mono<Atm> save(Atm atm);
	public Mono<Atm> saveBcp(Atm atm);
	public Mono<Atm> saveInterbank(Atm atm);
	public Mono<Atm> saveBbva(Atm atm);
	public Mono<Atm> update(Atm atm, String id);
	public Mono<Void> delete(Atm atm);

}
