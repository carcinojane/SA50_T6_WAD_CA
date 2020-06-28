package SA50.T6.WadCA.LAPS.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Staff {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int staffId;
	private String username;
	private String password;
	//foreign key
	private int managerId;
	private Designation designation;
	public enum Designation{
		employee,
		manager,
	}
	private float totalCompensationLeave;
	private float totalMedicalLeave;
	private float totalAnnualLeave;
	private LocalDate startDate;
	
	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Staff(String username, String password, int managerId, Designation designation, float totalCompensationLeave,
			float totalMedicalLeave, float totalAnnualLeave, LocalDate startDate) {
		super();
		this.username = username;
		this.password = password;
		this.managerId = managerId;
		this.designation = designation;
		this.totalCompensationLeave = totalCompensationLeave;
		this.totalMedicalLeave = totalMedicalLeave;
		this.totalAnnualLeave = totalAnnualLeave;
		this.startDate = startDate;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public float getTotalCompensationLeave() {
		return totalCompensationLeave;
	}

	public void setTotalCompensationLeave(float totalCompensationLeave) {
		this.totalCompensationLeave = totalCompensationLeave;
	}

	public float getTotalMedicalLeave() {
		return totalMedicalLeave;
	}

	public void setTotalMedicalLeave(float totalMedicalLeave) {
		this.totalMedicalLeave = totalMedicalLeave;
	}

	public float getTotalAnnualLeave() {
		return totalAnnualLeave;
	}

	public void setTotalAnnualLeave(float totalAnnualLeave) {
		this.totalAnnualLeave = totalAnnualLeave;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	
	

}
