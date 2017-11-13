package com.github.hekonsek.camel.stream.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static json4dummies.Json.fromJson;
import static json4dummies.Json.toJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CamelStreamKafkaBindingSpringTest.Config.class,
        properties = { "camel.stream.kafka.brokers=localhost:9092",
                "camel.stream.kafka.pipe.mypipe.from=from",
                "camel.stream.kafka.pipe.mypipe.bean=deviceEventsProcessor",
                "camel.stream.kafka.pipe.mypipe.to=to" })
public class CamelStreamKafkaBindingSpringTest {

    @Autowired ProducerTemplate producer;

    @Test
    public void should() throws InterruptedException {
        Thread.sleep(5000);
        String device = toJson(new Device());
        producer.sendBody("kafka:from?brokers=localhost:9092", device);
    }

    @SpringBootApplication
    static public class Config {

        @Bean(initMethod = "initialize")
        CamelStreamKafkaBinding myPipe(CamelContext camelContext) {
            return new CamelStreamKafkaBinding(camelContext, "mypipe");
        }

        @Component("deviceEventsProcessor")
        static public class DeviceProcessor {

            public String process(String event) {
                Device device = fromJson(event, Device.class);
                return toJson(device);
            }

        }

    }

}
