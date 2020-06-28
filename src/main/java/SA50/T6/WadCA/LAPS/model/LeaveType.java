package SA50.T6.WadCA.LAPS.model;
//import java.io.Serializable;

import javax.persistence.*;

import SA50.T6.WadCA.LAPS.model.Staff.Designation;

@Entity
@IdClass(LeaveTypeId.class)
public class LeaveType{
	//composite key
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Id
	private Designation designation;
	private String leaveType;
	private float entitlement;
	private float minClaim;
	
	public LeaveType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveType(Designation designation, String leaveType, int entitlement, float minClaim) {
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
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public float getEntitlement() {
		return entitlement;
	}
	public void setEntitlement(int entitlement) {
		this.entitlement = entitlement;
	}
	public float getMinClaim() {
		return minClaim;
	}
	public void setMinClaim(float minClaim) {
		this.minClaim = minClaim;
	}



}
