package com.springboot.servicioAtm.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.servicioAtm.client.SavingAccountClient;
import com.springboot.servicioAtm.document.Atm;
import com.springboot.servicioAtm.repo.AtmRepo;
import com.springboot.servicioAtm.util.Bank;
import com.springboot.servicioAtm.util.TypeOperation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AtmImplement implements AtmInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AtmImplement.class);

	@Autowired
	SavingAccountClient savingsAccountClient;
	
	@Autowired
	AtmRepo repo;

	@Override
	public Flux<Atm> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Mono<Atm> findById(String id) {
		
		return repo.findById(id);
	}
	
	@Override
	public Mono<Atm> save(Atm atm) {
		
		atm.setOperationDate(new Date());
		atm.setUpdateDate(new Date());
		
		return repo.save(atm);
	}

	@Override
	public Mono<Atm> saveBcp(Atm atm) {
		
		LOGGER.info("service 1 ---> "+atm.toString());
	
		return savingsAccountClient.findByNumAccount(atm.getNumberAccount()).flatMap(savingsAccount->{
			
			LOGGER.info("service 2 ---> "+savingsAccount.toString());

			
			 Double comision=10.0;
			 if(savingsAccount.getNameBank().toUpperCase().equals(Bank.NAME_BCP)) comision=0.0;
			 
			 if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.DEBITO)) {
					
				 savingsAccount.setBalance((savingsAccount.getBalance()-atm.getAmount())-comision);
				 savingsAccount.setUpdateDate(new Date());
		        
			   }else if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.ABONO)) {
				
				   savingsAccount.setBalance((savingsAccount.getBalance()+atm.getAmount())-comision);
				   savingsAccount.setUpdateDate(new Date());
  		       
			   }
			 
			 return  savingsAccountClient.update(savingsAccount,savingsAccount.getId()).flatMap(account->{
				 
				 LOGGER.info("service 2 ---> "+account.toString());
 		    	
		    	 return save(atm);
		    });
			
		});
	}
	
	@Override
	public Mono<Atm> saveInterbank(Atm atm) {
		return savingsAccountClient.findByNumAccount(atm.getNumberAccount()).flatMap(savingsAccount->{
			
			 Double comision=20.0;
			 if(savingsAccount.getNameBank().toUpperCase().equals(Bank.NAME_INTERBANK)) comision=0.0;
			 
			 if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.DEBITO)) {
					
				 savingsAccount.setBalance((savingsAccount.getBalance()-atm.getAmount())-comision);
				 savingsAccount.setUpdateDate(new Date());
		        
			   }else if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.ABONO)) {
				
				   savingsAccount.setBalance((savingsAccount.getBalance()+atm.getAmount())-comision);
				   savingsAccount.setUpdateDate(new Date());
 		       
			   }
			 
			 return  savingsAccountClient.update(savingsAccount,savingsAccount.getId()).flatMap(account->{
		    	
		    	 return save(atm);
		    });
			
		});
	}
	
	@Override
	public Mono<Atm> saveBbva(Atm atm) {
		return savingsAccountClient.findByNumAccount(atm.getNumberAccount()).flatMap(savingsAccount->{
			
			 Double comision=30.0;
			 if(savingsAccount.getNameBank().toUpperCase().equals(Bank.NAME_BBVA)) comision=0.0;
			 
			 if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.DEBITO)) {
					
				 savingsAccount.setBalance((savingsAccount.getBalance()-atm.getAmount())-comision);
				 savingsAccount.setUpdateDate(new Date());
		        
			   }else if(atm.getTypeOperation().trim().toUpperCase().equals(TypeOperation.ABONO)) {
				
				   savingsAccount.setBalance((savingsAccount.getBalance()+atm.getAmount())-comision);
				   savingsAccount.setUpdateDate(new Date());
		       
			   }
			 
			 return  savingsAccountClient.update(savingsAccount,savingsAccount.getId()).flatMap(account->{
		    	
		    	 return save(atm);
		    });
			
		});
	}

	@Override
	public Mono<Atm> update(Atm atm, String id) {
		
		  return repo.findById(id).flatMap(a -> {

		     a.setAmount(atm.getAmount());
		     a.setNumberAccount(atm.getNumberAccount());
		     a.setTypeOperation(atm.getTypeOperation());
		     a.setUpdateDate(new Date());

		      return repo.save(a);
		  
		    });


		
	}

	@Override
	public Mono<Void> delete(Atm atm) {
		
		return repo.delete(atm);
	}

}
