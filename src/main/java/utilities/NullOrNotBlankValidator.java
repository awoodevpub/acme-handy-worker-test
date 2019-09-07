
package utilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

	@Override
	public void initialize(final NullOrNotBlank parameters) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(final String str, final ConstraintValidatorContext constraintValidatorContext) {
		// TODO Auto-generated method stub
		return str == null || str.trim().length() > 0;
	}

}
