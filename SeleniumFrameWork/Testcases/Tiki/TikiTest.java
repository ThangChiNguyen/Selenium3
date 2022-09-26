package Tiki;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import Common.Logger;
import Constant.Constant;
import Enum.Tiki.MenuItem;

public class TikiTest extends BaseTest{
	
	HomePage homePage = new HomePage();
	
	@Test
	public void TestCase001() {
		Logger.info("Test case 001: Verify the product information loaded correctly");
		String searchProduct = "Điện thoại";
		
		Logger.info("Step 1: Navigate to \"TIKI\" website");
		homePage.open(Constant.TIKI_URL);
		
		Logger.verify("Verify that: \"Tìm sản phẩm ...\" textbox is displayed");
		String expectedPlaceHolder = "Tìm sản phẩm, danh mục hay thương hiệu mong muốn ...";
		assertTrue(homePage.isSearchTextBoxDisplayed(), "Search textbox is not displayed as expected");
		assertEquals(homePage.getSearchPlaceHolderText(), expectedPlaceHolder, "'Tìm sản phẩm ...' textbox should be displayed as expected");
		
		Logger.verify("\"Tìm kiếm\" button is displayed");
		assertTrue(homePage.isSearchButtonDisplayed(), "\"Tìm kiếm\" button should be displayed");
		
		Logger.info("Step 2: On home page, enter value in \"Tìm sản phẩm ...\" textbox");
		Logger.info("Step 3: Click \"Tìm kiếm\" button");
		ProductSearchPage productSearchPage = homePage.searchProduct(searchProduct);
		
		Logger.verify("Verify that: Breadcrumb is \"Trang chủ > Điện thoại\"");
		assertTrue(productSearchPage.isBreadCrumbDisplayed("Trang chủ > Điện thoại"), "Breadcrumb 'Trang chủ > Điện thoại' should be displayed as expected");
		
		Logger.verify("Verify that: \"Kết quả tìm kiếm cho `Điện thoại`\" title is displayed");
		assertEquals(productSearchPage.getSearchResultTitle(), "Kết quả tìm kiếm cho `" +searchProduct+ "`", "Search title should be displayed as expected as expected");
		
		Logger.info("Step 4: Select any item from result grid");
		Product product = productSearchPage.getRandomProductInformation();
		ProductPage productPage = productSearchPage.selectProduct(product);
		
		Logger.verify("Verify that: The selected item is displayed correctly in details. (Name, Price)");
		assertEquals(product.getName(), productPage.getProductName(), "Product name should be as same as selected product");
		assertEquals(product.getPrice(), productPage.getProductPrice(), "Product price should be as same as selected product");
	}
	
	@Test
	public void TestCase002() {
		Logger.info("Test case 002: Verify user can filter search condition for product");
		
		Logger.info("Step 1: Navigate to \"TIKI\" website");
		homePage.open(Constant.TIKI_URL);
		
		Logger.info("Step 2: Select left menu");
		ProductSearchPage productSearchPage = homePage.selectMenuItem(MenuItem.ELECTRIC_APPLIANCE, "Lò vi sóng");
		
		Logger.verify("Verify that: Breadcrumb is \"Trang chủ > Điện Gia Dụng > Đồ dùng nhà bếp > Lò vi sóng\"");
		assertTrue(productSearchPage.isBreadCrumbDisplayed("Trang chủ > Điện Gia Dụng > Đồ dùng nhà bếp > Lò vi sóng"), "The Breadcrumb should be displayed as expected");
		
		Logger.verify("Verify that: \"Lò vi sóng\" title is displayed");
		assertEquals(productSearchPage.getSearchResultTitle(), "Lò vi sóng", "'Lò vi sóng' title is not displayed");
		
		Logger.info("Step 3: Select \"Nhà cung cấp\"");
		productSearchPage.selectFilterOption("Nhà cung cấp", "Tiki Trading");
		
		Logger.info("Check on \"TIKINOW Giao nhanh\" checkbox");
		productSearchPage.selectServiceFilterOption("Giao Siêu Tốc 2h");
		
		Logger.info("Enter price range, then select OK button");
		productSearchPage.filterProductByRangeOfPrice(1000000, 2000000);
		
		Logger.verify("\"Nhà cung cấp: Tiki Trading\", Giao hàng nhanh 2h: Có\",  \"Giá: 1.500.000đ đến 3.000.000đ\" are displayed as keyword tags");
		Logger.warning("Tags displayed are: Tiki Trading, 1.000.000đ đến 2.000.000đ and TIKINOW image is selected");
		assertTrue(productSearchPage.isKeyworkTagDisplayed("Nhà cung cấp: Tiki Trading"), "\"Nhà cung cấp: Tiki Trading\" tag is not displayed as expected");
		assertTrue(productSearchPage.isKeyworkTagDisplayed("Giao hàng nhanh 2h: Có"), "\"Giao hàng nhanh 2h: Có\" tag is not displayed as expected");
		assertTrue(productSearchPage.isKeyworkTagDisplayed("Giá: 1.500.000đ đến 3.000.000đ"), "\"Giá: 1.500.000đ đến 3.000.000đ\" tag is not displayed as expected");
		
		Logger.verify("Verify that the name of all displayed items in the result grid contains \"Lò Vi Sóng\"");
		assertTrue(productSearchPage.isTextContainedInProductName("Lò Vi Sóng"), "the name of all displayed items in the result grid does not contains \"Lò Vi Sóng\"");
	
		Logger.verify("Verify that the price of all displayed items in the result grid is within the range \"1.500.000đ - 3.000.000đ\"");
		assertTrue(productSearchPage.isProductDisplayedPriceInRange(1500000, 3000000), "the price of all displayed items in the result grid is within the range \"1.500.000đ - 3.000.000đ\"");
	}

}
