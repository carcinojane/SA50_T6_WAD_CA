package SA50.T6.WadCA.LAPS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ManagerReport {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer month;
	private Integer staffId;
	private LType leaveType;
	private LeaveStatus leaveStatus;
	
	public ManagerReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ManagerReport(Integer month, Integer staffId, LType leaveType, LeaveStatus leaveStatus) {
		super();
		this.month = month;
		this.staffId = staffId;
		this.leaveType = leaveType;
		this.leaveStatus = leaveStatus;
	}
	
	

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public LType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LType leaveType) {
		this.leaveType = leaveType;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	
	
	
}

	
	
	