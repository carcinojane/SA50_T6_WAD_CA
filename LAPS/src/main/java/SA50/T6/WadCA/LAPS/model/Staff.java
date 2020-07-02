package SA50.T6.WadCA.LAPS.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int staffId;
	
	@NotNull
	@Column(unique = true)
	private String username;
	@NotNull
	@Size(min = 6, max = 15)
	private String password;
	
	@Email(message="Email should be in a valid format. Eg: aaa@email.com")
	private String email;

	// self-referencing staff to manger: staffId
	@ManyToOne
	@JoinColumn(name = "managerId")
	private Staff manager;

	@OneToMany(mappedBy = "manager")
	private Set<Staff> subordinates = new HashSet<Staff>();

	private Designation designation;

	public enum Designation {
		admin, employee, manager,
	}

	private float totalCompensationLeave;
	private float totalMedicalLeave;
	private float totalAnnualLeave;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date startDate;

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Staff(@NotNull String username, @NotNull @Size(min = 6, max = 15) String password,
			@Email String email, Staff manager, Set<Staff> subordinates, Designation designation,
			float totalCompensationLeave, float totalMedicalLeave, float totalAnnualLeave, Date startDate) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.manager = manager;
		this.subordinates = subordinates;
		this.designation = designation;
		this.totalCompensationLeave = totalCompensationLeave;
		this.totalMedicalLeave = totalMedicalLeave;
		this.totalAnnualLeave = totalAnnualLeave;
		this.startDate = startDate;
	}

	public Staff(@NotNull String username, @NotNull @Size(min = 6, max = 15) String password,@Email String email,
			float totalCompensationLeave, float totalMedicalLeave, float totalAnnualLeave) {
		super();
		this.username = username;
		this.password = password;
		this.email=email;
		this.totalCompensationLeave = totalCompensationLeave;
		this.totalMedicalLeave = totalMedicalLeave;
		this.totalAnnualLeave = totalAnnualLeave;
	}

	public Staff(@NotNull String username, @NotNull @Size(min = 6, max = 15) String password,@Email String email) {
		super();
		this.username = username;
		this.password = password;
		this.email=email;
	}

	public boolean isManager(Staff staff) {
		if (staff.getDesignation() == Designation.manager) {
			return true;
		}
		return false;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setSubordinates(Set<Staff> subordinates) {
		this.subordinates = subordinates;
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
