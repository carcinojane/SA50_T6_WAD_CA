package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.repo.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	LeaveRepository lrepo;

	@Override
	public ArrayList<LeaveRecord> findLeaveRecordByStaffId(int staffId) {
		ArrayList<LeaveRecord> records = new ArrayList<LeaveRecord>();
		List<LeaveRecord> leaverecord = lrepo.findAll();
		for (Iterator<LeaveRecord> iterator = leaverecord.iterator(); iterator.hasNext();) {
			LeaveRecord leaveRecord2 = (LeaveRecord) iterator.next();
			records.add(leaveRecord2);
			
		}
		return records; 
	}
	
	@Override
	public ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(int managerId) {
		ArrayList<LeaveRecord> records = new ArrayList<LeaveRecord>();
		List<LeaveRecord> leaverecord = lrepo.findAll();
		for (Iterator<LeaveRecord> iterator = leaverecord.iterator(); iterator.hasNext();) {
			LeaveRecord leaveRecord2 = (LeaveRecord) iterator.next();
			records.add(leaveRecord2);
			
		}
		return records; 
	}

	@Override
	public ArrayList<LeaveRecord> findAll() {
		return (ArrayList<LeaveRecord>) lrepo.findAll();
	}

	@Override
	public LeaveRecord findById(int id) {
		return lrepo.findById(id).get();
	}

	@Override
	public boolean saveLeaveRecord(LeaveRecord leaveRecord) {
		if(lrepo.save(leaveRecord)!= null) return true; else return false;
	}
	
}
