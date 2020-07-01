package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveTypeId;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.repo.LeaveTypeRepository;

@Service
public class LeaveTypeImpl implements LeaveTypeService {

	@Autowired
	LeaveTypeRepository ltrepo;
	
//	@Transactional
//	public ArrayList<String> convertEnum(enum e){
//		return Stream.of(LType.values().toString()).collect(Collectors.toCollection(ArrayList::new));
//	}

	@Transactional
	public ArrayList<String> findAllLeaveTypeNames() {
//		ArrayList<String> leaveTypes = new ArrayList<>();
//		List<LeaveType> leaveTypeList = ltrepo.findAll();
//		for (Iterator<LeaveType> iterator = leaveTypeList.iterator(); iterator.hasNext();) {
//			LeaveType leaveType = (LeaveType) iterator.next();
//			leaveTypes.add(leaveType.getLeaveType());
//		}
		//return Stream.of(LType.values()).collect(Collectors.toCollection(ArrayList::new));
		//LType.values().toString();
		return Stream.of(LType.values().toString()).collect(Collectors.toCollection(ArrayList::new));
	}

	@Transactional
	public ArrayList<String> findLeaveTypeNamesByDesignation(Designation designation) {
		ArrayList<String> leaveTypes = new ArrayList<>();
		List<LeaveType> leaveTypeList = ltrepo.findAll();
		for (Iterator<LeaveType> iterator = leaveTypeList.iterator(); iterator.hasNext();) {
			LeaveType leaveType = (LeaveType) iterator.next();
			if(leaveType.getDesignation() == designation) {
				leaveTypes.add(leaveType.getLeaveType().toString());
			}
			
		}
		return leaveTypes;
	}

	@Transactional
	public void save(LeaveType leaveType) {
		ltrepo.save(leaveType);
		
	}

	@Transactional
	public ArrayList<LeaveType> findAllLeaveType() {
		return (ArrayList<LeaveType>) ltrepo.findAll();
	}

	@Override
	public LeaveType findLeaveTypeToEdit(LType leaveType, Designation designation) {
		LeaveType record=ltrepo.findLeaveTypeToEdit(leaveType, designation);
		return record;
	}
	
}
