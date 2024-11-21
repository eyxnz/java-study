package prob04;

public class StringUtil {
    public static String concatenate(String[] strArr) {
        StringBuilder sb = new StringBuilder();

        for (String s : strArr) {
            sb.append(s);
        }

        return sb.toString();
    }
}
