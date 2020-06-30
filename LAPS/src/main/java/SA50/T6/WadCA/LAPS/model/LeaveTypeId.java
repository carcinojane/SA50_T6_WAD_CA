package SA50.T6.WadCA.LAPS.model;

import java.io.Serializable;

import SA50.T6.WadCA.LAPS.model.Staff.Designation;

public class LeaveTypeId implements Serializable{

	private int id;
	private Designation designation;
	
	
	public LeaveTypeId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveTypeId(int id, Designation designation) {
		this.id = id;
		this.designation = designation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + id;
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
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (id != other.id)
			return false;
		return true;
	}


	
	
	
}
