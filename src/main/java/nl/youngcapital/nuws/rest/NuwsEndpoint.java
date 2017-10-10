package nl.youngcapital.nuws.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/nuws2")
	public NieuwsItem getNuws2() {
		NieuwsItem n = new NieuwsItem("testNieuwsitemUrl");
		nuwsservice.test(n);
		return n;
	}
	
	@GetMapping("/nuws3")
	public ArrayList<Integer> getNuws3() {
		ArrayList<Integer> ar = new ArrayList<Integer>();
		for (int i = 0; i<5; i++) {
			ar.add(i);
		}
		return ar;
	}
	
	@PostMapping("/nuwspost")
	public void postEntiteit(@RequestBody NieuwsItem nieuwsitem) {
		System.out.println("Jojo");
		System.out.println(nieuwsitem.getUrl());
		nuwsservice.test(nieuwsitem);
	}
	
	
	
	
	
}
