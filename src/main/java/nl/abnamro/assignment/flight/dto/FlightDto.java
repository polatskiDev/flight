package nl.abnamro.assignment.flight.dto;

import lombok.*;

import java.time.LocalTime;

/**
 * @author Orhan Polat
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {

    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Double price;
}
