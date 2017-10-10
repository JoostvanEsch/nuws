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

	public NieuwsItem test(NieuwsItem nieuwsitem) {
		nuwsrepository.save(nieuwsitem);
		return nieuwsitem;
	}
	
	
}
