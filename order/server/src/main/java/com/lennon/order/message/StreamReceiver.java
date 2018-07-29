package com.lennon.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.OUTPUT)
    public String process(Object message){
      log.info("StreamReceiver--Input:{}",message);
      return "ok";
    }


    @StreamListener(StreamClient.OUTPUT)
    @SendTo(StreamClient.OUTPUT2)
    public String process(String message){
        log.info("StreamReceiver--out:{}",message);
        return "ok2";
    }

    @StreamListener(StreamClient.OUTPUT2)
    public void process2(String message){
        log.info("StreamReceiver--out2:{}",message);
    }
}
