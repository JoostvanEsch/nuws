package nl.youngcapital.nuws.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.nuws.NieuwsItem;
import nl.youngcapital.nuws.service.NuwsService;


@RestController
public class NuwsEndpoint {
	
	@Autowired
	NuwsService nuwsservice;

	@GetMapping("/nuws")
	public String getNuws() {
		nuwsservice.test(new NieuwsItem());
		return "hallo";
	}
	
	
	
	
	
}
