package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Account;
@Component
public class AccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Account acc = (Account)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"holder","holder.empty", "Holder must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"accountType","account.empty", "Pick an account");
		ValidationUtils.rejectIfEmpty(errors,"dateOpened","date.empty", "date must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"balance","balance.empty", "balance must not be empty");
		if(acc.getBranch()==null)
			errors.rejectValue("branch", "branch.null", "what branch you in?");
		if(acc.getCustomer()==null)
			errors.rejectValue("customer", "customer.null", "you are who?");

	}

}
