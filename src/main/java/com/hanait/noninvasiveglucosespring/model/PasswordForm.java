package com.hanait.noninvasiveglucosespring.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordForm {
    private String newPassword;
    private String newPasswordConfirm;
}
