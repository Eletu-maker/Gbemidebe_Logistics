package org.example.service;

import org.example.dto.request.*;
import org.example.dto.response.*;

import java.util.HashMap;

public interface DispatchServices {
    DispatchRegisterResponse register (DispatchRegisterRequest registerRequest);
    DispatchLoginResponse login (DispatchLoginRequest request);
    HashMap<String, String> checkInformation(CheckInfoRequest request);
    AtSenderAddressResponse atSenderAddress(AtSenderAddressRequest request);
    CompletedTripResponse packageDelivered (CompletedTripRequest request);
    DispatchLogOutResponse logout(DispatchLogOutRequest request);

}
