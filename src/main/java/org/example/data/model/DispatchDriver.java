package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Dispatch Driver")
public class DispatchDriver extends User{
    @Id
    private String id;
    private boolean available ;
    private String senderPhoneNumber;
    private String senderAddress;
    private boolean tripStart;
    private Receiver receiver;
}
