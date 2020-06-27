package SA50.T6.WadCA.LAPS.model;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class LeaveRecord{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	private int managerId;
	private LeaveStatus leaveStatus;
	public enum LeaveStatus{
		APPLIED,
		APPROVED,
		REJECTED,
		CANCEL
	}
	
	private String reason;
	private LocalDateTime leaveStartDate;
	private LocalDateTime leaveEndDate;
	private int staffId;
	private String workDessemination;
	private int contact;
	private String reasonForRejection;
	
	
	public LeaveRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRecord(int managerId, LeaveStatus leaveStatus, String reason, LocalDateTime leaveStartDate,
			LocalDateTime leaveEndDate, int staffId, String workDessemination, int contact) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.staffId = staffId;
		this.workDessemination = workDessemination;
		this.contact = contact;
	}
	
	public LeaveRecord(int managerId, LeaveStatus leaveStatus, String reason, LocalDateTime leaveStartDate,
			LocalDateTime leaveEndDate, int staffId, String workDessemination, int contact, String reasonForRejection) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.staffId = staffId;
		this.workDessemination = workDessemination;
		this.contact = contact;
		this.reasonForRejection = reasonForRejection;
	}


	public int getLeaveId() {
		return leaveId;
	}


	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}


	public int getManagerId() {
		return managerId;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}


	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public LocalDateTime getLeaveStartDate() {
		return leaveStartDate;
	}


	public void setLeaveStartDate(LocalDateTime leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}


	public LocalDateTime getLeaveEndDate() {
		return leaveEndDate;
	}


	public void setLeaveEndDate(LocalDateTime leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}


	public int getStaffId() {
		return staffId;
	}


	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}


	public String getWorkDessemination() {
		return workDessemination;
	}


	public void setWorkDessemination(String workDessemination) {
		this.workDessemination = workDessemination;
	}


	public int getContact() {
		return contact;
	}


	public void setContact(int contact) {
		this.contact = contact;
	}


	public String getReasonForRejection() {
		return reasonForRejection;
	}


	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}


	
	

	

}
