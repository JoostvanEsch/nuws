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
    private static List<String> nunllijst = new ArrayList<>();

    public LinkList() {
    }

    public static void generateList() throws IOException {

        URL url = new URL("https://www.nu.nl/");

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

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
        	if (startMarker == 1 && stopMarker == 0 && line.contains("<a href") && line.contains(".html") && !(line.contains("/video")) && !(line.contains("/livestream")) && !(line.contains("/advertorial")) && !(line.contains("/nushop"))) {
        		relevantCode.add("https://www.nu.nl" + line.substring(line.indexOf("/"), (line.indexOf(".html")+5)));
                        
             
        	}
        }
        System.out.println("relevant code testline");
        System.out.println(relevantCode.get(10));
        for (String s : relevantCode) {
        		if (true) {
        			Scraper sc = new Scraper(s);
        			nunllijst.add("<a href=\"" + s + "\" target=\"nunl_iframe\">" + sc.scrapeTitle(new URL(s)) +"</a>");
        		}
        }
        System.out.println(nunllijst.get(0));
        
        
    }

    public static List<String> getNunllijst() {
        return nunllijst;
    }
 
}