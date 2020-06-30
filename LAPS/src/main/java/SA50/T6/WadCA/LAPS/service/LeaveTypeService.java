package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveTypeId;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;

public interface LeaveTypeService {
	public ArrayList<String> findAllLeaveTypeNames();
	public ArrayList<String> findLeaveTypeNamesByDesignation(Designation designation);
	public void save(LeaveType leaveType);
	public ArrayList<LeaveType> findAllLeaveType();
	public LeaveType findLeaveTypeById(LeaveTypeId id);
}
