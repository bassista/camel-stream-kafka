package com.github.hekonsek.camel.stream.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

public class CamelStreamKafkaBinding {

    private final CamelContext camelContext;

    private final String pipe;

    public CamelStreamKafkaBinding(CamelContext camelContext, String pipe) {
        this.camelContext = camelContext;
        this.pipe = pipe;
    }

    public void initialize() {
        try {
            final String brokers = camelContext.resolvePropertyPlaceholders("{{camel.stream.kafka.brokers}}");

            final String from = camelContext.resolvePropertyPlaceholders("{{camel.stream.kafka.pipe." + pipe + ".from}}");
            final String to = camelContext.resolvePropertyPlaceholders("{{camel.stream.kafka.pipe." + pipe + ".to}}");
            final String bean = camelContext.resolvePropertyPlaceholders("{{camel.stream.kafka.pipe." + pipe + ".bean}}");

            camelContext.addRoutes(new RouteBuilder() {

                @Override public void configure() throws Exception {
                    from("kafka:" + from + "?brokers=" + brokers).
                            bean(bean).
                            to("kafka:" + to + "?brokers=" + brokers);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}