/**
 * 
 */
package net.atos.testoffer.usersmanagementapi.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.atos.testoffer.usersmanagementapi.validator.IsAdultValidator;

/**
 * Validation tests
 * 
 * @author elheni
 *
 */
class IsAdultValidatorTest {
	private static final String DATE_FORMAT_DD_M_MYYYY = "ddMMyyyy";
	@MockBean
	private ConstraintValidatorContext constraintValidatorContext;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_GivenBirthDate_WhenAgeLessThan18_ReturnFalse() {
		IsAdultValidator validator = new IsAdultValidator();
		validator.setAge(18);
		LocalDate calculatedBirthDate = LocalDate.now().minusYears(17);
		boolean isValid = validator.isValid(
				calculatedBirthDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_M_MYYYY)),
				constraintValidatorContext);
		assertFalse(isValid);
	}

	@Test
	void test_GivenBirthDate_WhenAgeGreaterThan18_ReturnTrue() {
		IsAdultValidator validator = new IsAdultValidator();
		validator.setAge(18);
		LocalDate calculatedBirthDate = LocalDate.now().minusYears(19);
		boolean isValid = validator.isValid(
				calculatedBirthDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_M_MYYYY)),
				constraintValidatorContext);
		assertTrue(isValid);
	}

	@Test
	void test_GivenBirthDate_WhenAgeEquals18_ReturnTrue() {
		IsAdultValidator validator = new IsAdultValidator();
		validator.setAge(18);
		LocalDate calculatedBirthDate = LocalDate.now().minusYears(18);
		boolean isValid = validator.isValid(
				calculatedBirthDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_M_MYYYY)),
				constraintValidatorContext);
		assertTrue(isValid);
	}

}
