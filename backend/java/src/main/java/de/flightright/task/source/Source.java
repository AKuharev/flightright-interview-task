package de.flightright.task.source;

import lombok.Builder;
import lombok.Data;

/**
 * @author Andrei Kukharau
 * @since 1.0
 */
@Data
@Builder
public class Source {
    private Long id;
    private final String name;
}
