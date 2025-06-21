package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.dto.request.*;
import org.example.dto.response.*;

import java.util.List;


public interface SenderService {
    SenderRegisterResponse register(SenderRegisterRequest request);
    SenderLoginResponse login(SenderLoginRequest request);
    AddressesResponse orderDispatch(AddressesRequest request);
    CancelResponse cancelTrip(CancelRequest request);
    BeginTripResponse startTrip (BeginTripRequest request);
    SenderLogOutResponse logout (SenderLogOutRequest request);
    List<String> message (messageRequest request);
    List<String> messageBox (message request);
}
