package com.azureembeddedapp;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/ping")
public class PingController {
  @Get
  public String ping() {
    return "pong";
  }

}
