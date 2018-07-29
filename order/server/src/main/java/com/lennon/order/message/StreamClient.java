package com.lennon.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    String INPUT="myMessage";
    String OUTPUT="outPut";
    String OUTPUT2="outPut2";
    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();
    @Output(StreamClient.OUTPUT2)
    MessageChannel output2();
}
