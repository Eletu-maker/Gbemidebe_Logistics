package org.example.service;

import org.example.data.model.DispatchDriver;
import org.example.data.model.MessageBox;
import org.example.data.model.Sender;
import org.example.data.repository.DispatchDrivers;
import org.example.data.repository.Messageboxes;
import org.example.data.repository.Senders;
import org.example.dto.request.*;
import org.example.dto.response.*;
import org.example.exception.AccountException;
import org.example.exception.PasswordException;
import org.example.exception.RegisterException;
import org.example.exception.ServiceError;
import org.example.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DispatchServicesImpl implements DispatchServices {
    @Autowired
    private DispatchDrivers dispatchDrivers;
    @Autowired
    private Senders senders;
    @Autowired
    private SenderServiceImpl senderService;
    @Autowired
    private Messageboxes messageboxes;
    @Override
    public DispatchRegisterResponse register(DispatchRegisterRequest request) {
        DispatchRegisterResponse response = new DispatchRegisterResponse();
        if(dispatchDrivers.existsByEmail(request.getEmail()) || dispatchDrivers.existsByPhoneNumber(request.getPhoneNumber())) throw new RegisterException("Account already exist");
        else save(request);
        response.setMessage("register successful");
        return response;
    }

    @Override
    public DispatchLoginResponse login(DispatchLoginRequest request) {
        DispatchLoginResponse response = new DispatchLoginResponse();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        if(driver == null) throw new AccountException("Account does not exist");
        if(!driver.validatePassword(request.getPassword())) throw new PasswordException("wrong password");
        driver.setLogin(true);
        driver.setAvailable(true);
        dispatchDrivers.save(driver);
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public HashMap<String, String> checkInformation(CheckInfoRequest request) {
        HashMap<String, String> map = new HashMap<>();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        map.put("Sender PhoneNumber: ", driver.getSenderPhoneNumber());
        map.put("Sender Address: ", driver.getSenderAddress());
        map.put("Receiver Address: ",driver.getReceiver().getAddress());
        map.put("Receiver PhoneNumber: ",driver.getReceiver().getPhoneNumber());
        return map;
    }

    @Override
    public AtSenderAddressResponse atSenderAddress(AtSenderAddressRequest request) {
        AtSenderAddressResponse response = new AtSenderAddressResponse();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        driver.setAtSenderAddress(true);
        dispatchDrivers.save(driver);
        Sender sender = senders.findByPhoneNumber(driver.getSenderPhoneNumber());
        sender.setDispatchAsArrived(true);
        sender.setSeenRider(true);
        senders.save(sender);
        response.setMessage("You have reached the sender address");
        return response;
    }



    public List<String> message (messageRequest request){
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        Sender sender = senders.findByPhoneNumber(driver.getSenderPhoneNumber());
        MessageBox box = messageboxes.findBySender(sender);
        String words = "Rider: " + request.getChat();
        box.getBox().add(words);
        messageboxes.save(box);
        return box.getBox();
    }

    @Override
    public List<String> messageBox(message request) {
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        Sender sender = senders.findByPhoneNumber(driver.getSenderPhoneNumber());
        MessageBox box = messageboxes.findBySender(sender);
        return box.getBox();
    }

    /*
        @Override
        public void atSenderAddress(String email) {
            DispatchDriver driver = dispatchDrivers.findByEmail(email);
            driver.setAtSenderAddress(true);
            dispatchDrivers.save(driver);
            Sender sender = senders.findByPhoneNumber(driver.getSenderPhoneNumber());
            sender.setDispatchAsArrived(true);
            senders.save(sender);

        }


     */
    @Override
    public CompletedTripResponse packageDelivered(CompletedTripRequest request) {
        CompletedTripResponse response = new CompletedTripResponse();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        dispatchDrivers.save(driver);
        Sender sender = senders.findByPhoneNumber(driver.getSenderPhoneNumber());
        sender.setGivenDispatchPackage(false);
        senders.save(sender);
        CancelRequest cancelRequest = new CancelRequest();
        cancelRequest.setEmail(sender.getEmail());
        senderService.cancelTrip(cancelRequest);
        response.setMessage("package delivered");

        return response;
    }

    @Override
    public DispatchLogOutResponse logout(DispatchLogOutRequest request) {
        DispatchLogOutResponse response = new DispatchLogOutResponse();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        if(driver.isTripStart()) throw new ServiceError("can not logout until package has being delivered");
        driver.setLogin(false);
        dispatchDrivers.save(driver);
        response.setMessage("logout successful");
        return response;
    }




    /*
        @Override
        public DispatchLoginResponse login(DispatchLoginRequest request) {
            DispatchLoginResponse response = new DispatchLoginResponse();
            DispatchDriver dispatchDriver = dispatchDrivers.findByEmail(request.getEmail());
            if(dispatchDriver == null)throw new AccountException("Account does not exist");
            if(!dispatchDriver.getPassword().equals(request.getPassword()))throw new AccountException("Wrong password");
            dispatchDriver.setLogin(true);
            response.setMessage("Login successful");
            return response;
        }


     */
    private void save(DispatchRegisterRequest request){
        DispatchDriver dispatchDriver = new DispatchDriver();
        Validations.validateName(request);
        dispatchDriver.setName(request.getName());
        Validations.validateEmail(request);
        dispatchDriver.setEmail(request.getEmail());
        Validations.validatePassword(request);
        dispatchDriver.setPassword(request.getPassword());
        Validations.validatePhoneNumber(request);
        dispatchDriver.setPhoneNumber(request.getPhoneNumber());
        dispatchDrivers.save(dispatchDriver);
    }

    public DispatchResponse getRider(DispatchRequest request){
        DispatchResponse response = new DispatchResponse();
        DispatchDriver driver = dispatchDrivers.findByEmail(request.getEmail());
        response.setRider(driver);
        return response;
    }


}
