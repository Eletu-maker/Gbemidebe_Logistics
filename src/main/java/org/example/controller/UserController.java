package org.example.controller;

import org.example.dto.request.*;
import org.example.dto.response.*;
import org.example.service.DispatchServicesImpl;
import org.example.service.SenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private DispatchServicesImpl dispatchServices;
    @Autowired
    private SenderServiceImpl senderService;

    @PostMapping("/registerSender")
    public ResponseEntity<?> registerSender(@RequestBody SenderRegisterRequest request){
        try {
            SenderRegisterResponse response = senderService.register(request);
            return new ResponseEntity<>(new ApiResponse(true,response), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loginSender")
    public ResponseEntity<?> loginSender(@RequestBody SenderLoginRequest request){
        try{
            SenderLoginResponse response = senderService.login(request);
            return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registerRider")
    public ResponseEntity<?> registerRider(@RequestBody DispatchRegisterRequest request){
        try {
            DispatchRegisterResponse response = dispatchServices.register(request);
            return new ResponseEntity<>(new ApiResponse(true,response.getMessage()),HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loginRider")
    public ResponseEntity<?> loginRider(@RequestBody DispatchLoginRequest request){
        try {
            DispatchLoginResponse response = dispatchServices.login(request);
            return new ResponseEntity<>(new ApiResponse(true,response.getMessage()),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orderRide")
    public ResponseEntity<?> orderRide(@RequestBody AddressesRequest request){
        try {
            AddressesResponse response = senderService.orderDispatch(request);
            return new ResponseEntity<>((new ApiResponse(true,response)),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checkInfo")
    public ResponseEntity<?> checkInfo(@RequestBody CheckInfoRequest request){
        try {
            HashMap<String, String> data = dispatchServices.checkInformation( request);
            return new ResponseEntity<>((new ApiResponse(true,data)),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/atSenderAddress")
    public ResponseEntity<?> atSenderAddress(@RequestBody AtSenderAddressRequest request){
        try{
            AtSenderAddressResponse response = dispatchServices.atSenderAddress(request);
            return  new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/startTrip")
    public ResponseEntity<?> startTrip(@RequestBody BeginTripRequest request){
        try {
            BeginTripResponse response = senderService.startTrip(request);
            return  new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/packageDelivered")
    public ResponseEntity<?> packageDelivered(@RequestBody CompletedTripRequest request){
        try {
            CompletedTripResponse response = dispatchServices.packageDelivered(request);
            return  new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancelTrip")
    public ResponseEntity<?> cancelTrip(@RequestBody CancelRequest request){
        try {
            CancelResponse response = senderService.cancelTrip(request);
            return  new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>((new ApiResponse(false,e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



