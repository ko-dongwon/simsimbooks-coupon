package simsimbooks.couponserver.domain.user.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.user.entity.User;

import java.time.LocalDate;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate birth;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birth = user.getBirth();
    }
}
