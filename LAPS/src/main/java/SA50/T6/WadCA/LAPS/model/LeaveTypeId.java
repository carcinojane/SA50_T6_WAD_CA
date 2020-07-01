package SA50.T6.WadCA.LAPS.model;

import java.io.Serializable;

import SA50.T6.WadCA.LAPS.model.Staff.Designation;

public class LeaveTypeId implements Serializable{

	private LType leaveType;
	private Designation designation;
	
	public LeaveTypeId() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveTypeId other = (LeaveTypeId) obj;
		if (designation != other.designation)
			return false;
		if (leaveType != other.leaveType)
			return false;
		return true;
	}
	
	
	
	
	
	



	
	
	
}
