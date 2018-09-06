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
public interface ScheduleMqInterface {

    String invite = "invite";

    String responseInvite = "responseInvite";

    @Output(ScheduleMqInterface.invite)
    MessageChannel invite();

    @Input(ScheduleMqInterface.responseInvite)
    SubscribableChannel responseInvite();

}
