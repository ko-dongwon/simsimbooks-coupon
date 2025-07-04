package simsimbooks.couponserver.domain.user.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User of(String name, String email) {
        return new User(name, email);
    }

    // update method
    public void changeName(@Nullable String name) {
        Optional.ofNullable(name).ifPresent(n -> this.name = n);
    }
}
