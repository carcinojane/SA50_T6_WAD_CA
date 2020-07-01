package SA50.T6.WadCA.LAPS.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import SA50.T6.WadCA.LAPS.model.Overtime;

public class OvertimeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Overtime.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Overtime overtime = (Overtime) target;
		
		if(overtime.getHours() < 4) {
			errors.reject("min", "At least 4 hours has to be clocked in to be eligable for OT claim.");
		}
		
	}

}

