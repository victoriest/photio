package me.victoriest.photio.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author VictoriEST
 * @date 2018/9/6
 * photio
 */
public interface ContractMqInterface {


    String invite = "invite";

    String responseInvite = "responseInvite";

    @Input(ContractMqInterface.invite)
    SubscribableChannel invite();

    @Output(ContractMqInterface.responseInvite)
    MessageChannel responseInvite();

}
