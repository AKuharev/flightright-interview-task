package de.flightright.task.source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Andrei Kukharau
 * @since 1.0
 */
@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    Optional<Source> findByName(String name);
}
