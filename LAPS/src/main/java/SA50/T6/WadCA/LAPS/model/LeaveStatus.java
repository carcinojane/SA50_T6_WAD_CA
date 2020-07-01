package SA50.T6.WadCA.LAPS.model;

public enum LeaveStatus{
	APPLIED("Applied"),
	APPROVED("Approved"),
	REJECTED("Rejected"),
	CANCELLED("Cancelled"),
	UPDATED("Updated");
	
	private final String displayValue;
	LeaveStatus(String displayValue) 
	{
		this.displayValue=displayValue;
	}
	public String getDisplayValue() {
		return displayValue;
	}
}
