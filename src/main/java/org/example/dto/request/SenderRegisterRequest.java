package org.example.dto.request;

import lombok.Data;

@Data
public class SenderRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

}
