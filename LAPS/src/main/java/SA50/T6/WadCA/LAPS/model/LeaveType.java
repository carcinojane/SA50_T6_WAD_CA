package SA50.T6.WadCA.LAPS.model;
//import java.io.Serializable;

import javax.persistence.*;

import SA50.T6.WadCA.LAPS.model.Staff.Designation;

@Entity
@IdClass(LeaveTypeId.class)
public class LeaveType{
	//composite key
	@Id
	private Designation designation;
	@Id
	private LType leaveType;
	private float entitlement;
	
	private float minClaim;
	
	public LeaveType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveType(Designation designation, LType leaveType, float entitlement, float minClaim) {
		super();
		this.designation = designation;
		this.leaveType = leaveType;
		this.entitlement = entitlement;
		this.minClaim = minClaim;
	}

	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public LType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LType leaveType) {
		this.leaveType = leaveType;
	}
	public float getEntitlement() {
		return entitlement;
	}
	public void setEntitlement(float entitlement) {
		this.entitlement = entitlement;
	}
	public float getMinClaim() {
		return minClaim;
	}
	public void setMinClaim(float minClaim) {
		this.minClaim = minClaim;
	}



}
