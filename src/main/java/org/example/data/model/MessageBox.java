package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
@Data
@Document("MessageBox")
public class MessageBox {
    @Id
    private String id;
    private ArrayList<String> box;
    private Sender sender;

}
