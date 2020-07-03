package SA50.T6.WadCA.LAPS.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;

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
		LeaveRecord leave = (LeaveRecord) target;
		LocalDate from = leave.getLeaveStartDate();
		LocalDate to = leave.getLeaveEndDate();
		Staff staff = leave.getStaff();
		float numOfDay = lservice.numOfLeaveApplied(leave);
		
		if(from.isAfter(to)) {
			errors.reject("date error", "To date should be later than From date");
		}
		
		if(from.isBefore(LocalDate.now())) {
			errors.reject("back date", "From date should be later than today");
		}
				
		if(from.getDayOfWeek() == DayOfWeek.SATURDAY || from.getDayOfWeek() == DayOfWeek.SUNDAY || to.getDayOfWeek() == DayOfWeek.SATURDAY || to.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			errors.reject("PH", "From or To date falls on PH");
		}
		
		if(leave.getLeaveType() == LType.AnnualLeave) {
			if(numOfDay > staff.getTotalAnnualLeave()) {
				errors.reject("insufficient", "Leave entitlement is not sufficient");
			}
		}
		
		if(leave.getLeaveType() == LType.MedicalLeave) {
			if(numOfDay > staff.getTotalMedicalLeave()) {
				errors.reject("insufficient", "Leave entitlement is not sufficient");
			}
		}
		
		if(leave.getLeaveType() == LType.Compensation) {
			if(numOfDay > staff.getTotalCompensationLeave()) {
				errors.reject("insufficient", "Leave entitlement is not sufficient");
			}
		}

	}
	
}
