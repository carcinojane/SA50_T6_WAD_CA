package SA50.T6.WadCA.LAPS.model;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;

@Entity
public class Leave {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	@OneToOne
	@JoinColumn(name="userId")
	private int managerId;
	private LeaveStatus leaveStatus;
	public enum LeaveStatus{
		APPLIED,
		APPROVED,
		REJECTED,
		CANCEL,
		DELETED,
	}
	
	private String reason;
	//needs mapping 
	@OneToOne     
	@JoinColumn(name="leaveType")
	private LeaveType leaveType;
	private LocalDateTime leaveStartDate;
	private LocalDateTime leaveEndDate;
	private int staffId;
	private String workDessemination;
	private int contact;
	private String reasonForRejection;
	
	
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Leave(int managerId, LeaveStatus leaveStatus, String reason, LeaveType leaveType,
			LocalDateTime leaveStartDate, LocalDateTime leaveEndDate, int staffId) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveType = leaveType;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.staffId = staffId;
	}


	public Leave(int managerId, LeaveStatus leaveStatus, String reason, LeaveType leaveType,
			LocalDateTime leaveStartDate, LocalDateTime leaveEndDate, int staffId, String workDessemination,
			int contact, String reasonForRejection) {
		super();
		this.managerId = managerId;
		this.leaveStatus = leaveStatus;
		this.reason = reason;
		this.leaveType = leaveType;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.staffId = staffId;
		this.workDessemination = workDessemination;
		this.contact = contact;
		this.reasonForRejection = reasonForRejection;
	}
	
	

}
