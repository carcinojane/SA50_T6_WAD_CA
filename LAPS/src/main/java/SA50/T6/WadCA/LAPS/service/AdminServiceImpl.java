package SA50.T6.WadCA.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.repo.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminRepository arepo;
}
