package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Branch;

@Component
public class BranchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Branch.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Branch b = (Branch)target;
		ValidationUtils.rejectIfEmpty(errors,"name","name.empty", "Name must not be empty");
		if(b.getAddress()!=null) {
		ValidationUtils.rejectIfEmpty(errors,"address.line1","address.line1.empty", "Line1 must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"address.city","address.city.empty", "City must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"address.state","address.state.empty", "State must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"address.country","address.country.empty", "Country must not be empty");
		ValidationUtils.rejectIfEmpty(errors,"address.zip","address.zip.empty", "Zip must not be empty");
		}

	}

}
