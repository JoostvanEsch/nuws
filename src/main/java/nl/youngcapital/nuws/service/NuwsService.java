package nl.youngcapital.nuws.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.youngcapital.nuws.Gebruiker;
import nl.youngcapital.nuws.NieuwsItem;
import nl.youngcapital.nuws.Tag;
import nl.youngcapital.nuws.Admin;


@Service
@Transactional
public class NuwsService {
	
    @Autowired
    NuwsRepository nuwsrepository;
        
    @Autowired
    TagRepository tagrepository;
        
    @Autowired
    GebruikerRepository gebruikerrepository;
    
    @Autowired
    AdminRepository adminrepository;

	public void addToDatabase(NieuwsItem nieuwsitem) {
		 System.out.println("service werkt");
                 nuwsrepository.save(nieuwsitem);
		//return nieuwsitem;
	}
        
        public void addTagToDatabase(Tag tag) {
		tagrepository.save(tag);
               
		//return nieuwsitem;
	}
	
	public void addToDatabase(Gebruiker gebruiker) {
		gebruikerrepository.save(gebruiker);
		//return nieuwsitem;
	}
	
	public ArrayList<Gebruiker> getUsersFromDatabase() {
		return (ArrayList<Gebruiker>) gebruikerrepository.findAll();
	}
	
	public NieuwsItem getFromDatabase(long id) {
		NieuwsItem n = nuwsrepository.findOne(id);
		return n;
	}
	
	public List<NieuwsItem> getAllFromDatabase() {
		return (List<NieuwsItem>) nuwsrepository.findAll();
	}
        
        public List<Tag> getTagsFromDatabase() {
               
		return (List<Tag>) tagrepository.findAll();
	}
        
         public List<Admin> getAdminsFromDatabase() {
                System.out.println("AdminService werkt");
		return (List<Admin>)adminrepository.findAll();
	}
        
        public void deleteAllDatabase() {
		nuwsrepository.deleteAll();
		//return nieuwsitem;
	}
        
        public void deleteOneFromDatabase(long id) {
		nuwsrepository.delete(id);
        }
        
        public void deleteTagFromDatabase(long id){
           tagrepository.delete(id);
        }
	
}
