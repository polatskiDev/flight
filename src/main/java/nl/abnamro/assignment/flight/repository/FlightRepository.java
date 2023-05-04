package nl.abnamro.assignment.flight.repository;

import nl.abnamro.assignment.flight.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Orhan Polat
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Page<Flight> findAllByOriginAndDestination(String origin, String destination, Pageable pageable);
}
