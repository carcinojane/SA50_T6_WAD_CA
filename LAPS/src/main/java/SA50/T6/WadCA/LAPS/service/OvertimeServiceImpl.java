package SA50.T6.WadCA.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.Overtime;
import SA50.T6.WadCA.LAPS.repo.OvertimeRepository;

@Service
public class OvertimeServiceImpl implements OvertimeService{
	@Autowired
	OvertimeRepository orepo;

	@Override
	public float FindCompensationLeaveAwarded(int overtime) {
		int daysAwarded = overtime/4;
		float unitsAwarded = daysAwarded/2;
		return unitsAwarded;
	}

	@Override
	public boolean SaveOvertime(Overtime overtime) {
		if(orepo.save(overtime)!= null) return true; else return false;
		
	}
	
}

