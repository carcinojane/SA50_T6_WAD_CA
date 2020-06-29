package SA50.T6.WadCA.LAPS.model;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;


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
	private LocalDate leaveStartDate;
	private LocalDate leaveEndDate;
	private int staffId;
	@ManyToOne
	@JoinColumn(name="staffId", insertable=false, updatable=false)
	private Staff staff;
	@ManyToOne
	//@JoinColumn(name="leaveType", insertable=false, updatable=false)
	@JoinColumns({
		@JoinColumn(name="id", insertable=false, updatable=false),
		@JoinColumn(name="designation", insertable=false, updatable=false)
	})
	private LeaveType leaveType;
	private String workDessemination;
	private int contact;
	private String reasonForRejection;
	
	
	
	
	public LeaveRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRecord(int managerId, LeaveStatus leaveStatus, String reason, LocalDate leaveStartDate,
			LocalDate leaveEndDate, int staffId, String workDessemination, int contact) {
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
	
	public LeaveRecord(int managerId, LeaveStatus leaveStatus, String reason, LocalDate leaveStartDate,
			LocalDate leaveEndDate, int staffId, String workDessemination, int contact, String reasonForRejection) {
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


	public LocalDate getLeaveStartDate() {
		return leaveStartDate;
	}


	public void setLeaveStartDate(LocalDate leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}


	public LocalDate getLeaveEndDate() {
		return leaveEndDate;
	}


	public void setLeaveEndDate(LocalDate leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}


//	public Staff getStaff() {
//		return staff;
//	}
//
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}
	
	


	public String getWorkDessemination() {
		return workDessemination;
	}


	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
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
