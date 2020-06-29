package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.repo.LeaveTypeRepository;

@Service
public class LeaveTypeImpl implements LeaveTypeService {

	@Autowired
	LeaveTypeRepository ltrepo;

	@Override
	public ArrayList<String> findAllLeaveTypeNames() {
		ArrayList<String> leaveTypes = new ArrayList<>();
		List<LeaveType> leaveTypeList = ltrepo.findAll();
		for (Iterator<LeaveType> iterator = leaveTypeList.iterator(); iterator.hasNext();) {
			LeaveType leaveType = (LeaveType) iterator.next();
			leaveTypes.add(leaveType.getLeaveType());
		}
		return leaveTypes;
	}

	@Override
	public ArrayList<String> findLeaveTypeNamesByDesignation(Designation designation) {
		ArrayList<String> leaveTypes = new ArrayList<>();
		List<LeaveType> leaveTypeList = ltrepo.findAll();
		for (Iterator iterator = leaveTypeList.iterator(); iterator.hasNext();) {
			LeaveType leaveType = (LeaveType) iterator.next();
			if(leaveType.getDesignation() == designation) {
				leaveTypes.add(leaveType.getLeaveType());
			}
			
		}
		return leaveTypes;
	} 
}
