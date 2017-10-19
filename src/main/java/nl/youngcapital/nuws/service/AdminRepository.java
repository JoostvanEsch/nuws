package nl.youngcapital.nuws.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.nuws.Admin;


@Component
public interface AdminRepository extends CrudRepository<Admin, Long> {

}
