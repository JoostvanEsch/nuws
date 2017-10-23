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
import nl.youngcapital.nuws.Review;



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
    
    @Autowired
    ReviewRepository reviewrepository;
    
	public void addToDatabase(Review review) {
		reviewrepository.save(review);
		//return nieuwsitem;
	}


	public void addToDatabase(NieuwsItem nieuwsitem) {
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
	
	public List<Review> getReviewsFromDatabase(long nieuwsItemId) {
		ArrayList<Review> allReviews = (ArrayList<Review>) reviewrepository.findAll();
		ArrayList<Review> returnList = new ArrayList<Review>();
		
		for (Review r : allReviews) {
			if (r.getNieuwsitem().getId() == nieuwsItemId) {
				returnList.add(r);
			}
		}
		
		return (List<Review>) returnList;
	}
	
	public ArrayList<Gebruiker> getUsersFromDatabase() {
		return (ArrayList<Gebruiker>) gebruikerrepository.findAll();
	}
	
	public Gebruiker getUserFromDatabase(long id) {
		return gebruikerrepository.findOne(id);
	}
	
	public NieuwsItem getFromDatabase(long id) {
		NieuwsItem n = nuwsrepository.findOne(id);
		return n;
	}
        
        public Admin getOneAdminFromDatabase(long id) {
		Admin a = adminrepository.findOne(id);
		return a;
	}
	
	public List<NieuwsItem> getAllFromDatabase() {
		return (List<NieuwsItem>) nuwsrepository.findAll();
	}
	
	public List<NieuwsItem> getAllFromDatabase(long id) {
		List<NieuwsItem> l = (List<NieuwsItem>) nuwsrepository.findAll();
		ArrayList<NieuwsItem> list = new ArrayList<NieuwsItem>();
		for (NieuwsItem i : l) {
			if (i.getAdmin().getId() == id) {
				list.add(i);
			}
		}
		return list;
	}
        
        public List<Tag> getTagsFromDatabase() {
               
		return (List<Tag>) tagrepository.findAll();
	}
        
         public List<Admin> getAdminsFromDatabase() {
                
		return (List<Admin>)adminrepository.findAll();
	}
        
        public void addAdminToDatabase(Admin admin){
            
            adminrepository.save(admin);
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
