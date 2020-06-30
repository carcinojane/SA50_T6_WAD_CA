package SA50.T6.WadCA.LAPS.service;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.model.Staff;

public interface AdminService {

	public Admin findAdminById(int adminId);
	public Admin findAdminByName(String username);
}
