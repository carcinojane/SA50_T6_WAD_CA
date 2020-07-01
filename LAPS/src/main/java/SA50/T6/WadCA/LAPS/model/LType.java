package SA50.T6.WadCA.LAPS.model;

public enum LType{
	AnnualLeave("Annual Leave"),
	MedicalLeave("Medical Leave"),
	Compensation("Compensation");
	
	private final String displayValue;
	private LType(String displayValue) {
		this.displayValue=displayValue;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	
	
}




