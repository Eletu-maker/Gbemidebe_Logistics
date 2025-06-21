package org.example.data.repository;

import org.example.data.model.MessageBox;
import org.example.data.model.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Messageboxes extends MongoRepository<MessageBox,String> {
    MessageBox findBySender(Sender sender);
}
