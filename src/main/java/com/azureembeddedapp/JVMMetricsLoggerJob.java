package com.azureembeddedapp;

import com.microsoft.applicationinsights.TelemetryClient;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class JVMMetricsLoggerJob {

  private static final Logger LOG = LoggerFactory.getLogger(JVMMetricsLoggerJob.class);
  private final TelemetryClient telemetryClient;
  private final MeterRegistry meterRegistry;

  public JVMMetricsLoggerJob(TelemetryClient telemetryClient, MeterRegistry meterRegistry) {
    this.telemetryClient = telemetryClient;
    this.meterRegistry = meterRegistry;
  }

  @Scheduled(fixedRate = "10s")
  void computeDBMetrics() {
    Meter activeConnectionMeter = meterRegistry.get("jvm.memory.used").meter();
    activeConnectionMeter
        .measure()
        .forEach(
            measurement -> {
              if (measurement.getStatistic() == Statistic.VALUE) {
                double value = measurement.getValue();
                telemetryClient.trackMetric("cats.hikaricp.connections.active", value);
                LOG.info("cats.hikaricp.connections.active: {}", value);
              }
            });
  }
}
