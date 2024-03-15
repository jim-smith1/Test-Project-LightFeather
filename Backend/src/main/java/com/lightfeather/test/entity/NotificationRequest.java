package com.lightfeather.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String supervisor;

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", supervisor='" + supervisor + '\'' +
                '}';
    }
}