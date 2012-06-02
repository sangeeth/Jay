package jay.util;

import java.util.HashSet;
import java.util.Set;

public class LangUtil {
	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static Set<Long> parseLongSet(String commaSeparatedNumbers) {
		Set<Long> longSet = new HashSet<Long>();
		if (isEmpty(commaSeparatedNumbers)) {
			return longSet;
		}
		String[] numbers = commaSeparatedNumbers.trim().split(",");
		for (String number : numbers) {
			longSet.add(Long.parseLong(number.trim()));
		}
		return longSet;
	}
}
