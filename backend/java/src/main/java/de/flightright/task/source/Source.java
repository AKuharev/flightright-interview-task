package de.flightright.task.source;

import de.flightright.task.user.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andrei Kukharau
 */
@Entity
@Table(name = "sources")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_sources",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    private final Set<User> users = new HashSet<>();

    @Builder
    public Source(@NonNull String name, Set<User> users) {
        this.name = name;
        this.users.addAll(users);
    }

    public int getNumberOfUsers() {
        return this.users.size();
    }

    public Source addUser(User user) {
        this.users.add(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (!Objects.equals(id, source.id)) return false;
        return name.equals(source.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
