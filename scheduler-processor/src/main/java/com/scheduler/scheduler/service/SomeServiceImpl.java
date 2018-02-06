package com.scheduler.scheduler.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SomeServiceImpl implements SomeService {
    @Override
    public void printMessage(String message) {
        log.info("print message inside service: {}", message);
    }
}
