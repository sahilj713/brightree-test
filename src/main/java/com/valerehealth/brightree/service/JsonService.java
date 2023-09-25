package com.valerehealth.brightree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JsonService {

    @Autowired
    ObjectMapper objectMapper;

    public String toJsonOrString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("failed to parse an object to json string", e);
            return obj.toString();
        }
    }

}
