package org.example.dto.request;

import lombok.Data;

@Data
public class AddressesRequest {
    private String senderEmail;
    private String receiverPhoneNumber;
    private String senderAddress;
    private String finalDestination;
}
