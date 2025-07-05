package simsimbooks.couponserver.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}
