package SA50.T6.WadCA.LAPS.service;

import SA50.T6.WadCA.LAPS.model.Overtime;

public interface OvertimeService {

	public float FindCompensationLeaveAwarded(int overtime);
	public boolean SaveOvertime(Overtime overtime);
}
