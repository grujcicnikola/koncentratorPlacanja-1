package com.example.bankAcquirer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankAcquirer.domain.BuyerInfo;
import com.example.bankAcquirer.domain.Payment;
import com.example.bankAcquirer.dto.BuyerInfoDTO;
import com.example.bankAcquirer.dto.MerchantDTO;
import com.example.bankAcquirer.dto.PaymentDTO;
import com.example.bankAcquirer.service.BuyerInfoService;
import com.example.bankAcquirer.service.MerchantService;
import com.example.bankAcquirer.service.PaymentService;

import javassist.NotFoundException;

@RestController
@RequestMapping("payment")
@CrossOrigin(origins = "http://localhost:4201")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private BuyerInfoService buyerInfoService;
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validate ( @RequestBody MerchantDTO dto, BindingResult bindingResult) {
		System.out.println("Validacija ");
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<String>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		
		boolean valid = merchantService.validate(dto);
		System.out.println("Validacija "+valid);
		
		return new ResponseEntity<Boolean>(valid, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generatePayment(@RequestBody PaymentDTO payment, BindingResult bindingResult) {
		System.out.print("pgodio banku");
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<String>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		
		Payment entity;
		try {
			entity = paymentService.generatePayment(payment);
			return new ResponseEntity<Payment>(entity, HttpStatus.OK);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/payment/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPaymentById(@PathVariable("id") String id) {
		Optional<Payment> payment = paymentService.findByPaymentId(id);
		if(payment.get() != null) {
			PaymentDTO paymentDTO = new PaymentDTO(payment);
			return new ResponseEntity<PaymentDTO>(paymentDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/addBuyerInfo",method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BuyerInfoDTO> addBuyerInfo(@RequestBody BuyerInfoDTO dto)
	{
		BuyerInfo user = new BuyerInfo(dto);
		user.setPayment(paymentService.findByPaymentId(dto.getPaymentId()).get());
		try {
			buyerInfoService.addNewBuyerInfo(user);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
/*	@RequestMapping(
			value = "/transaction", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> transaction(@RequestBody DTOTransaction dtoTransaction, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<String>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = this.bankService.transaction(dtoTransaction);
		
		Payment payment = transaction.getPayment();
		String redirectUrl = "";
		if (transaction.getStatus().equals("success")) {
			redirectUrl = payment.getSuccessURL();
		} else if (transaction.getStatus().equals("failed")) {
			redirectUrl = payment.getFailedURL();
		} else {
			redirectUrl = payment.getErrorURL();
		}
		
		String retVal = restTemplateNotHttps.getForObject(redirectUrl, String.class);

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}*/
}