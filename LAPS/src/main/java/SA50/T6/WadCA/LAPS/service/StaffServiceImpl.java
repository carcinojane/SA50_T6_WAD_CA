package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

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

	@Transactional
	public Staff findStaffByName(String name) {
		Staff staff = srepo.findByUsername(name); 
		return staff;
	}

	public ArrayList<Staff> findAllStaff() {
		return (ArrayList<Staff>) srepo.findAll();
	}

	@Transactional
	public boolean saveStaff(Staff staff) {
		if(srepo.save(staff)!=null) return true; else return false;
	}

	@Transactional
	public ArrayList<Staff> findSearchStaff(String username) {
		return (ArrayList<Staff>) srepo.findStaffLikeSearchInput(username);
	}

	@Transactional
	public Integer findStaffIdByUsername(String username) {
		return srepo.findStaffIdByUsername(username);
	}

	@Transactional
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

	@Transactional
	public Staff findManagerByUsername(String username) {
		return srepo.findManagerByUsername(username);
	}

	@Transactional
	public void deleteStaff(Staff staff) {
		srepo.delete(staff);

	}

	@Transactional
	public ArrayList<Staff> findAllActiveStaff() {
		return (ArrayList<Staff>) srepo.findAllActiveStaff();
	}

	@Transactional
	public ArrayList<Staff> findAllInActiveStaff() {
		return (ArrayList<Staff>) srepo.findAllInActiveStaff();
	}

	@Transactional
	public ArrayList<Staff> findSubordinates(Staff staff) {
		return (ArrayList<Staff>) srepo.findAllSubordinates(staff);
	}
}
