package com.jane.springboot.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PasswordUpdateRequest {
    @NotEmpty(message = "Old Password is mandatory")
    @Size(min = 5, max = 30, message = "Old Password must be at least 5 characters long.")
    private String oldPassword;

    @NotEmpty(message = "New Password is mandatory")
    @Size(min = 5, max = 30, message = "New Password must be at least 5 characters long.")
    private String newPassword;

    @NotEmpty(message = "Confirmed Password is mandatory")
    @Size(min = 5, max = 30, message = "Confirmed Password must be at least 5 characters long.")
    private String newPassword2;

    public PasswordUpdateRequest(String oldPassword, String newPassword, String newPassword2) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPassword2 = newPassword2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
