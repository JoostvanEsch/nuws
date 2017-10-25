package nl.youngcapital.nuws.rest;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.nuws.Gebruiker;
import nl.youngcapital.nuws.NieuwsItem;
import nl.youngcapital.nuws.Scraper;
import nl.youngcapital.nuws.Tag;
import nl.youngcapital.nuws.Admin;
import nl.youngcapital.nuws.service.NuwsService;
import nl.youngcapital.nuws.LinkList;
import nl.youngcapital.nuws.Review;


@RestController
public class NuwsEndpoint {
	
	@Autowired
	NuwsService nuwsservice;
	
	@ResponseBody
        @PostMapping("/updatenewsitemremove/{id}")
        public String removeTagFromNewsitem(@RequestBody String targettag, @PathVariable long id){
            NieuwsItem nieuwsitem = nuwsservice.getFromDatabase(id);
            String oldtag = nieuwsitem.getTags();
            if (oldtag.contains(targettag)){
                String newtag = oldtag.replace(targettag+" ", "");
                nieuwsitem.setTags(newtag);
                nuwsservice.addToDatabase(nieuwsitem); 
            }
            return "";
        }
        
        @ResponseBody
        @PostMapping("/deletetagfromallnewsitems")
         public String deleteTagFromAllNewsitems(@RequestBody String targettag){
             long id = Long.parseLong(targettag);
             Tag tag =  nuwsservice.getOneTagFromDatabase(id);
             String tagstring = tag.getTag();
             List<NieuwsItem> itemlist = nuwsservice.getAllFromDatabase();
             for (NieuwsItem i : itemlist){
                String oldtag = i.getTags();
                if (oldtag.contains(tagstring)){
                    String newtag = oldtag.replace(tagstring+" ", "");
                    i.setTags(newtag);
                    nuwsservice.addToDatabase(i); 
                }
            }
            return "";
        }
         
        @ResponseBody
        @GetMapping("/getnewsitemlist")
        public List<NieuwsItem> getNewsitemList(){
            List<NieuwsItem> NIList = nuwsservice.getAllFromDatabase();
            return NIList;
        }
                
         
        @ResponseBody
        @PostMapping("/updatenewsitemadd/{id}")
        public String addTagToNewsitem(@RequestBody String targettag, @PathVariable long id){
            NieuwsItem nieuwsitem = nuwsservice.getFromDatabase(id);
            String oldtag = nieuwsitem.getTags();
            
            if (!oldtag.contains(targettag)){
                String newtag = oldtag.concat(targettag+" ");
                nieuwsitem.setTags(newtag);
                nuwsservice.addToDatabase(nieuwsitem); 
            }
            return "";
        }
        
        @ResponseBody
	@GetMapping("/nuws2/{id}")
	public NieuwsItem getNuws2(@PathVariable long id) throws IOException{
		NieuwsItem n = nuwsservice.getFromDatabase(id);
		n.setTitle(new Scraper(n.getUrl()).scrapeTitle(new URL(n.getUrl())));
		n.setSub(new Scraper(n.getUrl()).scrapeSubTitle(new URL(n.getUrl())));
		n.setBodytext(new Scraper(n.getUrl()).scrapeBody(new URL(n.getUrl())));
		return n;
	}
	
	@ResponseBody
	@GetMapping("/nuws2")
	public List<NieuwsItem> getNuws2() throws IOException{
		
		List<NieuwsItem> NIList = nuwsservice.getAllFromDatabase();
		for (NieuwsItem n : NIList) {
			n.setTitle(new Scraper(n.getUrl()).scrapeTitle(new URL(n.getUrl())));
			n.setSub(new Scraper(n.getUrl()).scrapeSubTitle(new URL(n.getUrl())));
			n.setBodytext(new Scraper(n.getUrl()).scrapeBody(new URL(n.getUrl())));
		}
		
		return NIList;
	}
	     
	@ResponseBody
	@GetMapping("/nuwstitles")
	public List<NieuwsItem> getNuwsTitles() throws IOException{
		List<NieuwsItem> NITList = nuwsservice.getAllFromDatabase();
		for (NieuwsItem n : NITList) {
			n.setTitle(new Scraper(n.getUrl()).scrapeTitle(new URL(n.getUrl())));
		}
		
		return NITList;
	}
	
	@ResponseBody
	@GetMapping("/nuwstitles/{id}")
	public List<NieuwsItem> getNuwsTitlesByAdmin(@PathVariable long id) throws IOException{
		
		List<NieuwsItem> NITList = nuwsservice.getAllFromDatabase(id);
		for (NieuwsItem n : NITList) {
			n.setTitle(new Scraper(n.getUrl()).scrapeTitle(new URL(n.getUrl())));
		}
		
		return NITList;
	}
	

        @ResponseBody
        @DeleteMapping("/nuwsdelete")
        public String deleteNuws(){
                nuwsservice.deleteAllDatabase();
                return "";
        }

	@ResponseBody
	@GetMapping("/nuwstitlesbytag/{tag}")
	public ArrayList<NieuwsItem> getNuwsTitlesByTag(@PathVariable String tag) throws IOException{
		ArrayList<NieuwsItem> returnList = new ArrayList<NieuwsItem>();
		List<NieuwsItem> NITList = nuwsservice.getAllFromDatabase();
		for (NieuwsItem n : NITList) {
			if (n.getTags().contains(tag)) {
				n.setTitle(new Scraper(n.getUrl()).scrapeTitle(new URL(n.getUrl())));
				returnList.add(n);
			}
		}
		
		return returnList;
	}
        
