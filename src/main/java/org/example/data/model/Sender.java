package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Data
@Document("Sender")
public class Sender extends User{
    @Id
    private String id;
    private String address;
    private LocalDateTime dispatchAtYourAddress;
    private boolean givenDispatchPackage;
    private Receiver receiver;
    private DispatchDriver dispatchDriver;
    private LocalDateTime receiverCollectedPackageFromDispatch;
    private DispatchDriver previousDispatch;
}
