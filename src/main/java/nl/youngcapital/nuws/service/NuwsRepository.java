package nl.youngcapital.nuws.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.nuws.NieuwsItem;


@Component
public interface NuwsRepository extends CrudRepository<NieuwsItem, Long> {

}
