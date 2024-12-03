package com.spring_cloud.eureka.client.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("해당 id는 이미 사용중입니다");
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("해당 닉네임을 이미 사용중입니다");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(nickname)
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        String accessToken = jwtUtil.createAccessToken(username);
        return "Bearer " + accessToken;
    }
}