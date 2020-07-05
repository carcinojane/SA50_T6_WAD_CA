package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffService {

	public Staff findStaffById(int staffId);
	public Staff findStaffByName(String username);
	public Staff findManagerByUsername(String username);
	public Staff findStaffObjByUsername(String username);
	public ArrayList<Staff> findAllStaff();
	public ArrayList<Staff> findAllActiveStaff();
	public ArrayList<Staff> findAllInActiveStaff();
	public ArrayList<String> findAllManagerNames();
	public ArrayList<Staff> findSearchStaff(String username);
	public ArrayList<Staff> findSubordinates(Staff staff);
	public boolean saveStaff(Staff staff);
	public Integer findStaffIdByUsername(String username);
	public void deleteStaff(Staff staff);
}
