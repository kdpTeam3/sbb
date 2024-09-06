package com.mysite.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

    private String username;

    // 이메일: 정규 표현식으로 유효한 이메일 형식 검증
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-_.]*@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-_]+(\\.[a-zA-Z0-9-_]+)?$", 
             message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
}
