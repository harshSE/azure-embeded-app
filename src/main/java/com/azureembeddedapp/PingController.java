package com.azureembeddedapp;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/ping")
public class PingController {

  private int counter;

  @Get
  public String ping() {
    return "pong" + ++counter;
  }

}
