package nl.abnamro.assignment.flight.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Orhan Polat
 */
public class FlightPagedList extends PageImpl<FlightDto> implements Serializable {

    public FlightPagedList(List<FlightDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
