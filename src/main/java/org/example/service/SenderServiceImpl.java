package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.data.model.Receiver;
import org.example.data.model.Sender;
import org.example.data.repository.DispatchDrivers;
import org.example.data.repository.Senders;
import org.example.dto.request.AddressesRequest;
import org.example.dto.request.CancelRequest;
import org.example.dto.request.SenderLoginRequest;
import org.example.dto.request.SenderRegisterRequest;
import org.example.dto.response.AddressesResponse;
import org.example.dto.response.CancelResponse;
import org.example.dto.response.SenderLoginResponse;
import org.example.dto.response.SenderRegisterResponse;
import org.example.exception.AccountException;
import org.example.exception.PasswordException;
import org.example.exception.RegisterException;
import org.example.exception.ServiceError;
import org.example.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SenderServiceImpl implements SenderService{
    @Autowired
    private Senders senders;
    @Autowired
    private DispatchDrivers dispatchDrivers;
    @Override
    public SenderRegisterResponse register(SenderRegisterRequest request) {
        SenderRegisterResponse response = new SenderRegisterResponse();
        if(senders.existsByEmail(request.getEmail())) throw new RegisterException("Account already exist");
        else save(request);
        response.setMessage("register successful");
        return response;
    }

    @Override
    public SenderLoginResponse login(SenderLoginRequest request) {
        SenderLoginResponse response = new SenderLoginResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if(sender == null)throw new AccountException("Account does not exist");
        if(!sender.getPassword().equals(request.getPassword()))throw new PasswordException("wrong password");
        sender.setLogin(true);
        senders.save(sender);
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public AddressesResponse orderDispatch(AddressesRequest request) {
        AddressesResponse response = new AddressesResponse();
        Sender sender = senders.findByEmail(request.getSenderEmail());

        if(sender.isLogin()) {
            DispatchDriver driver = getDispatch(request);
            if (driver == null) throw new ServiceError("no available rider at the moment");
            sender.setDispatchDriver(driver);
            sender.setPreviousDispatch(driver);
            sender.setAddress(request.getSenderAddress());
            Receiver receiver = new Receiver();
            receiver.setPhoneNumber(request.getReceiverPhoneNumber());
            receiver.setAddress(request.getFinalDestination());
            sender.setReceiver(receiver);
            driver.setAvailable(false);
            driver.setSenderAddress(request.getSenderAddress());
            driver.setSenderPhoneNumber(sender.getPhoneNumber());
            driver.setReceiver(receiver);
            senders.save(sender);
            dispatchDrivers.save(driver);
            response.setMessage("Dispatch rider on his way");
        } else throw new AccountException("You need to login before ordering a ride");
        return response;
    }

    @Override
    public CancelResponse cancelTrip(CancelRequest request) {
        CancelResponse response = new CancelResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if(sender.isLogin()) {
        DispatchDriver driver = sender.getDispatchDriver();
        sender.setDispatchDriver(null);
        sender.setAddress(null);
        sender.setReceiver(null);
        driver.setReceiver(null);
        driver.setAvailable(true);
        driver.setSenderPhoneNumber(null);
        driver.setSenderAddress(null);
        senders.save(sender);
        dispatchDrivers.save(driver);
        response.setMessage("ride Canceled");
        return response;
        } else throw new AccountException("You need to login before ordering a ride");
    }

    private DispatchDriver getDispatch(AddressesRequest request){
        Sender sender = senders.findByEmail(request.getSenderEmail());
        List<DispatchDriver> list = dispatchDrivers.findAllByLogin(true);
        for(DispatchDriver dispatchDriver:list ){

            if (dispatchDriver.isAvailable() && !sender.getPreviousDispatch().getId().equals(dispatchDriver.getId())){
                return dispatchDriver;
            }
        }
        return null;
    }
    private void save(SenderRegisterRequest request){
        Sender sender = new Sender();
        Validations.validateName(request);
        sender.setName(request.getName());
        Validations.validateEmail(request);
        sender.setEmail(request.getEmail());
        Validations.validatePassword(request);
        sender.setPassword(request.getPassword());
        Validations.validatePhoneNumber(request);
        sender.setPhoneNumber(request.getPhoneNumber());
        DispatchDriver defaultObj = new DispatchDriver();
        sender.setPreviousDispatch(defaultObj);
        senders.save(sender);
    }
/*
    @Override
    public SenderLoginResponse login(SenderLoginRequest request){
        SenderLoginResponse response = new SenderLoginResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if ( sender == null) {
            throw new AccountException("Account does not exist");
        }
        if (!sender.getPassword().equals(request.getPassword())) {
            throw new AccountException("Wrong password");
        }
        sender.setLogin(true);
        senders.save(sender);
        response.setMessage("Login successful");
        return response;


    }

    @Override
    public AddressesResponse orderDispatch(AddressesRequest request) {
        AddressesResponse Response = new  AddressesResponse();
        Sender sender = senders.findByEmail(request.getSenderEmail());
        DispatchDriver dispatchDriver = getDispatch();
        if(dispatchDriver == null) throw  new ServiceError("no available dispatch, try again in the next 30 minutes ");
        sender.setAddress(request.getSenderAddress());
        dispatchDriver.setSenderAddress(request.getSenderAddress());
        dispatchDriver.setSenderPhoneNumber(sender.getPhoneNumber());
        Receiver receiver = new Receiver();
        receiver.setAddress(request.getFinalDestination());
        receiver.setPhoneNumber(request.getReceiverPhoneNumber());
        dispatchDriver.setReceiver(receiver);
        sender.setDispatchDriver(dispatchDriver);
        dispatchDriver.setAvailable(false);
        dispatchDrivers.save(dispatchDriver);
        senders.save(sender);
        Response.setMessage("Dispatch rider on his way");
        return Response;
    }

    @Override
    public CancelResponse cancelTrip(CancelRequest request) {
        CancelResponse response = new CancelResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        DispatchDriver dispatchDriver = sender.getDispatchDriver();
        sender.setAddress(null);
        sender.setDispatchDriver(null);
        sender.setReceiver(null);
        sender.
        return null;
    }





 */

}


