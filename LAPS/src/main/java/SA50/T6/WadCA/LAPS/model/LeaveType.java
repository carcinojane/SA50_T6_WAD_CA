package SA50.T6.WadCA.LAPS.model;
import javax.persistence.*;

@Entity
public class LeaveType {
	//composite key
	@Id
	private String designation;
	@Id
	private String leaveType;
	private int entitlement;
	private float minClaim;
	public LeaveType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveType(String designation, String leaveType, int entitlement, float minClaim) {
		super();
		this.designation = designation;
		this.leaveType = leaveType;
		this.entitlement = entitlement;
		this.minClaim = minClaim;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public int getEntitlement() {
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
