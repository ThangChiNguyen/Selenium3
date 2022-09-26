package Common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHelper {

	public static JSONObject getJsonObject(String filePath) throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader(filePath));
		JSONObject jo = (JSONObject) obj;
		return jo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> toMap(JSONObject object, String key) {
		Map<String, Object> map = ((Map<String, Object>) object.get(key));
		Iterator<Entry<String, Object>> itr1 = map.entrySet().iterator();
		while (itr1.hasNext()) {
			Map.Entry pair = itr1.next();
			map.put((String) pair.getKey(), pair.getValue());
		}
		return map;
	}

	public static String getValue(String filePath, String key) {
		String value = "";
		try {
			value = (String) getJsonObject(filePath).get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getValue(String filePath, String key1, String key2) {
		String value = "";
		try {
			Map<String, Object> mapValue = toMap(getJsonObject(filePath), key1);
			value = (String) mapValue.get(key2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String[] getAllValues(String filePath, String key1, String key2) throws IOException, ParseException {
		FileReader reader = new FileReader(filePath);
		Object obj = new JSONParser().parse(reader);
		JSONArray nodeList = (JSONArray) obj;
		
		String[] values = new String[nodeList.size()];
		
		for(int i = 0; i < nodeList.size(); i++) {
			values[i] = parseJsonObject((JSONObject)nodeList.get(i), key1, key2);
		}
		return values;
	}
	
	private static String parseJsonObject(JSONObject node, String key1, String key2) {
		// Get node object within list
		JSONObject jsonObject = (JSONObject) node.get(key1);

		// Get value of first node
		String name = (String) jsonObject.get(key2);
		return name;
	}
}