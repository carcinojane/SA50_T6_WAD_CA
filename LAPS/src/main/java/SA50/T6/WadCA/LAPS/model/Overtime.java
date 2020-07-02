package SA50.T6.WadCA.LAPS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Overtime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int overtimeId;
	@NotNull
	private int hours;
	@ManyToOne
	private Staff staff;
	
	public Overtime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Overtime(int hours, Staff staff) {
		super();
		this.hours = hours;
		this.staff = staff;
	}

	public int getOvertimeId() {
		return overtimeId;
	}

	public void setOvertimeId(int overtimeId) {
		this.overtimeId = overtimeId;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	
	
	
}

