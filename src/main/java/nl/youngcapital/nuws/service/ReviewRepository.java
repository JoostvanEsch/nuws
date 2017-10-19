package nl.youngcapital.nuws.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.nuws.Review;


@Component
public interface ReviewRepository extends CrudRepository<Review, Long> {

}
