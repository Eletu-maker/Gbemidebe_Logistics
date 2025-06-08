package org.example.dto.request;

import lombok.Data;

@Data
public class SenderLoginRequest {
    private String email;
    private String password;
}
