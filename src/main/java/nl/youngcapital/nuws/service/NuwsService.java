package nl.youngcapital.nuws.service;


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
	
	
}
