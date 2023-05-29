package ru.liftcom.bot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.liftcom.database.entity.CustomOrder;
import ru.liftcom.database.entity.CustomUser;
import ru.liftcom.database.repository.UserRepository;

@Service
public class Producer {
    private static final String TOPIC = "users";
    protected final UserRepository repo;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(UserRepository repo, KafkaTemplate<String, String> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Producer(UserRepository repo) {
        this.repo = repo;
    }


}
