package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public Staff findStaffByName(String name) {
		Staff staff = srepo.findByUsername(name); 
		return staff;
	}
	
	public ArrayList<Staff> findAllStaff() {
		return (ArrayList<Staff>) srepo.findAll();
	}

	@Override
	public boolean saveStaff(Staff staff) {
		if(srepo.save(staff)!=null) return true; else return false;
	}

	@Override
	public ArrayList<Staff> findAllManager() {
		return (ArrayList<Staff>) srepo.findAllManagers();
	}

	@Override
	public ArrayList<Staff> findSearchStaff(String username) {
		return (ArrayList<Staff>) srepo.findStaffLikeSearchInput(username);
			}

	@Override
	public Integer findStaffIdByUsername(String username) {
		// TODO Auto-generated method stub
		return srepo.findStaffIdByUsername(username);
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
