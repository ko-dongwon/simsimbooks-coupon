package simsimbooks.couponserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;
import simsimbooks.couponserver.common.util.DtoMapper;
import simsimbooks.couponserver.domain.user.dto.UserCreateRequest;
import simsimbooks.couponserver.domain.user.dto.UserResponse;
import simsimbooks.couponserver.domain.user.dto.UserUpdateRequest;
import simsimbooks.couponserver.domain.user.entity.User;
import simsimbooks.couponserver.domain.user.exception.UserNotFoundException;
import simsimbooks.couponserver.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest requestDto) {
        // 이메일 중복 확인
        if(userRepository.existsByEmail(requestDto.getEmail())) throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);

        User save = userRepository.save(User.of(requestDto.getName(), requestDto.getEmail(),requestDto.getBirth()));
        return DtoMapper.toDto(save, UserResponse.class);
    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return DtoMapper.toDto(user, UserResponse.class);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest requestDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.changeName(requestDto.getName());
        return DtoMapper.toDto(user, UserResponse.class);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
