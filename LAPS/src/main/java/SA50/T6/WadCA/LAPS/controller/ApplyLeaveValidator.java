package SA50.T6.WadCA.LAPS.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.Staff;

public class ApplyLeaveValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveRecord.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LeaveRecord leave = (LeaveRecord) target;
		LocalDate from = leave.getLeaveStartDate();
		LocalDate to = leave.getLeaveEndDate();
//		Period period = Period.between(from, to);
		Staff staff = leave.getStaff();
		
		float numOfDay = 0;
		LocalDate curr = from;
		
		do {
			if(curr.compareTo(from)==0 && leave.getLeaveStartTime() == 'P')
				numOfDay += 0.5;
			else if(curr.compareTo(to)==0 && leave.getLeaveEndTime() == 'A')
				numOfDay += 0.5;
			else if(curr.getDayOfWeek() != DayOfWeek.SATURDAY && curr.getDayOfWeek() != DayOfWeek.SUNDAY)
				numOfDay ++;
			
			curr = curr.plusDays(1);
		}while(curr.compareTo(to)!=0);
		
		
//		
//		while(from.isBefore(to)) {
//			if((DayOfWeek.SATURDAY) != from.getDayOfWeek() && (DayOfWeek.SUNDAY != from.getDayOfWeek())){
//				numOfDay++;
//			}
//			break;
//		}
//		
		if(from.getDayOfWeek() == DayOfWeek.SATURDAY || from.getDayOfWeek() == DayOfWeek.SUNDAY || to.getDayOfWeek() == DayOfWeek.SATURDAY || to.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			errors.reject("PH", "From or To date falls on PH");
		}
		
		if(leave.getLeaveType().equals("Annual Leave")) {
			if(numOfDay > staff.getTotalAnnualLeave()) {
				errors.reject("insufficient", "Leave entitlement is not sufficient");
			}
		}
		
		if(leave.getLeaveType().equals("Medical Leave")) {
			if(numOfDay > staff.getTotalMedicalLeave()) {
				errors.reject("insufficient", "Leave entitlement is not sufficient");
			}
		}

	}
	
}
