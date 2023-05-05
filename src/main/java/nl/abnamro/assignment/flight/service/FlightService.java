package nl.abnamro.assignment.flight.service;

import nl.abnamro.assignment.flight.dto.FlightPagedList;
import org.springframework.data.domain.PageRequest;

/**
 * @author Orhan Polat
 */
public interface FlightService {

    FlightPagedList getFlightsWithOriginAndDestination(String origin, String destination, PageRequest pageRequest, String[] sort);
}
