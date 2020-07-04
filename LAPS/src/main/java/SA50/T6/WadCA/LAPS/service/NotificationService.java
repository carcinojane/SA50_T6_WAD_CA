package SA50.T6.WadCA.LAPS.service;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface NotificationService {

	public void sendNotification(Staff staff, Integer employeeId, String employeeName, String appliedLeaveType);
}
