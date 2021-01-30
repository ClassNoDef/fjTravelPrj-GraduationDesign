package com.travel.fj.utils;

public class GenString {

	 
		public static String makeString(int length, 
				boolean isCanRepeat) {
			
			int len = length;
			int n=33;
			
			char[] codes = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
					'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p',
					'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
					'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
					'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	 
			
			char[] result = new char[len];
	 
			if (isCanRepeat) {
				for (int i = 0; i < result.length; i++) {
					
					int r = (int) (Math.random() * n);
	 
				
					result[i] = codes[r];
				}
			} else {
				for (int i = 0; i < result.length; i++) {
				
					int r = (int) (Math.random() * n);

					result[i] = codes[r];
	 
					codes[r] = codes[n - 1];
					n--;
				}
			}
			return String.valueOf(result);
		}

}
