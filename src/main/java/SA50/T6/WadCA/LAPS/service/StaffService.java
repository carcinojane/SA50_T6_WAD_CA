package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffService {
//	@Query("SELECT s.totalAnnualLeave, s.totalMedicalLeave, s.totalCompensationLeave FROM Staff s WHERE s.staffId = :staffId")
//	public ArrayList<Double> findLeaveBalanceById (@Param("staffId") int staffId);
	
//	public ArrayList<Float> findLeaveBalanceById(int staffId);
	
	public Staff findStaffById(int staffId);
}
