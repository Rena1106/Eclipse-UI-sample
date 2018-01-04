package dialogs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExample {
	
	private static final String REGEX = "\\bcat\\b";
	private static final String INPUT = "cat cat cat catt catt cat";

	public static void main(String[] args) {
		// Set pattern to find in a string
		String line = "This order was placed for QTuv2000! OK?";
		String pattern = "(\\D*)(\\d[0,4])(.*)";
		
		Pattern p = Pattern.compile(REGEX);
		Matcher mat = p.matcher(INPUT);
		int count = 0;
		
		while(mat.find()) {
			count++;
			System.out.println("Match number" + count);
			System.out.println("Start: " + mat.start());
			System.out.println("End: " + mat.end());
		}
				
		
		// create Pattern Object
		Pattern r = Pattern.compile(pattern);
		
		// create matcher Object
		Matcher m = r.matcher(line);
		if(m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
			System.out.println("Found value: " + m.group(3));
		} else {
			System.out.println("No Match");
		}

	}

}
