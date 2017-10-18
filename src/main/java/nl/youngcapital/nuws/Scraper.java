package nl.youngcapital.nuws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Scraper {
	
	String source;
	String title;
	String subTitle;
	String body;
	
	public Scraper(String source) throws IOException{
		this.source = source;
		//this.title = scrapeTitle(new URL(source));
		//this.subTitle = scrapeSubTitle(new URL(source));
		//this.body = scrapeBody(new URL(source));
		
	}

	/*
    public static void main(String[] args) throws IOException {
    		
   		Scraper sc = new Scraper("https://www.nu.nl/binnenland/4957793/verdachte-van-terreurdreiging-bij-psv-stadion-niet-vervolgd.html");
        System.out.println(sc.title);
        System.out.println(sc.subTitle);
        System.out.println(sc.body);
        	}
        	*/
    
    
    public String scrapeTitle(URL url) throws IOException{
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        int start = 0;
        int closeDivCount = 0;
        String startString = "title-container";
        ArrayList<String> titleAr = new ArrayList<String>();
        
       //System.out.println("printen start");
        
        while ((line = br.readLine()) != null) {
        	if (line.toLowerCase().contains(startString.toLowerCase())) {
        		start = 1;
        	}
        	if (start == 1) {
        		titleAr.add(line);
                        //System.out.println(line);
            if (line.toLowerCase().contains("</div>")) {
            		closeDivCount++;
            }
            if (closeDivCount == 2) {
            		start = 0;
            }
        	}
        }
        
        
        //System.out.println("printen eind");
       
        if (titleAr.size() > 0 ){
            return titleAr.get(3).trim();
        }
        else{
            return "(deze link heeft geen geldige titel)";
        }
        
    }
    
    public String scrapeSubTitle(URL url) throws IOException{
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        int start = 0;
        int closeDivCount = 0;
        String startString = "item-excerpt";
        ArrayList<String> subTitleAr = new ArrayList<String>();
        
        SubTitleLoop:
        while ((line = br.readLine()) != null) {
        	if (line.toLowerCase().contains(startString.toLowerCase())) {
        		start = 1;
        	}
        	if (start == 1) {
        		subTitleAr.add(line);
            //System.out.println(line);
            if (line.toLowerCase().contains("</div>")) {
            		closeDivCount++;
            }
            if (closeDivCount == 2) {
            		start = 0;
            		break SubTitleLoop;
            }
        	}
        }
        return subTitleAr.get(1).trim();
    }
    
    public String scrapeBody(URL url) throws IOException{
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        int start = 0;
        String startString = "block.article.body";
        String stopString = "=\"block divider\"";
        ArrayList<String> bodyAr = new ArrayList<String>();
        StringBuilder bodySB = new StringBuilder();
        StringBuilder bodyTxt = new StringBuilder();
        
        BodyLoop:
        while ((line = br.readLine()) != null) {
        	if (line.toLowerCase().contains(startString.toLowerCase())) {
        		start = 1;
        	}
        	if (start == 1 && line.toLowerCase().contains("<p>".toLowerCase())) {start = 2;}
        	if (start == 2) {
        		bodyAr.add(line);
            //System.out.println(line);
            if (line.toLowerCase().contains(stopString.toLowerCase())) {
            	break BodyLoop;
            }
        	}
        }
        
        for (String s : bodyAr) {
        		bodySB.append(s.trim());
        }
        while (bodySB.indexOf("<p>") >= 0 || bodySB.indexOf("<a id=\"anchor") >= 0) {
        	
        	if ((bodySB.indexOf("<p>") < bodySB.indexOf("<a id=\"anchor")) || ((bodySB.indexOf("<a id=\"anchor") == -1) && (bodySB.indexOf("<p>") !=-1 ))) {
            if (bodySB.indexOf("<p>", bodySB.indexOf("<p>" + 3)) < bodySB.indexOf("</p>")) {
            		bodyTxt.append(bodySB.substring(bodySB.indexOf("<p>"), (bodySB.indexOf("</p>") + 4)));
            }
        bodySB.delete(0 , (bodySB.indexOf("</p>") + 4));
        	} else if (bodySB.indexOf("<a id=\"anchor") < bodySB.indexOf("<p>") && bodySB.indexOf("<a id=\"anchor") != -1) {
        bodyTxt.append(bodySB.substring(bodySB.indexOf("<a id=\"anchor"), (bodySB.indexOf("</h3>") + 5)));
        bodySB.delete(0 , (bodySB.indexOf("</h3>") + 5));
        } else {
        	break;
        }
        }
        
        return bodyTxt.toString();
    }
}


