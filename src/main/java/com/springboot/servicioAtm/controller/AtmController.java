package com.springboot.servicioAtm.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.servicioAtm.document.Atm;
import com.springboot.servicioAtm.service.AtmImplement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/atm")
public class AtmController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AtmController.class);
	
	@Autowired
	AtmImplement service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Atm>>> toList() {

		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));

	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Atm>> findById(@PathVariable String id) {
		
		return service.findById(id).map(s -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(s))
				.defaultIfEmpty(ResponseEntity.notFound().build());

	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Atm>> update(@RequestBody Atm Atm,
			@PathVariable String id) {
		
		
		LOGGER.info("Controller ----> "+Atm.toString());

		return service.update(Atm, id)
				.map(s -> ResponseEntity.created(URI.create("/api/Atm".concat(s.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(s))
				.defaultIfEmpty(ResponseEntity.notFound().build());

	}
	

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {

		return service.findById(id).flatMap(s -> {
			return service.delete(s).then(Mono.just(new ResponseEntity<Void>(HttpStatus.ACCEPTED)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

	}
	
	@PostMapping("/bcp")
	public Mono<Atm> saveBcp(@RequestBody Atm atm) {
		
		LOGGER.info("accountDto ---> "+atm.toString());

		 return service.saveBcp(atm);

	}
	
	@PostMapping("/interbank")
	public Mono<ResponseEntity<Atm>> saveInterbank(@RequestBody Atm atm) {
		
		LOGGER.info("accountDto ---> "+atm.toString());

		return service.saveInterbank(atm)
				.map(s -> ResponseEntity.created(URI.create("/api/atm".concat(s.getId())))
						 .contentType(MediaType.APPLICATION_JSON).body(s))
				         .defaultIfEmpty(new ResponseEntity<Atm>(HttpStatus.NOT_FOUND));

	}
	
	@PostMapping("/bbva")
	public Mono<ResponseEntity<Atm>> saveBvva(@RequestBody Atm atm) {
		
		LOGGER.info("accountDto ---> "+atm.toString());

		return service.saveBbva(atm)
				.map(s -> ResponseEntity.created(URI.create("/api/atm".concat(s.getId())))
						 .contentType(MediaType.APPLICATION_JSON).body(s))
				         .defaultIfEmpty(new ResponseEntity<Atm>(HttpStatus.NOT_FOUND));

	}
	
	
	

}
