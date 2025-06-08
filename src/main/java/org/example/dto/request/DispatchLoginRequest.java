package org.example.dto.request;

import lombok.Data;

@Data
public class DispatchLoginRequest {
    private String email;
    private String password;
}
