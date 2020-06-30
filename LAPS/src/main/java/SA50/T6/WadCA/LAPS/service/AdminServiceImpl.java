package SA50.T6.WadCA.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.repo.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminRepository arepo;

	@Override
	public Admin findAdminById(int adminId) {
		return arepo.findById(adminId).get();
	}

	@Override
	public Admin findAdminByName(String username) {
		Admin admin = arepo.findByUsername(username);
		return admin;
	}
}
