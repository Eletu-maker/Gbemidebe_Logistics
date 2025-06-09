package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.dto.request.*;
import org.example.dto.response.*;


public interface SenderService {
    SenderRegisterResponse register(SenderRegisterRequest request);
    SenderLoginResponse login(SenderLoginRequest request);
    AddressesResponse orderDispatch(AddressesRequest request);
    CancelResponse cancelTrip(CancelRequest request);
    BeginTripResponse startTrip (BeginTripRequest request);

}
