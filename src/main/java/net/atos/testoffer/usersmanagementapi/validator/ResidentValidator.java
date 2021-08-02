package net.atos.testoffer.usersmanagementapi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * 
 * A validator linked to the annotation @IsResidentOf
 * @author elheni
 *
 */
public class ResidentValidator implements ConstraintValidator<IsResidentOf, String> {

	private String countryCode;
	
	@Override
	public void initialize(IsResidentOf isResidentOf) {
		this.countryCode = isResidentOf.countryCode();
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.hasLength(value)) {
			return countryCode.contentEquals(value.toUpperCase());
		}
		
		return false;
	}

}
