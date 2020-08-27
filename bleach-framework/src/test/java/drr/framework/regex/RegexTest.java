
package drr.framework.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	private static final Pattern colPattern = Pattern.compile("[A-Z]+");

	private static final Pattern rowPattern = Pattern.compile("\\d+");

	public static void main(String[] args) {
		String a = "AAAAA11";
		Matcher c = colPattern.matcher(a);
		Matcher r = rowPattern.matcher(a);
		System.out.println(c.find());
		System.out.println(c.group());
		System.out.println(r.find());
		System.out.println(r.group());
		System.out.println(r.group());
	}

}
