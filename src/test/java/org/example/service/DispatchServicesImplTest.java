package org.example.service;

import org.example.dto.request.*;
import org.example.dto.response.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.spi.ImageReaderWriterSpi;

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
        CheckInfoRequest request1 = new CheckInfoRequest();
        request1.setEmail("Eletu@gmail.com");
        System.out.println(dispatchServices.checkInformation(request1));
        //dispatchServices.atSenderAddress("Eletu@gmail.com");
        AtSenderAddressRequest request = new AtSenderAddressRequest();
        request.setEmail("Eletu@gmail.com");
        AtSenderAddressResponse response = dispatchServices.atSenderAddress(request);
        assertEquals("You have reached the sender address", response.getMessage());
    }

    @Test
    public void testMessage(){
        messageRequest request = new messageRequest();
        request.setEmail("Eletu@gmail.com");
        request.setChat("hello im on my way");
        dispatchServices.message(request);
    }

    @Test
    public void testPackageDelivered(){
        CompletedTripRequest request = new CompletedTripRequest();
        request.setEmail("Eletu@gmail.com");
        CompletedTripResponse response = dispatchServices.packageDelivered(request);
        assertEquals("package delivered",response.getMessage());
    }


    @Test
    public void testLogout(){
        DispatchLogOutRequest request = new DispatchLogOutRequest();
        request.setEmail("Eletu@gmail.com");
        DispatchLogOutResponse response = dispatchServices.logout(request);
        assertEquals("logout successful",response.getMessage());
    }


}