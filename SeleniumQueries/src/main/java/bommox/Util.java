package bommox;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
	
	public static class IO {
		
		public static InputStream getIS(String resname) {
			return Util.class.getClassLoader().getResourceAsStream(resname);
		}
		
		public static URI getURI(String resname) {
			URI result = null;
			try {
				result =  Util.class.getClassLoader().getResource(resname).toURI();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	public static class StringUtil {

		public static boolean containsSomeWord(String string, List<String> words) {
			boolean result = false;
			for (String word : words) {
				Pattern p = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
				if (p.matcher(string).find()) {
					result = true;
					break;
				}
			}
			return result;
		}
		
		public static boolean containsEveryWord(String string, List<String> words) {
			boolean result = true;
			for (String word : words) {
				Pattern p = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
				if (!p.matcher(string).find()) {
					result = false;
					break;
				}
			}
			return result;
		}
		
	}
}
