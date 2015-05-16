package com.searcheveryaspect.backend.webserver.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.database.read.DatabaseReader;
import com.searcheveryaspect.backend.database.read.SystemRequest;
import com.searcheveryaspect.backend.webserver.SystemResponse;

import org.restexpress.Request;
import org.restexpress.Response;

/**
 * SystemController handles requests for meta information about the system, like the last
 * time the database was updated.
 */
public class SystemController extends ReadOnlyController {
  private final DatabaseReader<SystemRequest, SystemResponse> reader;

  /**
   * Returns a new SystemController. The DatabaseReader serves as a connection to the
   * database and is needed to process read requests.
   */
  public SystemController(DatabaseReader<SystemRequest, SystemResponse> reader) {
    this.reader = checkNotNull(reader);
  }

  /**
   * Implements the Read method for the system information.
   * 
   * @param request
   * @param response
   * @return
   */
  public SystemResponse read(Request request, Response response) {
    try {
      return reader.read(new SystemRequest());
    } catch (Exception e) {
      response.setException(e);
      return null;
    }
  }
}
