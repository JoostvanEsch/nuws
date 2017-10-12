package nl.youngcapital.nuws.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.youngcapital.nuws.NieuwsItem;

@Service
@Transactional
public class NuwsService {
	
	@Autowired
	NuwsRepository nuwsrepository;

	public void addToDatabase(NieuwsItem nieuwsitem) {
		nuwsrepository.save(nieuwsitem);
		//return nieuwsitem;
	}
	
	public NieuwsItem getFromDatabase(long id) {
		NieuwsItem n = nuwsrepository.findOne(id);
		return n;
	}
	
	public List<NieuwsItem> getAllFromDatabase() {
		return (List<NieuwsItem>) nuwsrepository.findAll();
	}
        
        public void deleteAllDatabase() {
		nuwsrepository.deleteAll();
		//return nieuwsitem;
	}
	
	
}
