package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	//https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
	 public boolean isValidEmailAddress(String email) {
         String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
         java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
         java.util.regex.Matcher m = p.matcher(email);
         return m.matches();
  }

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User)target;
		if(!isValidEmailAddress(u.getEmail())) {
			errors.rejectValue("email", "email.invalid", "Must be valid email address");
		}
//		
//		if(u.getPassword().length() <8) {
//			errors.rejectValue("password", "password.min", "Must be greater than 8 characters - password");
//		}
//		
//		if(u.getUsername().isEmpty()) {
//			errors.rejectValue("username", "username.empty", "Must not be empty");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","password.empty", "password should not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username","username.empty", "Username should not be empty");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "id.empty", "ID should not be empty.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Email should not be empty.");
		if(u.getRoles().isEmpty()) {
			errors.rejectValue("roles", "role.empty", "The roles should not be empty");
		}
//		if(u.getEmail().isEmpty()) {
//			errors.rejectValue("email", "email.empty", "The roles should not be empty");
//		}
//		if(u.getPassword().isEmpty()) {
//			errors.rejectValue("password", "password.empty", "The password should not be empty");
//
//		}
	}

}
