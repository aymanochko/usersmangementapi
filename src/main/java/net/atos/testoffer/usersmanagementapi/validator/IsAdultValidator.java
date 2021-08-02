package net.atos.testoffer.usersmanagementapi.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A validator that check the age of a user in order to verify if it's adult or not
 * it's linked to the annotation @IsAdult
 * 
 * @author elheni
 *
 */
public class IsAdultValidator implements ConstraintValidator<IsAdult, String> {

	private Integer age; 
	@Override
	public void initialize(IsAdult isAdult) {
		age = isAdult.age();
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(Objects.isNull(value) || !value.matches("^[0-9]{8}$")) {
			return false;
		}
		Integer userAge = calculateAgeInYear(value);
		return userAge >= age;
	}
	
	private Integer calculateAgeInYear(String dateOfBirth) {
		LocalDate actualDate = LocalDate.now();
		LocalDate dateOfBirthLocal = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("ddMMyyyy"));
        return Period.between(dateOfBirthLocal, actualDate).getYears();
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}

}
