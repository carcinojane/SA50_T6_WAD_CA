package SA50.T6.WadCA.LAPS.service;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffService {
	
	public Staff findStaffById(int staffId);
	public ArrayList<Staff> findAllStaff();
	public boolean saveStaff(Staff staff);
}
