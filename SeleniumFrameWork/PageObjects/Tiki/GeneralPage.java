package Tiki;

import Common.Utilities;
import Constant.Constant;
import ElementWrapper.Element;
import Enum.Tiki.MenuItem;

public class GeneralPage {

	// Header elements
	protected final Element tikiIcon = new Element("//a[@class='tiki-logo']/img");
	protected final Element cartIcon = new Element("//div[@class='cart-wrapper']/img");
	protected final Element breadCrumbItem = new Element("//div[@class='breadcrumb']/a[@class='breadcrumb-item']");
	
	// Search elements
	protected final Element txtSearch = new Element("//input[@data-view-id='main_search_form_input']");
	protected final Element btnSearch = new Element("//button[@data-view-id='main_search_form_button']");
	
	// Menu elements
	protected final Element btnMenu = new Element("//a[@class='Menu-button']");
	protected final Element menuItem = new Element("//ul[@data-view-id='main_navigation']//span[text()='%s']/parent::a");  
	protected final Element subMenuItem = new Element("//div[@data-view-id='main_navigation_item']//span[@data-view-id='main_navigation_sub_item']//a[text()='%s']");
		  
	// Methods
	
	public boolean isSearchTextBoxDisplayed() {
		return txtSearch.isDisplayed();
	}
	
	public String getSearchPlaceHolderText() {
		return txtSearch.getAttribute("placeholder");
	}
	
	public boolean isSearchButtonDisplayed() {
		return btnSearch.isDisplayed();
	}
	
	public GeneralPage enterSearchForm(String product) {
		txtSearch.sendKeys(product);
		return this;
	}
	
	public GeneralPage submitSearchForm() {
		btnSearch.click();
		return this;
	}
	
	public ProductSearchPage searchProduct(String product) {
		enterSearchForm(product);
		submitSearchForm();
		return new ProductSearchPage().waitForSearchTitleLoading(product);
	}
	
	public boolean isBreadCrumbDisplayed(String value) {
		String[] breadCrumbItems = Utilities.splitString(value, Constant.BREAD_CRUMB_ITEM_REGEX);
		String[] actualBreadCrumbItems = breadCrumbItem.getAllTexts();
		for(int i = 0; i < actualBreadCrumbItems.length; i++) {
			if(breadCrumbItems[i].trim().equals(actualBreadCrumbItems[i].trim())) {
				return true;
			}
		}
		return false;
	}
	
	public ProductSearchPage selectMenuItem(MenuItem menu, String subMenu) {
		btnMenu.click();
		if(subMenu == null) {
			menuItem.getDynamicElement(menu.getMenuItem()).click();
		}
		else{
			menuItem.getDynamicElement(menu.getMenuItem()).hoverTo(Constant.DEFAULT_TIMEOUT);
			subMenuItem.getDynamicElement(subMenu).click();
		}
		return new ProductSearchPage().waitForLoading();
	}
	
}
