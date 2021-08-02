package net.atos.testoffer.usersmanagementapi.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Java anotation used to the a user country if it's local or not
 * @author elheni
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResidentValidator.class)
public @interface IsResidentOf {
    String message() default "The user is not a local resident";
    String countryCode() default "FR";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
	/**
	 * Defines several {@link IsResidentOf} annotations on the same element.
	 *
	 * @see IsResidentOf
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		IsResidentOf[] value();
	}
}
