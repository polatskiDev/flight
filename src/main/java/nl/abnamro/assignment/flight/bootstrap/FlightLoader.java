package nl.abnamro.assignment.flight.bootstrap;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.flight.model.Flight;
import nl.abnamro.assignment.flight.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author Orhan Polat
 */
@Component
@RequiredArgsConstructor
public class FlightLoader implements CommandLineRunner {

    public final FlightRepository flightRepository;
    @Override
    public void run(String... args) throws Exception {

        if (flightRepository.count() == 0) {
           loadFlightRecords();
        }
    }

    private void loadFlightRecords() {
        Flight f1 = Flight.builder()
                .id(1L)
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(LocalTime.of(11,0))
                .arrivalTime(LocalTime.of(17,0))
                .price(850.0)
                .build();

        Flight f2 = Flight.builder()
                .id(2L)
                .flightNumber("B101")
                .origin("AMS")
                .destination("BOM")
                .departureTime(LocalTime.of(12,0))
                .arrivalTime(LocalTime.of(19,30))
                .price(750.0)
                .build();

        Flight f3 = Flight.builder()
                .id(3L)
                .flightNumber("C101")
                .origin("AMS")
                .destination("BLR")
                .departureTime(LocalTime.of(13,0))
                .arrivalTime(LocalTime.of(18,30))
                .price(800.0)
                .build();

        Flight f4 = Flight.builder()
                .id(4L)
                .flightNumber("D101")
                .origin("AMS")
                .destination("MAA")
                .departureTime(LocalTime.of(9,0))
                .arrivalTime(LocalTime.of(15,0))
                .price(600.0)
                .build();

        Flight f5 = Flight.builder()
                .id(1L)
                .flightNumber("E101")
                .origin("MAA")
                .destination("BOM")
                .departureTime(LocalTime.of(16,0))
                .arrivalTime(LocalTime.of(17,30))
                .price(100.0)
                .build();

        Flight f6 = Flight.builder()
                .id(6L)
                .flightNumber("F101")
                .origin("BOM")
                .destination("DEL")
                .departureTime(LocalTime.of(20,30))
                .arrivalTime(LocalTime.of(21,30))
                .price(80.0)
                .build();

        Flight f7 = Flight.builder()
                .id(7L)
                .flightNumber("G101")
                .origin("BOM")
                .destination("DEL")
                .departureTime(LocalTime.of(18,0))
                .arrivalTime(LocalTime.of(19,30))
                .price(100.0)
                .build();

        Flight f8 = Flight.builder()
                .id(1L)
                .flightNumber("A201")
                .origin("LHR")
                .destination("DEL")
                .departureTime(LocalTime.of(11,30))
                .arrivalTime(LocalTime.of(17,0))
                .price(600.0)
                .build();

        Flight f9 = Flight.builder()
                .id(9L)
                .flightNumber("B201")
                .origin("LHR")
                .destination("BOM")
                .departureTime(LocalTime.of(12,35))
                .arrivalTime(LocalTime.of(19,30))
                .price(750.0)
                .build();

        Flight f10 = Flight.builder()
                .id(10L)
                .flightNumber("C201")
                .origin("LHR")
                .destination("BLR")
                .departureTime(LocalTime.of(13,45))
                .arrivalTime(LocalTime.of(18,30))
                .price(800.0)
                .build();

        Flight f11 = Flight.builder()
                .id(11L)
                .flightNumber("D201")
                .origin("LHR")
                .destination("MAA")
                .departureTime(LocalTime.of(9,0))
                .arrivalTime(LocalTime.of(15,0))
                .price(600.0)
                .build();

        Flight f12 = Flight.builder()
                .id(12L)
                .flightNumber("E201")
                .origin("DEL")
                .destination("BOM")
                .departureTime(LocalTime.of(18,45))
                .arrivalTime(LocalTime.of(20,15))
                .price(100.0)
                .build();

        Flight f13 = Flight.builder()
                .id(13L)
                .flightNumber("F201")
                .origin("BOM")
                .destination("DEL")
                .departureTime(LocalTime.of(21,15))
                .arrivalTime(LocalTime.of(22,30))
                .price(80.0)
                .build();

        Flight f14 = Flight.builder()
                .id(14L)
                .flightNumber("G01")
                .origin("BOM")
                .destination("DEL")
                .departureTime(LocalTime.of(20,20))
                .arrivalTime(LocalTime.of(21,30))
                .price(100.0)
                .build();

        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);
        flightRepository.save(f4);
        flightRepository.save(f5);
        flightRepository.save(f6);
        flightRepository.save(f7);
        flightRepository.save(f8);
        flightRepository.save(f9);
        flightRepository.save(f10);
        flightRepository.save(f11);
        flightRepository.save(f12);
        flightRepository.save(f13);
        flightRepository.save(f14);
    }
}
