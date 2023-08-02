package com.azureembeddedapp;

import io.micronaut.runtime.Micronaut;

public class Application {

  public static void main(String[] args) {
    if (System.getenv("AZUREMONITOR_INSTRUMENTATION_KEY") != null
    && !System.getenv("AZUREMONITOR_INSTRUMENTATION_KEY").isBlank()) {
      System.out.println("===========================AZUREMONITOR_INSTRUMENTATION_KEY received");
    } else {
      System.out.println("===========================AZUREMONITOR_INSTRUMENTATION_KEY not received");
    }
    Micronaut.run(Application.class, args);
  }
}