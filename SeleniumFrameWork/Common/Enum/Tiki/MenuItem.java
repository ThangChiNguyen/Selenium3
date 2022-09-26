package Enum.Tiki;

public enum MenuItem {

	PHONE_AND_TABLET("Điện Thoại - Máy Tính Bảng"),
	ELECTRONIC_AND_REFRIGERATOR("Điện Tử - Điện Lạnh"),
	ACCESSORIES_AND_DIGITAL_EQUIPMENT("Phụ Kiện - Thiết Bị Số"),
	LAPTOP_AND_IT_EQUIPMENT("Laptop - Thiết bị IT"),
	CAMERA_AND_RECORDING("Máy Ảnh - Quay Phim"),
	ELECTRIC_APPLIANCE("Điện Gia Dụng"),
	HOUSE_LIFE("Nhà Cửa Đời Sống"),
	GOODS_AND_FOODS("Hàng Tiêu Dùng - Thực Phẩm"),
	TOYS_MOM_AND_BABY("Đồ chơi, Mẹ & Bé"),
	BEAUTY_AND_HEALTH("Làm Đẹp - Sức Khỏe"),
	FASHION_AND_ACCESSORIES("Thời trang - Phụ kiện"),
	SPORT_AND_PICNIC("Thể Thao - Dã Ngoại"),
	MOTOBIKE_CAR_AND_BYCYCLE("Xe Máy, Ô tô, Xe Đạp"),
	INTERNATIONAL_MERCHANDISE("Hàng quốc tế"),
	BOOK_VPP_AND_GIFT("Sách, VPP & Quà Tặng"),
	VOUCHER_SERVICE_AND_CARD("Voucher - Dịch Vụ - Thẻ Cào");
	
	private final String itemName;
	
	MenuItem(final String itemName) {
		this.itemName = itemName;
	}
	
	public String getMenuItem() {
		return this.itemName;
	}
	
}
