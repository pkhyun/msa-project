package com.spring_cloud.eureka.client.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> createAuthenticationToken(@Valid @RequestBody LoginRequestDto requestDto) {
        String token = authService.login(requestDto);
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .token(token)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}

