
package com.springboot.servicioAtm.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CurrentAccountDto {
	
	
	private String id;
	private String nameAccount;
	private String nameBank;
	private List<HeadlineDto> headlines;
	private String numberAccount;
	private Double tea;
	private String state;
	private Double balance;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;


}
