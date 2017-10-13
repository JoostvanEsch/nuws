package nl.youngcapital.nuws.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.nuws.Tag;


@Component
public interface TagRepository extends CrudRepository<Tag, Long> {

}
