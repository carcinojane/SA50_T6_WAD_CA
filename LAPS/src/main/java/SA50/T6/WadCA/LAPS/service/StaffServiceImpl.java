package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	StaffRepository srepo;

	@Override
	public Staff findStaffById(int staffId) {
		return srepo.findById(staffId).get();
	}

//	@Override
//	public ArrayList<Float> findLeaveBalanceById(int staffId) {
//		Staff staff = srepo.findById(staffId).get();
//		ArrayList<Float> balance = new ArrayList<Float>();
//		balance.add(staff.getTotalAnnualLeave());
//		balance.add(staff.getTotalMedicalLeave());
//		balance.add(staff.getTotalCompensationLeave());
//		return balance;
//	}
	

	
	
	
	
}
