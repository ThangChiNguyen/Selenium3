package Common;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomHelper {

	public static String randomString(int length) {
		return RandomStringUtils.random(length, true, false);
	}
	
	public static int randomNumbers(int length) {
		Random random = new Random();
		int index = random.nextInt(length);
		return index;
	}
	
	public static String randomSpecialString(int length) {
		String specialCharacters = "~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String result = RandomStringUtils.random( length, specialCharacters );		
		return result;
	}
	
}