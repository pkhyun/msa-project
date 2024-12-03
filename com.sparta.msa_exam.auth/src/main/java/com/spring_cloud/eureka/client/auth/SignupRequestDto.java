package com.spring_cloud.eureka.client.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotNull(message = "아이디 입력은 필수입니다.")
    private final String username;

    @NotNull(message = "패스워드 입력은 필수입니다.")
    private final String password;

    @NotNull(message = "닉네임 입력은 필수입니다.")
    private final String nickname;

    @Builder
    public SignupRequestDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
