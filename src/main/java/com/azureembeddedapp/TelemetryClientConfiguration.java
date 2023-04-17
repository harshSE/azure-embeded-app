package com.azureembeddedapp;

import com.microsoft.applicationinsights.TelemetryClient;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class TelemetryClientConfiguration {

  @Bean
  @Singleton
  public TelemetryClient createTelemetryClient() {
    return new TelemetryClient();
  }
}