package SA50.T6.WadCA.LAPS.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Staff {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int staffId;
	@Column(unique = true)
	private String username;
	private String password;
	
	//self-referencing: staffId
		@ManyToOne
		@JoinColumn(name="managerId")
		private Staff manager;
		
		@OneToMany(mappedBy="manager",cascade={CascadeType.ALL})
		private Set<Staff> subordinates = new HashSet<Staff>();
	
	
	private Designation designation;
	public enum Designation{
		admin,
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
	

	public Staff(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Staff(String username, String password, Staff manager, Designation designation, float totalCompensationLeave,
			float totalMedicalLeave, float totalAnnualLeave, LocalDate startDate) {
		super();
		this.username = username;
		this.password = password;
		this.manager = manager;
		this.designation = designation;
		this.totalCompensationLeave = totalCompensationLeave;
		this.totalMedicalLeave = totalMedicalLeave;
		this.totalAnnualLeave = totalAnnualLeave;
		this.startDate = startDate;
	}
//
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

	

	public Staff getManager() {
		return manager;
	}

	public void setManager(Staff manager) {
		this.manager = manager;
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
	
	public Collection<Staff> getSubordinates(){
		return new ArrayList<Staff>(subordinates);
	}
	
	public void addSubordinates(Staff subordinate){
		if(subordinates.contains(subordinate))
			return;
		subordinates.add(subordinate);
		subordinate.setManager(this);
	}
	
	
	

}
