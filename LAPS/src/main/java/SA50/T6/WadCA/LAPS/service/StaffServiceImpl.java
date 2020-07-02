package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public ArrayList<Staff> findSearchStaff(String username) {
		return (ArrayList<Staff>) srepo.findStaffLikeSearchInput(username);
			}

	@Override
	public Integer findStaffIdByUsername(String username) {
		// TODO Auto-generated method stub
		return srepo.findStaffIdByUsername(username);
	}

	@Override

	public ArrayList<String> findAllManagerNames() {
		
			List<Staff> staffs = srepo.findAllManagers();
			ArrayList<String> names = new ArrayList<String>();
			for (Iterator<Staff> iterator = staffs.iterator(); iterator.hasNext();) {
				Staff staff = (Staff) iterator.next();
				names.add(staff.getUsername());
			}
			return names;
		}

	public Staff findStaffObjByUsername(String username) {
		return srepo.findByUsername(username);
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

	@Override
	public Staff findManagerByUsername(String username) {
		return srepo.findManagerByUsername(username);
	}

	@Override
	public void deleteStaff(Staff staff) {
		srepo.delete(staff);
		
	}

	@Override
	public ArrayList<Staff> findAllActiveStaff() {
		return (ArrayList<Staff>) srepo.findAllActiveStaff();
	}

	@Override
	public ArrayList<Staff> findAllInActiveStaff() {
		return (ArrayList<Staff>) srepo.findAllInActiveStaff();
	}

	@Override
	public ArrayList<Staff> findSubordinates(Staff staff) {
		return (ArrayList<Staff>) srepo.findAllSubordinates(staff);
	}
}
