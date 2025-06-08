package org.example.service;

import org.example.dto.request.DispatchLoginRequest;
import org.example.dto.request.DispatchRegisterRequest;
import org.example.dto.response.DispatchLoginResponse;
import org.example.dto.response.DispatchRegisterResponse;

import java.util.HashMap;

public interface DispatchServices {
    DispatchRegisterResponse register (DispatchRegisterRequest registerRequest);
    DispatchLoginResponse login (DispatchLoginRequest request);
    HashMap<String, String> checkInformation(String email);

}
