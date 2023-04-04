package com.epam.one.listener;


import java.util.concurrent.TimeUnit;

import com.epam.one.observer.LoggerMetricObserver;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.CounterToRateMetricTransform;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;

@Component
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Got event {}", event.getSource());
        initMetricsPublishing();
    }

    private void initMetricsPublishing() {
        PollScheduler scheduler = PollScheduler.getInstance();
        if (!scheduler.isStarted()) {
            scheduler.start();
        }

        MetricObserver logObserver = new LoggerMetricObserver("logger-observer");

        MetricObserver logObserverRate = new LoggerMetricObserver("logger-observer-rate");
        MetricObserver transform = new CounterToRateMetricTransform(logObserverRate, 1, TimeUnit.MINUTES);
        PollRunnable task = new PollRunnable(
                new MonitorRegistryMetricPoller(),
                BasicMetricFilter.MATCH_ALL,
                logObserver, transform);
        scheduler.addPoller(task, 1, TimeUnit.MINUTES);
    }

}