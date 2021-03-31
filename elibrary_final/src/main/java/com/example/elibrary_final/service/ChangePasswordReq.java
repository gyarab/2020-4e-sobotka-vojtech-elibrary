package com.example.elibrary_final.service;

public class ChangePasswordReq {
    private String currentPassword;
    private String newPassword;

    public ChangePasswordReq(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
