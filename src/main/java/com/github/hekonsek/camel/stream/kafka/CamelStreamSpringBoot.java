package com.github.hekonsek.camel.stream.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CamelStreamSpringBoot {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("camel.stream.kafka.brokers", "localhost:9092");
        System.setProperty("camel.stream.kafka.pipe.mypipe.from", "from");
        System.setProperty("camel.stream.kafka.pipe.mypipe.bean", "myPipeProcessor");
        System.setProperty("camel.stream.kafka.pipe.mypipe.to", "to");

        ApplicationContext applicationContext = new SpringApplicationBuilder(CamelStreamSpringBoot.class).run();

        Thread.sleep(5000);
        applicationContext.getBean(ProducerTemplate.class).sendBody("kafka:from?brokers=localhost:9092", new HashMap());

        Thread.sleep(10000);
    }

    @Bean(initMethod = "initialize")
    CamelStreamKafkaBinding myPipe(CamelContext camelContext) {
        return new CamelStreamKafkaBinding(camelContext, "mypipe");
    }

    @Component("myPipeProcessor")
    static public class MyPipeProcessor {

        public String process(String event) {
            return event;
        }

    }

}