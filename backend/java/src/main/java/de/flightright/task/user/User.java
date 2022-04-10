package de.flightright.task.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author Andrei Kukharau
 * @since 1.0
 */
@Data
@Builder
public class User {
    private Long id;
    private final String email;
    private final String phone;
}
