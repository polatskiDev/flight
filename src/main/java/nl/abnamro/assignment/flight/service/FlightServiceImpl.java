package nl.abnamro.assignment.flight.service;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.flight.dto.FlightDto;
import nl.abnamro.assignment.flight.dto.FlightPagedList;
import nl.abnamro.assignment.flight.model.Flight;
import nl.abnamro.assignment.flight.repository.FlightRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Orhan Polat
 */
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService{

    private static final Logger LOG = LogManager.getLogger(FlightServiceImpl.class);

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    @Override
    public FlightPagedList getFlightsWithOriginAndDestination(String origin, String destination, PageRequest pageRequest) {
        LOG.info("getFlightsWithOriginAndDestination");
        Page<Flight> flightList = flightRepository.findAllByOriginAndDestination(origin, destination, pageRequest);
        return new FlightPagedList(flightList
                .getContent()
                .stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList()),
        PageRequest.of(flightList.getPageable().getPageNumber(),
                        flightList.getPageable().getPageSize()),
                        flightList.getTotalElements());
    }
}
