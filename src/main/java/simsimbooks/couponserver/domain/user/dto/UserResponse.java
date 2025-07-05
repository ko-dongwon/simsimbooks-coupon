package simsimbooks.couponserver.domain.user.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.user.entity.User;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
