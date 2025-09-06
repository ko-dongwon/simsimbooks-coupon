package simsimbooks.couponserver.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private LocalDate birth;
}
