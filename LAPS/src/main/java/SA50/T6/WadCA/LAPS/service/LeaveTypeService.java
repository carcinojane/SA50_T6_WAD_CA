package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;

public interface LeaveTypeService {
	public ArrayList<String> findAllLeaveTypeNames();
	public ArrayList<String> findLeaveTypeNamesByDesignation(Designation designation);
	public ArrayList<LeaveType> findAllLeaveType();
	public LeaveType findById(Integer id);
	public LeaveType findLeaveTypeToEdit(LType leaveType,Designation designation);
	public Collection<LType> findByDesignation(Designation designation);
	public List<LeaveType> findLeaveTypeByDesignation(Designation designation);
	public void save(LeaveType leaveType);
	
}
