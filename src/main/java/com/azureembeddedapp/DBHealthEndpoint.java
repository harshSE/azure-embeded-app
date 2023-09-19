package com.azureembeddedapp;

import static io.micronaut.http.HttpResponse.status;

import io.micrometer.core.instrument.MeterRegistry;
import io.micronaut.health.HealthStatus;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import io.micronaut.management.health.indicator.HealthResult;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

@Endpoint(id = "dbhealth", defaultEnabled = true, defaultSensitive = false)
public class DBHealthEndpoint {

  public static final LongAdder counter = new LongAdder();

  public static final String HIKARICP_CONNECTIONS = "hikaricp.connections";
  public static final String DB_HEALTH = "DBHealth";
  public static final String HIKARICP_CONNECTIONS_ACTIVE = "hikaricp.connections.active";
  public static final String HIKARICP_CONNECTIONS_IDLE = "hikaricp.connections.idle";
  public static final String HIKARICP_CONNECTIONS_PENDING = "hikaricp.connections.pending";
  public static final String HIKARICP_CONNECTIONS_MAX = "hikaricp.connections.max";
  public static final String HIKARICP_CONNECTIONS_MIN = "hikaricp.connections.min";
  private final MeterRegistry meterRegistry;

  public DBHealthEndpoint(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Read
  public HttpResponse<HealthResult> getDbHealth() {
    counter.increment();
    HealthStatus status = HealthStatus.UP;
    HttpStatus httpStatus = HttpStatus.OK;
    int value = getValue(HIKARICP_CONNECTIONS);
    if (value == 0 || counter.longValue() % 10 == 0) {
      status = HealthStatus.DOWN;
      httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
    }
    var healthResult = HealthResult.builder(DB_HEALTH, status).details(getDetails()).build();
    return status(httpStatus).body(healthResult);
  }

  Map<String, Integer> getDetails() {
    return Map.of(
        HIKARICP_CONNECTIONS_IDLE, getValue(HIKARICP_CONNECTIONS_IDLE),
        HIKARICP_CONNECTIONS_PENDING, getValue(HIKARICP_CONNECTIONS_PENDING),
        HIKARICP_CONNECTIONS, getValue(HIKARICP_CONNECTIONS),
        HIKARICP_CONNECTIONS_ACTIVE, getValue(HIKARICP_CONNECTIONS_ACTIVE),
        HIKARICP_CONNECTIONS_MAX, getValue(HIKARICP_CONNECTIONS_MAX),
        HIKARICP_CONNECTIONS_MIN, getValue(HIKARICP_CONNECTIONS_MIN));
  }

  private int getValue(String hikaricpConnections) {
    return (int) meterRegistry.get(hikaricpConnections).gauge().value();
  }
}
