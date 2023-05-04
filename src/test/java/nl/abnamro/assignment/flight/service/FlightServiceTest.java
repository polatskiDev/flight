package nl.abnamro.assignment.flight.service;

import nl.abnamro.assignment.flight.dto.FlightDto;
import nl.abnamro.assignment.flight.dto.FlightPagedList;
import nl.abnamro.assignment.flight.model.Flight;
import nl.abnamro.assignment.flight.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Orhan Polat
 */
@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetFlightsWithOriginAndDestination() {

        String origin = "AMS";
        String destination = "IST";
        int pageNumber = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Flight flight1 = Flight.builder()
                .id(1L)
                .flightNumber("A101")
                .origin("AMS")
                .destination("IST")
                .departureTime(LocalTime.of(8,55))
                .arrivalTime(LocalTime.of(12,35))
                .price(100.0)
                .build();
        Flight flight2 =  Flight.builder()
                .id(2L)
                .flightNumber("B101")
                .origin("AMS")
                .destination("IST")
                .departureTime(LocalTime.of(11,55))
                .arrivalTime(LocalTime.of(15,35))
                .price(100.0)
                .build();

        List<Flight> flightList = Arrays.asList(flight1, flight2);
        Page<Flight> flightPage = new PageImpl<>(flightList, pageRequest, flightList.size());

        FlightPagedList expectedPagedList = new FlightPagedList(flightPage
                .getContent()
                .stream()
                .map(flight -> modelMapper.map(flight,FlightDto.class))
                .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                flightList.size());

        when(flightRepository.findAllByOriginAndDestination(origin, destination, pageRequest))
                .thenReturn(flightPage);

        FlightService flightService = new FlightServiceImpl(flightRepository, modelMapper);

        // Act
        FlightPagedList actualPagedList = flightService.getFlightsWithOriginAndDestination(origin, destination,
                pageRequest);

        // Assert
        assertEquals(expectedPagedList, actualPagedList);

    }
}
