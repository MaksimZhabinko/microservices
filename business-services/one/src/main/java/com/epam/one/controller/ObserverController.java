package com.epam.one.controller;
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

    @Monitor(name = "requestCounter",
            type = DataSourceType.COUNTER,
            level = DataSourceLevel.INFO,
            description = "Total number of requests")
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Monitor(name = "randomGauge",
            type = DataSourceType.GAUGE,
            level = DataSourceLevel.CRITICAL,
            description = "A random gauge")
    private final AtomicInteger randomGauge = new AtomicInteger(0);

    @MonitorTags
    private final TagList tags = BasicTagList.of("controller", "observer", "requestCounter", "atomic");

    @PostConstruct
    public void init() {
        Monitors.registerObject("controller", this);
    }

    @GetMapping("/triggerMetric")
    public String triggerMetric(@RequestParam("with") String triggerWith) {
        requestCounter.incrementAndGet();
        randomGauge.set(RandomUtils.nextInt(100));
        return "Some metrics were changed with " + triggerWith;
    }

}
