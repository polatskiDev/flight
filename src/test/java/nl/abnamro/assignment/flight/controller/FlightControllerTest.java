package nl.abnamro.assignment.flight.controller;

import nl.abnamro.assignment.flight.dto.FlightDto;
import nl.abnamro.assignment.flight.dto.FlightPagedList;
import nl.abnamro.assignment.flight.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Orhan Polat
 */
@WebMvcTest(FlightController.class)
@ExtendWith(SpringExtension.class)
public class FlightControllerTest {

    @MockBean
    private FlightService flightService;

    List<FlightDto> flights = new ArrayList<>();

    @BeforeEach
    void setUp() {
        flights = Arrays.asList(
                FlightDto.builder()
                        .id(1L)
                        .flightNumber("A101")
                        .origin("AMS")
                        .destination("IST")
                        .departureTime(LocalTime.of(8,55))
                        .arrivalTime(LocalTime.of(12,0))
                        .price(100.0)
                        .build(),
                FlightDto.builder()
                        .id(2L)
                        .flightNumber("B101")
                        .origin("AMS")
                        .destination("IST")
                        .departureTime(LocalTime.of(11,55))
                        .arrivalTime(LocalTime.of(15,35))
                        .price(100.0)
                        .build());
    }

    @Test
    public void testGetFlights() {

        // Arrange
        String origin = "AMS";
        String destination = "IST";
        int pageNumber = 0;
        int pageSize = 10;
        String[] sort = new String[0];

        FlightPagedList pagedList = new FlightPagedList(flights, PageRequest.of(0,10),flights.size());

        when(flightService.getFlightsWithOriginAndDestination(origin, destination, PageRequest.of(pageNumber, pageSize), sort))
                .thenReturn(pagedList);

        FlightController flightController = new FlightController(flightService);

        // Act
        ResponseEntity<FlightPagedList> responseEntity = flightController.getFlights(origin, destination, pageNumber, pageSize, sort);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pagedList, responseEntity.getBody());
    }

    @Test
    public void testGetFlightsSorted() {
        String origin = "AMS";
        String destination = "IST";
        int pageNumber = 0;
        int pageSize = 10;
        String sortDuration = "duration,desc";
        String sortPrice = "prices,asc";
        String[] sort =new String[]{sortDuration, sortPrice};

        FlightPagedList pagedList = new FlightPagedList(flights, PageRequest.of(0,10),flights.size());

        when(flightService.getFlightsWithOriginAndDestination(origin, destination, PageRequest.of(pageNumber, pageSize), sort))
                .thenReturn(pagedList);

        FlightController flightController = new FlightController(flightService);

        ResponseEntity<FlightPagedList> responseEntity = flightController.getFlights(origin, destination, pageNumber, pageSize, sort);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pagedList, responseEntity.getBody());
    }

}
