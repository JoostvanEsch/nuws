package nl.youngcapital.nuws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class LinkList {
    private static List<String> nunllist = new ArrayList<>();

    public LinkList() {
    }

    public static void generateList() throws IOException {

        URL url = new URL("https://www.nu.nl/");

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String line = null;
        	ArrayList<String> relevantCode = new ArrayList<String>();
        	String startText = "column-content-background";
        	String stopText = "sidebar";
        	int startMarker = 0;
        	int stopMarker = 0;
        	ArrayList<String> linkList = new ArrayList<String>();
        	
        	
        	
        while ((line = br.readLine()) != null) {
        	if (line.contains(startText)) {startMarker++;}
        	if (line.contains(stopText)) {stopMarker++;}
        	if (startMarker == 1 && stopMarker == 0 && line.contains("<a href") && line.contains(".html") && !(line.contains("/video")) && !(line.contains("/livestream")) && !(line.contains("/liveticker")) && !(line.contains("/advertorial")) && !(line.contains("/nushop")) && !(line.contains("/podcast"))) {
        		relevantCode.add("https://www.nu.nl" + line.substring(line.indexOf("/"), (line.indexOf(".html")+5)));
                        
             
        	}
        }
        
        for (String s : relevantCode) {
        		if (true) {
        			Scraper sc = new Scraper(s);
        			nunllist.add("<a href=\"" + s + "\" target=\"nunl_iframe\">" + sc.scrapeTitle(new URL(s)) +"</a>");
        		}
        }
        
        
        
    }

    public static List<String> getNunlList() {
        return nunllist;
    }
 
}