    	@ResponseBody
    	@GetMapping("/getreviews/{id}")
    	public List<Review> getReviews(@PathVariable long id) {
    		
    		List<Review> reviewList = nuwsservice.getReviewsFromDatabase(id);
    		return reviewList;

    	}
        
        @ResponseBody
        @GetMapping("/adminlogin")
        public List<Admin> getAdmins(){
                List<Admin> adminList = nuwsservice.getAdminsFromDatabase();
                return adminList;
        }
        
        @ResponseBody
        @GetMapping("/getoneadmin/{id}")
        public Admin getOneAdmin(@PathVariable long id){
            Admin admin = nuwsservice.getOneAdminFromDatabase(id);
            return admin;
        }
        
        
        @ResponseBody
        @DeleteMapping("/deleteone/{id}")
        public String deleteOne(@PathVariable long id){
		
                nuwsservice.deleteOneFromDatabase(id);
                return "";
        }
        
        @ResponseBody
        @DeleteMapping("/deletetag/{id}")
        public String deleteTag(@PathVariable long id){
		
                nuwsservice.deleteTagFromDatabase(id);
                return "";
        }
        
        @ResponseBody
	@GetMapping("/nunllinks")
        public List<String> getNUNLlinks()throws IOException {
  
            LinkList.generateList();
            List<String> y = LinkList.getNunlList();
            return y;
        }
        
        @ResponseBody
	@GetMapping("/nuwstags")
	public List<Tag> getTags() throws IOException{
		List<Tag> TagList = nuwsservice.getTagsFromDatabase();
		return TagList;
	}
        

    @ResponseBody
    	@GetMapping("/tagsfromitem/{id}")
    	public ArrayList<String> getTagsFromItem(@PathVariable long id){
    		NieuwsItem k = nuwsservice.getFromDatabase(id);
    		StringBuilder tags = new StringBuilder(k.getTags());
    		ArrayList<String> tagList = new ArrayList<String>();
    		while (true) {
    			if (tags.length() != 0) {
    				tagList.add(tags.substring(0, tags.indexOf(" ")));
    				tags.delete(0, (tags.indexOf(" ")+1));
    			} else {
    				break;
    			}
    		}
    		return tagList;
    	}
        

        @ResponseBody
	@PostMapping("/nuwspost/{id}")
	public String postEntiteit(@RequestBody NieuwsItem nieuwsitem, @PathVariable long id){
             
                Admin admin = nuwsservice.getOneAdminFromDatabase(id);
                nieuwsitem.setAdmin(admin);
                LocalDateTime ldt = LocalDateTime.now();
                String datetime = ldt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm"));
                nieuwsitem.setDatetime(datetime);
		nuwsservice.addToDatabase(nieuwsitem);
                return "";
	}
        
        @ResponseBody
        @PostMapping("/newtag")
	public String postNewTag(@RequestBody Tag tag) throws IOException{
		nuwsservice.addTagToDatabase(tag);
                return "";
	}
	
        @ResponseBody
        @PostMapping("/registeradmin")
	public boolean postAdminRegistration(@RequestBody Admin admin){
		List<Admin> adminList = nuwsservice.getAdminsFromDatabase();
		boolean adminNameTaken = true;
		for (Admin a : adminList) {
                    if (a.getNaam().equalsIgnoreCase(admin.getNaam())){
                        adminNameTaken = false;
                    }         
		}
		if (adminNameTaken) {
                    nuwsservice.addAdminToDatabase(admin);
                    return false;
		} 
                else {
                    return true;
		}
	}        

	@PostMapping("/register")
	public String postRegistration(@RequestBody Gebruiker gebruiker){
		ArrayList<Gebruiker> userList = new ArrayList<Gebruiker>();
		boolean userNameTaken = false;
		
		userList = nuwsservice.getUsersFromDatabase();
		for (Gebruiker g : userList) {
                    if (g.getNaam().equalsIgnoreCase(gebruiker.getNaam())) userNameTaken = true;
		}
		if (userNameTaken == false) {
                    return new String("true");
		} 
                else {
                    return new String("false");
		}
	}
	
	@PostMapping("/addRegistration")
	public boolean postAddRegistration(@RequestBody Gebruiker gebruiker){
		nuwsservice.addToDatabase(gebruiker);
		return true;
	}
	
	@PostMapping("/login")
	public String postLogin(@RequestBody Gebruiker gebruiker){
		
		ArrayList<Gebruiker> userList = new ArrayList<Gebruiker>();
		userList = nuwsservice.getUsersFromDatabase();
		boolean userNameExists = false;
		long uid = 0;
		String uPass = "";
		
		for (Gebruiker g : userList) {
			if (g.getNaam().equalsIgnoreCase(gebruiker.getNaam())) {
				userNameExists = true;
				uid = g.getId();
				uPass = g.getPassword();
			}
		}
		
		if (userNameExists == false) {
		return new String("Username does not exist");
		} else {
			if (gebruiker.getPassword().equals(uPass)) {
				return new String(""+uid);
			} else {
				return new String("Password incorrect");
			}
		}
	}
	
	@PostMapping("/checkLogin")
	public void postCheckLogin(@RequestBody Gebruiker gebruiker){
		//deze later nog aanvullen
	}
	
	@PostMapping("/addReview")
	public void postAddReview(@RequestBody Review review){
		System.out.println("werkt");
		review.setNieuwsitem(review.getNieuwsitem());
		nuwsservice.addToDatabase(review);
	}
	
	@ResponseBody
	@GetMapping("/gebruiker/{id}")
	public Gebruiker getGebruiker(@PathVariable long id){
		Gebruiker n = nuwsservice.getUserFromDatabase(id);
		return n;
	}
}