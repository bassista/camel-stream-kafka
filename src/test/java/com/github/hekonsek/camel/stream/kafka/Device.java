package com.github.hekonsek.camel.stream.kafka;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter @ToString @EqualsAndHashCode
public class Device {

    private String id;

    private String type;

    private Instant created;

    private Instant updated;

    private Map<String, Object> properties = new LinkedHashMap<>();

}