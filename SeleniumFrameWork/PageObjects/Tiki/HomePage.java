package Tiki;

import DriverWrapper.DriverManagement;

public class HomePage extends GeneralPage{

	// Methods
	public HomePage open(String url) {
		DriverManagement.getDriver().navigate().to(url);
		return this;
	}
		
}
