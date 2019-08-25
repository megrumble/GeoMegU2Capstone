package com.trilogyed.levelUpqueueconsumer;

import com.trilogyed.levelUpqueueconsumer.util.feign.LevelUpServiceClient;
import com.trilogyed.levelUpqueueconsumer.util.messages.Member;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
@Service
public class MessageListener {

    @Autowired
    LevelUpServiceClient client;

    public MessageListener(LevelUpServiceClient client) {
        this.client = client;
    }

    @RabbitListener(queues = LevelUpQueueConsumerApplication.QUEUE_NAME)
    public void receivemessages(Member msg){
        System.out.println(msg.toString());
        if(client != null) {
            client.addPointsToMember(msg);
//        if( msg.getLevelUpId() ==0) {
//            client.addMember(msg);
//        } else {
//            client.updateMember(msg.getLevelUpId(), msg);
//        }
        } else{
            System.out.println("level up client is null");
        }
    }
}
