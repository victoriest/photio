package me.victoriest.photio.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author VictoriEST
 * @date 2018/9/6
 * photio
 */
@Component
@EnableBinding(value = {ScheduleMqInterface.class})
public class ScheduleMqProcessor {

    @Autowired
    ScheduleMqInterface scheduleMqInterface;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener(ScheduleMqInterface.responseInvite)
    public void receiveTheResponseOfInvitation(Object responseOfInvitation) {
        logger.info("receive the response of invitation");
        // TODO receive the response of invitation
    }


    @SendTo(ScheduleMqInterface.invite)
    public Object createInvitation(Object invitation) {
        logger.info("invite");
        scheduleMqInterface.responseInvite().send(MessageBuilder.withPayload("invite").build());
        return invitation;
    }


}
