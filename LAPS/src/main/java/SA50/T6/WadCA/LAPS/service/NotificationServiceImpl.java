package SA50.T6.WadCA.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.Staff;

@Service
public class NotificationServiceImpl implements NotificationService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void sendNotification(Staff staff, Integer employeeId, String employeeName) throws MailException{
		//send email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(staff.getEmail());
		mail.setFrom("leavemanagementsysiss@gmail.com");
		mail.setSubject("Leave Application Notification!");
		mail.setText("Your employee - Employee Id: " + employeeId + ", Employee Name: " + employeeName + " has applied a leave. Please check the leave application system for more details.");
		
		javaMailSender.send(mail);
	}

}
