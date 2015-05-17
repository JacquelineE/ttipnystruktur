package com.searcheveryaspect.backend.webserver.controller;

import org.restexpress.Request;
import org.restexpress.Response;

/**
 * Provides empty methods for all but the Read function.
 */
abstract class ReadOnlyController {
  public void create(Request request, Response response) {

  }

  public void update(Request request, Response response) {

  }

  public void delete(Request request, Response response) {

  }
}
