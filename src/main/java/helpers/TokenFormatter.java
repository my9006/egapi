package helpers;

public class TokenFormatter {

    public static String cookieTokenFormatter(String token) {
        final String[] tokenParts = token.split("[.]");
        return "JCLAIMS=" + tokenParts[1] + ";" +
                "JHEAD=" + tokenParts[0] + ";" +
                "JSIG=" + tokenParts[2];
    }

}
