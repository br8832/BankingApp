package com.synergisticit.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;
import com.synergisticit.service.CustomerService;

@Component
public class CustomerValidator implements Validator{
	// Regular expression pattern for SSN (Social Security Number)
	// actual SSN "^(?!000|666|9\\d\\d)\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}$";
	// just a simple ddd-dd-dddd
    private static final String SSN_PATTERN = "^\\d{3}-\\d{2}-\\d{4}$";
    @Autowired
    CustomerService customerService;

    public static boolean isSSN(String ssn) {
        // Create a Pattern object
        Pattern pattern = Pattern.compile(SSN_PATTERN);
        
        // Match the given string with the pattern
        Matcher matcher = pattern.matcher(ssn);
        
        // Return true if the string matches the SSN pattern, false otherwise
        return matcher.matches();
    }
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Customer c = (Customer)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","name.empty", "Name must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"mobile","mobile.empty", "Mobile must not be empty");
		if(c.getUser()==null)
			errors.rejectValue("user","user.empty","Choose one User");
		else if(customerService.existsAlready(c.getUser().getUsername()) && c.getId()==customerService.getNextId())
			errors.rejectValue("user", "user.exists", "Por listo chava");
		if(c.getGender()==null)
			errors.rejectValue("gender","gender.empty","Choose one");
		if(c.getDob()==null)
			errors.rejectValue("dob","dob.empty","Enter your DOB");
		if(!isSSN(c.getSSN()))
			errors.rejectValue("SSN","SSN.invalid","Must be in form XXX-XX-XXXX");
		if(c.getAddress()!=null) {
			ValidationUtils.rejectIfEmpty(errors,"address.line1","address.line1.empty", "Line1 must not be empty");
			ValidationUtils.rejectIfEmpty(errors,"address.city","address.city.empty", "City must not be empty");
			ValidationUtils.rejectIfEmpty(errors,"address.state","address.state.empty", "State must not be empty");
			ValidationUtils.rejectIfEmpty(errors,"address.country","address.country.empty", "Country must not be empty");
			ValidationUtils.rejectIfEmpty(errors,"address.zip","address.zip.empty", "Zip must not be empty");
			}
			
	}

}
