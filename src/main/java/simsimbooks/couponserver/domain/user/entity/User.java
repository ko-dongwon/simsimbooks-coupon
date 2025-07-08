package simsimbooks.couponserver.domain.user.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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

    private LocalDate birth;

    private User(String name, String email,LocalDate birth) {
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public static User of(String name, String email, LocalDate birth) {
        return new User(name, email,birth);
    }

    // update method
    public void changeName(@Nullable String name) {
        if(StringUtils.hasText(name)) this.name = name;
    }
}
