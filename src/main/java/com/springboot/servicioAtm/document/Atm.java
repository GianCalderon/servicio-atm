package com.springboot.servicioAtm.document;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection ="Atm")
public class Atm {
	
	@Id
	private String id;
	
	@NotNull(message = "ATM numberAccount must not be null")
	private String numberAccount;
	
	@NotNull(message = "ATM typeOperation must not be null")
	private String typeOperation;
	
	@NotNull(message = "ATM amount must not be null")
	private Double amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date operationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;
	
	



}
