package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Transaction;

@Component
public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Transaction.class.equals(clazz);

	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Transaction t = (Transaction)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"amount","amount.empty", "Name must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"comments","comments.empty", "Comments must not be empty");
		if(t.getType()==null)
			errors.rejectValue("type", "type.empty", "Choose a type");
		else {
		switch(t.getType()) {
		case WITHDRAW:
			if(t.getFromAccount()==null) errors.rejectValue("fromAccount","fromAccount.empty","Choose fromAccount");
			break;
		case TRANSFER:
			if(t.getFromAccount()==null) errors.rejectValue("fromAccount","fromAccount.empty","Choose fromAccount");
			if(t.getToAccount()==null) errors.rejectValue("toAccount","toAccount.empty","Choose toAccount");
			break;
		case DEPOSIT:
			if(t.getToAccount()==null) errors.rejectValue("toAccount","toAccount.empty","Choose toAccount");
			break;
		}
		}
	}

}


