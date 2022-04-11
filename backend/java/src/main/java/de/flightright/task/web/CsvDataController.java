package de.flightright.task.web;

import de.flightright.task.source.Source;
import de.flightright.task.source.SourceRepository;
import de.flightright.task.user.User;
import de.flightright.task.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

/**
 * @author Andrei Kukharau
 * @since 1.0
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("csv")
@RequiredArgsConstructor
class CsvDataController {
    private final SourceRepository sourceRepository;
    private final UserRepository userRepository;

    @PostMapping
    @Transactional
    public void uploadChunkOfData(@RequestBody List<UserWithSource> chunk) {
        chunk.stream()
                .filter(UserWithSource::isValid)
                .map(this::toUser)
                .forEach(this.userRepository::save);

        chunk.stream()
                .filter(UserWithSource::isValid)
                .collect(groupingBy(UserWithSource::source, mapping(this::toUser, toSet())))
                .entrySet()
                .stream()
                .map(this::toSource)
                .forEach(this.sourceRepository::save);
    }

    @GetMapping
    @Transactional
    public Set<SourceWithNumberOfUsers> getSources() {
        return this.sourceRepository.findAll()
                .stream()
                .map(it -> new SourceWithNumberOfUsers(it.getName(), it.getNumberOfUsers()))
                .collect(toSet());
    }

    private User toUser(UserWithSource userWithSource) {
        return this.userRepository.findByEmailAndPhone(userWithSource.email(), userWithSource.phone())
                .orElse(User.builder()
                        .email(userWithSource.email())
                        .phone(userWithSource.phone())
                        .build());
    }

    private Source toSource(Map.Entry<String, Set<User>> entry) {
        return this.sourceRepository.findByName(entry.getKey())
                .orElse(Source.builder()
                        .name(entry.getKey())
                        .users(entry.getValue())
                        .build());
    }
}
