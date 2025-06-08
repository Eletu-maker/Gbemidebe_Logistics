package org.example.dto.request;

import lombok.Data;

@Data
public class DispatchRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
