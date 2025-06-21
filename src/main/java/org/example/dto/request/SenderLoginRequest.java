package org.example.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SenderLoginRequest {
    private String email;

    private String password;
}
