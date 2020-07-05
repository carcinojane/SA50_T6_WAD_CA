package SA50.T6.WadCA.LAPS.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;

@Component("applyLeaveValidator")
public class ApplyLeaveValidator implements Validator {
	@Autowired
	protected LeaveService lservice;

	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveRecord.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LeaveRecord leaveRecord = (LeaveRecord) target;

		LocalDate from = leaveRecord.getLeaveStartDate();
		LocalDate to = leaveRecord.getLeaveEndDate();
		Staff staff = leaveRecord.getStaff();
		float numOfDay = lservice.numOfLeaveApplied(leaveRecord);

		if(from.isAfter(to)) {
			errors.rejectValue("leaveStartDate", "date.error");
		}

		if(leaveRecord.getLeaveType()!= LType.MedicalLeave) {
			if(from.isBefore(LocalDate.now())) {
				errors.rejectValue("leaveStartDate", "back.date");
			}
		}


		if(from.getDayOfWeek() == DayOfWeek.SATURDAY || from.getDayOfWeek() == DayOfWeek.SUNDAY || to.getDayOfWeek() == DayOfWeek.SATURDAY || to.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			errors.rejectValue("leaveStartDate", "public.holiday");
			errors.rejectValue("leaveEndDate", "public.holiday");

		}
	}

}
