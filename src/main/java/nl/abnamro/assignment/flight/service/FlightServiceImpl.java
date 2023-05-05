package nl.abnamro.assignment.flight.service;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.flight.dto.FlightDto;
import nl.abnamro.assignment.flight.dto.FlightPagedList;
import nl.abnamro.assignment.flight.model.Flight;
import nl.abnamro.assignment.flight.repository.FlightRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    public FlightPagedList getFlightsWithOriginAndDestination(String origin, String destination, PageRequest pageRequest, String[] sort) {
        LOG.info("getFlightsWithOriginAndDestination");
        if (sort != null && sort.length > 0) {

            LOG.info("getFlightsWitSorting: " + Arrays.toString(sort));

            return getSortedPageable(origin, destination, pageRequest, sort);

        } else {
            LOG.info("getFlightsWithoutSorting");

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

    private FlightPagedList getSortedPageable(String origin, String destination, PageRequest pageRequest, String[] sort) {

        List<Flight> flightList =
                flightRepository.findAllByOriginAndDestination(origin, destination, Pageable.unpaged()).getContent();
        List<Flight> modifiableFlightList = new ArrayList<>(flightList);

        Comparator<Flight> comparator = (flight1, flight2) -> 0;
        if (sort[0].contains(",")){
            //means there are more than 1 fields to sort
            for (String sortField : sort) {
                String[] parts = sortField.split(",");
                String fieldName = parts[0];
                String direction = parts.length > 1 ? parts[1] : "asc";
                Comparator<Flight> fieldComparator = getFieldComparator(fieldName,direction);
                comparator = comparator.thenComparing(fieldComparator);
            }
        } else {
            String direction = sort.length > 1 ? sort[1] : "asc";
            Comparator<Flight> fieldComparator = getFieldComparator(sort[0],direction);
            comparator = comparator.thenComparing(fieldComparator);
        }
        modifiableFlightList.sort(comparator);

        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Flight> pagedFlights = new PageImpl<>(modifiableFlightList, pageable, modifiableFlightList.size());

        return new FlightPagedList(pagedFlights
                .getContent()
                .stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList()),
                PageRequest.of(pagedFlights.getPageable().getPageNumber(),
                        pagedFlights.getPageable().getPageSize()),
                pagedFlights.getTotalElements());

    }

    private Comparator<Flight> getFieldComparator(String sortField, String direction) {
        switch (sortField) {
            case "departureTime":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(Flight::getDepartureTime,Comparator.reverseOrder());
                }
                return Comparator.comparing(Flight::getDepartureTime);
            case "arrivalTime":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(Flight::getArrivalTime,Comparator.reverseOrder());
                }
                return Comparator.comparing(Flight::getArrivalTime);
            case "duration":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(flight -> getDuration(flight.getDepartureTime(), flight.getArrivalTime()),
                            Comparator.reverseOrder());
                }
                return Comparator.comparing(flight -> getDuration(flight.getDepartureTime(), flight.getArrivalTime()));
            case "price":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(Flight::getPrice,Comparator.reverseOrder());
                }
                return Comparator.comparing(Flight::getPrice);
            case "origin":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(Flight::getOrigin,Comparator.reverseOrder());
                }
                return Comparator.comparing(Flight::getOrigin);
            case "destination":
                if ("desc".equalsIgnoreCase(direction)) {
                    return Comparator.comparing(Flight::getDestination,Comparator.reverseOrder());
                }
                return Comparator.comparing(Flight::getDestination);
            default:
                LOG.error("Invalid sort field: " + sortField);
                return (flight1, flight2) -> 0;
        }
    }

    private Duration getDuration(LocalTime departureTime, LocalTime arrivalTime) {
        return Duration.between(departureTime, arrivalTime);
    }
}
