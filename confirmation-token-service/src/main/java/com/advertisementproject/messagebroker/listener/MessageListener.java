package com.advertisementproject.messagebroker.listener;

import com.advertisementproject.messagebroker.dto.ConfirmationTokenMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageListener {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "confirmationToken")
    public void confirmationTokenlistener(Object messageObject){
        System.out.println(messageObject);
//        ConfirmationTokenMessage confirmationTokenMessage = (ConfirmationTokenMessage) messageObject;
//        System.out.println(confirmationTokenMessage);
    }
}
