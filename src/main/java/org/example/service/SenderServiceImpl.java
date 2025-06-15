package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.data.model.Receiver;
import org.example.data.model.Sender;
import org.example.data.repository.DispatchDrivers;
import org.example.data.repository.Senders;
import org.example.dto.request.*;
import org.example.dto.response.*;
import org.example.exception.*;
import org.example.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if(senders.existsByEmail(request.getEmail()) || senders.existsByPhoneNumber(request.getPhoneNumber())) throw new RegisterException("Account already exist");
        else save(request);
        response.setMessage("register successful");
        return response;
    }

    @Override
    public SenderLoginResponse login(SenderLoginRequest request) {
        SenderLoginResponse response = new SenderLoginResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if(sender == null)throw new AccountException("Account does not exist");
        if(!sender.validatePassword(request.getPassword()))throw new PasswordException("wrong password");
        sender.setLogin(true);
        senders.save(sender);
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public AddressesResponse orderDispatch(AddressesRequest request) {
        AddressesResponse response = new AddressesResponse();
        Sender sender = senders.findByEmail(request.getSenderEmail());
        if(sender.getPreviousDispatch()== null){
            DispatchDriver rider = dispatchDrivers.getDispatchDriverByAvailable(false).get(0);
            sender.setPreviousDispatch(rider);
        }
        senders.save(sender);
        if(sender.isLogin()) {
            DispatchDriver driver = getDispatch(request);
            if (driver == null) throw new ServiceError("no available rider at the moment");
            sender.setDispatchDriver(driver);
            sender.setPreviousDispatch(driver);
            sender.setAddress(request.getSenderAddress());
            Receiver receiver = new Receiver();
            Validations.validatePhoneNumber(request);
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
        if(!sender.isLogin())  throw new AccountException("You need to login before ordering a ride");
        if(!sender.isGivenDispatchPackage()) {
            DispatchDriver driver = sender.getDispatchDriver();
            sender.setDispatchDriver(null);
            sender.setAddress(null);
            sender.setReceiver(null);
            sender.setTripBegan(null);
            sender.setDispatchAsArrived(false);
            //sender.setSeenRider(false);
            driver.setReceiver(null);
            driver.setAvailable(true);
            driver.setSenderPhoneNumber(null);
            driver.setSenderAddress(null);
            driver.setTripStart(false);
            senders.save(sender);
            dispatchDrivers.save(driver);
            response.setMessage("ride Canceled");
            return response;
        } else throw new SenderException("Trip can't be cancel until package is delivered");

    }

    @Override
    public BeginTripResponse startTrip(BeginTripRequest request) {
        BeginTripResponse response = new BeginTripResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if (sender.isDispatchAsArrived()){
            sender.setGivenDispatchPackage(true);
            sender.getDispatchDriver().setTripStart(true);
            sender.setTripBegan(LocalDateTime.now());
            DispatchDriver driver = sender.getDispatchDriver();
            driver.setTripStart(true);
            dispatchDrivers.save(driver);
            senders.save(sender);
        }else throw new SenderException("Trip can't begin without dispatch receiving the package");
        response.setMessages("Trip have began");
        return response;
    }

    public ArrivedResponse arrived (ArrivedRequest request){
        ArrivedResponse response = new ArrivedResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        sender.setSeenRider(false);
        senders.save(sender);
        response.setMessage("ok");
        return  response;
    }

    @Override
    public SenderLogOutResponse logout(SenderLogOutRequest request) {
        SenderLogOutResponse response = new SenderLogOutResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        if(sender.getTripBegan() != null) throw new ServiceError("can not logout until package has being delivered");
        sender.setLogin(false);
        senders.save(sender);
        response.setMessage("logout successful");
        return response;
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
        senders.save(sender);
    }

    public SenderResponse getSender(SenderRequest request){
        SenderResponse response = new SenderResponse();
        Sender sender = senders.findByEmail(request.getEmail());
        response.setSender(sender);
        return response;

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


