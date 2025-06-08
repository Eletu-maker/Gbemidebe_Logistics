package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.dto.request.AddressesRequest;
import org.example.dto.request.CancelRequest;
import org.example.dto.request.SenderLoginRequest;
import org.example.dto.request.SenderRegisterRequest;
import org.example.dto.response.AddressesResponse;
import org.example.dto.response.CancelResponse;
import org.example.dto.response.SenderLoginResponse;
import org.example.dto.response.SenderRegisterResponse;



public interface SenderService {
    SenderRegisterResponse register(SenderRegisterRequest request);
    SenderLoginResponse login(SenderLoginRequest request);
    AddressesResponse orderDispatch(AddressesRequest request);
    CancelResponse cancelTrip(CancelRequest request);

}
