package de.flightright.task.user;

import de.flightright.task.source.Source;
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
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phone;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    private final Set<Source> sources = new HashSet<>();

    @Builder
    public User(@NonNull String email, @NonNull String phone) {
        this.email = email;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!email.equals(user.email)) return false;
        return phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }
}
