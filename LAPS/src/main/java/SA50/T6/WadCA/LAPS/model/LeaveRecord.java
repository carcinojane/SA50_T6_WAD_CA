package SA50.T6.WadCA.LAPS.model;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class LeaveRecord{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	private int managerId;
	private LeaveStatus leaveStatus;
	
	@NotNull(message = "Reason is mandatory field")
	private String reason;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "From date is mandatory field")
	private LocalDate leaveStartDate;
	private char leaveStartTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "From date is mandatory field")
	private LocalDate leaveEndDate;
	private char leaveEndTime;
	private int staffId;
	@ManyToOne
	@JoinColumn(name="staffId", insertable=false, updatable=false)
	private Staff staff;

//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="id", insertable=false, updatable=false),
//		@JoinColumn(name="designation", insertable=false, updatable=false)
//	})
//	private LeaveType leaveType;

	private LType leaveType;
	private String workDissemination;
	private int contact;
	private String reasonForRejection;
	

	public LeaveRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRecord(int managerId, LeaveStatus leaveStatus, String reason, LocalDate leaveStartDate, char leaveStartTime,
			LocalDate leaveEndDate, char leaveEndTime, int staffId, String workDessemination, int contact) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveStartTime = leaveStartTime;
		this.leaveEndDate = leaveEndDate;
		this.leaveEndTime =leaveEndTime;
		this.staffId = staffId;
		this.workDissemination = workDessemination;
		this.contact = contact;
	}
	
	
	
	//add leave
	public LeaveRecord(int managerId, LeaveStatus leaveStatus,
			@NotNull(message = "Reason is mandatory field") String reason,
			@NotNull(message = "From date is mandatory field") LocalDate leaveStartDate, char leaveStartTime,
			@NotNull(message = "From date is mandatory field") LocalDate leaveEndDate, char leaveEndTime, int staffId,
			LType leaveType, String workDissemination, int contact, String reasonForRejection) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveStartTime = leaveStartTime;
		this.leaveEndDate = leaveEndDate;
		this.leaveEndTime = leaveEndTime;
		this.staffId = staffId;
		this.leaveType = leaveType;
		this.workDissemination = workDissemination;
		this.contact = contact;
		this.reasonForRejection = reasonForRejection;
	}

	public LeaveRecord(int managerId, LeaveStatus leaveStatus,
			@NotNull(message = "Reason is mandatory field") String reason,
			@NotNull(message = "From date is mandatory field") LocalDate leaveStartDate, char leaveStartTime,
			@NotNull(message = "From date is mandatory field") LocalDate leaveEndDate, char leaveEndTime, int staffId,
			Staff staff, LType leaveType, String workDissemination, int contact) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveStartTime = leaveStartTime;
		this.leaveEndDate = leaveEndDate;
		this.leaveEndTime = leaveEndTime;
		this.staffId = staffId;
		this.staff = staff;
		this.leaveType = leaveType;
		this.workDissemination = workDissemination;
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
		this.workDissemination = workDessemination;
		this.contact = contact;
		this.reasonForRejection = reasonForRejection;
	}
	
	


	public LeaveRecord(LeaveStatus leaveStatus, @NotNull(message = "Reason is mandatory field") String reason,
			@NotNull(message = "From date is mandatory field") LocalDate leaveStartDate, char leaveStartTime,
			@NotNull(message = "From date is mandatory field") LocalDate leaveEndDate, char leaveEndTime, int staffId,
			 LType leaveType, String workDissemination, int contact) {
		super();
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveStartDate = leaveStartDate;
		this.leaveStartTime = leaveStartTime;
		this.leaveEndDate = leaveEndDate;
		this.leaveEndTime = leaveEndTime;
		this.staffId = staffId;
		this.leaveType = leaveType;
		this.workDissemination = workDissemination;
		this.contact = contact;
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
	
	


	public String getWorkDissemination() {
		return workDissemination;
	}


	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public void setWorkDissemination(String workDessemination) {
		this.workDissemination = workDessemination;
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public LType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LType leaveType) {
		this.leaveType = leaveType;

		//this.leaveTypeDisplay = leaveType.getLeaveType().getDisplayValue();
	}
	
	public char getLeaveStartTime() {
		return leaveStartTime;
	}

	public void setLeaveStartTime(char leaveStartTime) {
		this.leaveStartTime = leaveStartTime;
	}

	public char getLeaveEndTime() {
		return leaveEndTime;
	}

	public void setLeaveEndTime(char leaveEndTime) {
		this.leaveEndTime = leaveEndTime;
	}
	
	@Override
	public String toString() {
		return "LeaveRecord [leaveStatus=" + leaveStatus + "]";
	}
}
