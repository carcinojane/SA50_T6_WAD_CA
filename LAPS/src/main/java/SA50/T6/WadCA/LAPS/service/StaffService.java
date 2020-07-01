package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffService {
	
	public Staff findStaffById(int staffId);

	public Staff findStaffByName(String username);

	public ArrayList<Staff> findAllStaff();
	public boolean saveStaff(Staff staff);
	public ArrayList<String> findAllManagerNames();
	public ArrayList<Staff> findSearchStaff(String username);
	public Integer findStaffIdByUsername(String username);
	public Staff findManagerByUsername(String username);
	public void deleteStaff(Staff staff);
	public Staff findStaffObjByUsername(String username);

}
