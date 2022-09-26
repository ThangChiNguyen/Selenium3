package Constant;

import Common.JsonHelper;
import Common.Utilities;

public class Constant {
	
	public static final String TIKI_URL = "https://tiki.vn/";
	public static final String HUB_URL = JsonHelper.getValue(Utilities.getProjectPath() + "\\Grid\\node_config.json", "hub") + "/wd/hub";
	public static final String BREAD_CRUMB_ITEM_REGEX = ">";
	public static final String DEFAULT_DELEMITER = ",";
	public static final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
	
	// Timeout
	public static final int DEFAULT_TIMEOUT = 30;
	
}