package org.example.service;

import org.example.dto.request.*;
import org.example.dto.response.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class SenderServiceImplTest {
    @Autowired
    private SenderServiceImpl service;

    @Test
    public void testSenderCanRegister(){
        SenderRegisterRequest request = registerRequest();
        SenderRegisterResponse response = service.register(request);
        assertEquals("register successful",response.getMessage());
    }

    private SenderRegisterRequest registerRequest(){
        SenderRegisterRequest senderRegisterRequest = new SenderRegisterRequest();
        senderRegisterRequest.setName("Eletu Usman");
        senderRegisterRequest.setEmail("usman@gmail.com");
        senderRegisterRequest.setPassword("magic009");
        senderRegisterRequest.setPhoneNumber("09134969393");
        return senderRegisterRequest;
    }

    private SenderLoginRequest request(){
        SenderLoginRequest request = new SenderLoginRequest();
        request.setEmail("usman@gmail.com");
        request.setPassword("magic009");
        return  request;
    }

    @Test
    public  void testLogin(){
        //service.register(registerRequest());
        SenderLoginRequest request = request();
        SenderLoginResponse response = service.login(request);
        assertEquals("Login successful", response.getMessage());

    }

    @Test
    public void testOrderDispatch(){
        AddressesRequest request = new AddressesRequest();
        request.setSenderAddress("sabo");
        request.setFinalDestination("ayobo");
        request.setReceiverPhoneNumber("09133145528");
        request.setSenderEmail("usman@gmail.com");

        AddressesResponse response = service.orderDispatch(request);
        assertEquals("Dispatch rider on his way",response.getMessage());

    }

    @Test
    public void testTrip(){
        BeginTripRequest request = new BeginTripRequest();
        request.setEmail("usman@gmail.com");
        BeginTripResponse response = service.startTrip(request);
        assertEquals("Trip have began",response.getMessages());
    }

    @Test
    public void testCancelRide(){
        CancelRequest request = new CancelRequest();
        request.setEmail("usman@gmail.com");
        CancelResponse response = service.cancelTrip(request);
        assertEquals("ride Canceled",response.getMessage());
    }


}