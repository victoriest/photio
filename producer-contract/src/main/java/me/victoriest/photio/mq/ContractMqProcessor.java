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
@EnableBinding(value = {ContractMqInterface.class})
public class ContractMqProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ContractMqInterface contractMqInterface;

    @StreamListener(ContractMqInterface.invite)
    public void invite(Object invitation) {
        logger.info("receive a invitation");
        // TODO receive the invitation
    }


    @SendTo(ContractMqInterface.responseInvite)
    public Object responseInvite(Object invitation) {
        logger.info("response the invite");
        contractMqInterface.responseInvite().send(MessageBuilder.withPayload("response the invite").build());
        return invitation;
    }

}
