package SA50.T6.WadCA.LAPS.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.Overtime;
import SA50.T6.WadCA.LAPS.repo.OvertimeRepository;

@Service
public class OvertimeServiceImpl implements OvertimeService{
	@Autowired
	OvertimeRepository orepo;

	@Transactional
	public float FindCompensationLeaveAwarded(int overtime) {
		int daysAwarded = overtime/4;
		float unitsAwarded = (float)daysAwarded/2;
		return unitsAwarded;
	}

	@Transactional
	public boolean SaveOvertime(Overtime overtime) {
		if(orepo.save(overtime)!= null) return true; else return false;
		
	}
	
}
