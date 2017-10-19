package nl.youngcapital.nuws.rest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import nl.youngcapital.nuws.service.NuwsService;
import nl.youngcapital.nuws.LinkList;


@RestController
public class NuwsEndpoint {
	
	@Autowired
	NuwsService nuwsservice;
	
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
	
        @GetMapping("/nuwsdelete")
        public String deleteNuws(){
		System.out.println("endpoint werkt");
                nuwsservice.deleteAllDatabase();
                return "";
        }
        
        @DeleteMapping("/deleteone/{id}")
        public String deleteOne(@PathVariable long id){
		System.out.println("endpoint werkt"+id);
                nuwsservice.deleteOneFromDatabase(id);
                return "";
        }
        
        @DeleteMapping("/deletetag/{id}")
        public String deleteTag(@PathVariable long id){
		System.out.println("endpoint werkt"+id);
                nuwsservice.deleteTagFromDatabase(id);
                return "";
        }
        
        @ResponseBody
	@GetMapping("/nunllinks")
        public List<String> getNUNLlinks()throws IOException {
            System.out.println("getNUNLlinks EndPoint wordt geactiveerd");
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
        
	@PostMapping("/nuwspost")
	public String postEntiteit(@RequestBody NieuwsItem nieuwsitem) throws IOException{
                System.out.println("EndPoint wordt geactiveerd");
		nuwsservice.addToDatabase(nieuwsitem);
                return "";
	}
        
        @PostMapping("/newtag")
	public void postNewTag(@RequestBody Tag tag) throws IOException{
		nuwsservice.addTagToDatabase(tag);
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
		} else {
		return new String("false");
		}
	}
	
	@PostMapping("/addRegistration")
	public void postAddRegistration(@RequestBody Gebruiker gebruiker){
		nuwsservice.addToDatabase(gebruiker);
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
				return new String("Login succes");
			} else {
				return new String("Password incorrect");
			}
		}
	}
	
	@PostMapping("/checkLogin")
	public void postCheckLogin(@RequestBody Gebruiker gebruiker){
		//deze later nog aanvullen
	}
}