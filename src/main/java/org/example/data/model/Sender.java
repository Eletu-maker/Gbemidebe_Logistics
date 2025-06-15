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
    private LocalDateTime tripBegan;
    private boolean dispatchAsArrived;
    private boolean seenRider;
    private boolean givenDispatchPackage;
    private Receiver receiver;
    private DispatchDriver dispatchDriver;
    private DispatchDriver previousDispatch;
}
