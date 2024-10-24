package org.example.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.common.AbsSdnObject;
import java.util.Properties;
import java.util.List;

public class ErrorProducer extends AbsSdnObject {
    private static ErrorProducer instance;
    private Properties properties;
    private KafkaProducer<String, String> kafkaProducer;

    private ErrorProducer(Properties properties){
        this.properties = properties;
        this.kafkaProducer = new KafkaProducer(properties);
    }
    public static synchronized ErrorProducer getInstance(Properties properties){
        if(instance == null){
            instance = new ErrorProducer(properties);
        }
        return instance;
    }

    public void send(List<ProducerRecord<String,String>> messageList){
        for(ProducerRecord producerRecord: messageList){
            this.kafkaProducer.send(producerRecord);
        }
        this.kafkaProducer.flush();
        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(ProducerRecord<String,String> message){
        this.kafkaProducer.send(message);
    }

    public void close(){
        this.kafkaProducer.close();
    }
}
