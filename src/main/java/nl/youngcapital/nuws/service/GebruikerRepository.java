package nl.youngcapital.nuws.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.nuws.Gebruiker;


@Component
public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {

}
