package nl.abnamro.assignment.flight.controller;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.flight.dto.FlightPagedList;
import nl.abnamro.assignment.flight.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Orhan Polat
 */
@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {

    private final Logger LOG = LogManager.getLogger(FlightController.class);

    private final FlightService flightService;

    @Value("${default.pageNumber}")
    private Integer defaultPageNumber;
    @Value("${default.pageSize}")
    private Integer defaultPageSize;

    @GetMapping
    public ResponseEntity<FlightPagedList> getFlights(@RequestParam("origin") String origin,
                                                      @RequestParam("destination") String destination,
                                                      @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(name = "sort", required = false) String[] sort) {

        LOG.info("getFlights");

        if (pageNumber == null || pageNumber < 0){
            pageNumber = defaultPageNumber;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = defaultPageSize;
        }

        return new ResponseEntity<>(flightService.getFlightsWithOriginAndDestination(origin, destination
                , PageRequest.of(pageNumber, pageSize), sort)
                , HttpStatus.OK);
    }
}
