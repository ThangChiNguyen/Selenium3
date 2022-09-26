package Tiki;

import Common.RandomHelper;
import Common.Utilities;
import Constant.Constant;
import ElementWrapper.Element;

public class ProductSearchPage extends GeneralPage {

	private final Element searchPageIframe = new Element("//iframe[@id='_hjRemoteVarsFrame']");
	private final Element productSearchTitle = new Element("//div[@class='title']/h1[contains(text(),'%s')]");
	private final Element resultSearchTitle = new Element("//div[@class='title']/h1");
	private final Element productItems = new Element("//a[@class='product-item']//div[@class='name']/span");
	private final Element productItemPrices = new Element("//a[@class='product-item']//div[contains(@class,'price-discount')]/div[@class='price-discount__price']");
	private final Element productNameIndex = new Element("(//a[@class='product-item']//div[@class='name']/span)[%s]");
	private final Element productPriceIndex = new Element("(//a[@class='product-item']//div[contains(@class,'price-discount')]/div[@class='price-discount__price'])[%s]");
	private final Element productName = new Element("//span[contains(text(),'%s')]/ancestor::a[@class='product-item']");
	private final Element lnkFilterOpt = new Element("//div[@data-view-label='%s']//span[text()='%s']");
	private final Element appliedFilterOpt = new Element("//div[@data-view-id='search_selected_filter_container']/p[text()='%s']");
	private final Element lnkServiceFilterOpt = new Element("//label[translate(@data-view-label, '"+Constant.UPPER_CHARS+"', '"+Constant.LOWER_CHARS+"')='%s']");
	private final Element txtPriceFrom = new Element("//div[@data-view-label='Giá']//div[@class='input-group']/input[@placeholder='Giá từ']");
	private final Element txtPriceTo = new Element("//div[@data-view-label='Giá']//div[@class='input-group']/input[@placeholder='Giá đến']");
	private final Element btnFilterPrice = new Element("//div[@data-view-label='Giá']//button[@data-view-id='search_filter_submit_button']");

	// Methods
	public ProductSearchPage waitForLoading() {
		searchPageIframe.waitForPresent(Constant.DEFAULT_TIMEOUT);
		resultSearchTitle.waitForVisibility(Constant.DEFAULT_TIMEOUT);
		return this;
	}
	
	public ProductSearchPage waitForSearchTitleLoading(String product) {
		waitForLoading();
		productSearchTitle.getDynamicElement(product).waitForVisibility(Constant.DEFAULT_TIMEOUT);
		return this;
	}
	
	public String getSearchResultTitle() {
		return resultSearchTitle.getText();
	}
	
	public Product getRandomProductInformation() {
		Utilities.waitForPageLoad(Constant.DEFAULT_TIMEOUT);
		productItems.waitForAllElementsPresent(Constant.DEFAULT_TIMEOUT);
		int totalOfProducts = productItems.getSize();
		int randomProductNumber = RandomHelper.randomNumbers(totalOfProducts);
		if(randomProductNumber==0) {
			randomProductNumber+=1;
		}
		String index = String.valueOf(randomProductNumber);
		String name = productNameIndex.getDynamicElement(index).getText();
		String price = productPriceIndex.getDynamicElement(index).getText();
		return new Product(name, price);
	}
	
	public ProductPage selectProduct(Product product) {
		productName.getDynamicElement(product.getName()).click();
		return new ProductPage().waitForLoading(product);
	}
	
	// Locations, evaluates, prices, brands, colors, suppliers filter
	public ProductSearchPage selectFilterOption(String category, String type) {
		lnkFilterOpt.getDynamicElement(category, type).click();
		return this.waitForLoading();
	}
	
	// Tiki services filter
	public ProductSearchPage selectServiceFilterOption(String serviceType) {
		lnkServiceFilterOpt.getDynamicElement(serviceType.toLowerCase()).click();
		return this.waitForLoading();
	}
	
	public ProductSearchPage filterProductByRangeOfPrice(int from, int to) {
		txtPriceFrom.sendKeys(String.valueOf(from));
		txtPriceTo.sendKeys(String.valueOf(to));
		btnFilterPrice.click();
		return this.waitForLoading();
	}
	
	public boolean isKeyworkTagDisplayed(String tag) {
		return appliedFilterOpt.getDynamicElement(tag).isDisplayed();
	}
	
	public boolean isTextContainedInProductName(String value) {
		productItems.waitForAllElementsPresent(Constant.DEFAULT_TIMEOUT);
		String[] productNames = productItems.getAllTexts();
		for(int i = 0; i < productNames.length; i++) {
			if(productNames[i].contains(value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isProductDisplayedPriceInRange(int fromPrice, int toPrice) {
		String[] productPrices = productItemPrices.getAllTexts();
		for(int i = 0; i < productPrices.length; i++) {
			Utilities.removeSubString(productPrices[i], "₫");
			Utilities.removeAllCharacterInString(productPrices[i], ".");
			int price = Integer.valueOf(productPrices[i]);
			if(price >= fromPrice && price <= toPrice) {
				return true;
			}
		}
		return false;
	}
	
}
