package com.epam.two.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.servo.annotations.DataSourceLevel;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;

@RestController
public class ObserverController {
    @Monitor(name = "requestCounterSecondApp",
            type = DataSourceType.COUNTER,
            level = DataSourceLevel.INFO,
            description = "Total number of requests in the second observer")
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Monitor(name = "randomGaugeSecondApp",
            type = DataSourceType.GAUGE,
            level = DataSourceLevel.CRITICAL,
            description = "A random gauge in the second observer")
    private final AtomicInteger randomGauge = new AtomicInteger(0);

    @MonitorTags
    private final TagList tags = BasicTagList.of("controller2", "observer2", "requestCounter2", "atomic2");

    @PostConstruct
    public void init() {
        Monitors.registerObject("controller2", this);
    }

    @GetMapping("/triggerController2Metric")
    public String triggerMetric(@RequestParam("with") String triggerWith) {
        requestCounter.incrementAndGet();
        randomGauge.set(RandomUtils.nextInt(100));
        return "Some metrics were in the Controller 2 changed with " + triggerWith;
    }

}
