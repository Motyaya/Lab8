import java.net.*;
import java.util.regex.*;


public class URLDepthPair {
    public static final String URL_REGEX = "(https?:\\/\\/)((\\w+\\.)+\\.(\\w)+[~:\\S\\/]*)";
    public static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX,  Pattern.CASE_INSENSITIVE);

    private URL URL;

    private int depth;

    public URLDepthPair(URL url, int d) throws MalformedURLException {
        URL = new URL(url.toString());

        depth = d;
    }

    @Override public String toString() {
        return "URL: " + URL.toString() + ", Depth: " + depth;
    }


    public URL getURL() {
        return URL;
    }


    public int getDepth() {
        return depth;
    }


    public String getHost() {
        return URL.getHost();
    }


    public String getDocPath() {
        return URL.getPath();
    }


    public static boolean isAbsolute(String url) {
        Matcher URLChecker = URL_PATTERN.matcher(url);
        if (!URLChecker.find()) {
            return false;
        }
        return true;
    }
}