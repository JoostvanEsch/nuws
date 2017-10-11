package nl.youngcapital.nuws.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.nuws.NieuwsItem;
import nl.youngcapital.nuws.service.NuwsService;


@RestController
public class NuwsEndpoint {
	
	@Autowired
	NuwsService nuwsservice;

	@GetMapping("/nuws")
	public String getNuws() {
		//nuwsservice.test(new NieuwsItem());
		return "hallo";
	}
	
	@ResponseBody
	@GetMapping("/nuws2/{id}")
	public NieuwsItem getNuws2(@PathVariable long id) {
		return nuwsservice.getFromDatabase(1);
	}
	
	@ResponseBody
	@GetMapping("/nuws2")
	public List<NieuwsItem> getNuws2() {
		return nuwsservice.getAllFromDatabase();
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
		//System.out.println(nieuwsitem.getUrl());
		nuwsservice.addToDatabase(nieuwsitem);
	}
	
	
	
	
	
}
