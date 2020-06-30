package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffService {
	
	public Staff findStaffById(int staffId);

	public Staff findStaffByName(String username);

	public ArrayList<Staff> findAllStaff();
	public boolean saveStaff(Staff staff);
	

}
