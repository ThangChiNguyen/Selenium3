package DriverWrapper;

public enum DriverType {

	Chrome("Chrome"), Firefox("Firefox");
	
	private final String value;
	
	DriverType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}