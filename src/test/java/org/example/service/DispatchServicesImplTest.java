package org.example.service;

import org.example.dto.request.CompletedTripRequest;
import org.example.dto.request.DispatchLoginRequest;
import org.example.dto.request.DispatchRegisterRequest;
import org.example.dto.response.CompletedTripResponse;
import org.example.dto.response.DispatchLoginResponse;
import org.example.dto.response.DispatchRegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DispatchServicesImplTest {
    @Autowired
    private DispatchServicesImpl dispatchServices;

    @Test
    public void testRegisterDispatch(){
        DispatchRegisterRequest request = request();
        DispatchRegisterResponse response = dispatchServices.register(request);
        assertEquals("register successful",response.getMessage());
        DispatchRegisterRequest request1 = request1();
        DispatchRegisterResponse response1 = dispatchServices.register(request1);
        assertEquals("register successful",response1.getMessage());
    }

    private DispatchRegisterRequest request(){
        DispatchRegisterRequest request = new DispatchRegisterRequest();
        request.setEmail("Eletu@gmail.com");
        request.setName("korede Eletu");
        request.setPassword("koredeeletu");
        request.setPhoneNumber("08033472283");
        return request;
    }
    private DispatchRegisterRequest request1(){
        DispatchRegisterRequest request = new DispatchRegisterRequest();
        request.setEmail("korede@gmail.com");
        request.setName("korede Eletu");
        request.setPassword("koredeeletu");
        request.setPhoneNumber("08033472223");
        return request;
    }


    @Test
    public void testLoginDispatch(){
        DispatchLoginRequest request = new DispatchLoginRequest();
        request.setEmail("Eletu@gmail.com");
        request.setPassword("koredeeletu");
        DispatchLoginResponse response = dispatchServices.login(request);
        assertEquals("Login successful" , response.getMessage());
    }

    @Test
    public void testCheckInformation(){
        System.out.println(dispatchServices.checkInformation("Eletu@gmail.com"));
        dispatchServices.atSenderAddress("Eletu@gmail.com");
    }

    @Test
    public void testPackageDelivered(){
        CompletedTripRequest request = new CompletedTripRequest();
        request.setEmail("Eletu@gmail.com");
        CompletedTripResponse response = dispatchServices.packageDelivered(request);
        assertEquals("package delivered",response.getMessage());
    }


}