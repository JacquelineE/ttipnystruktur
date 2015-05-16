package com.searcheveryaspect.backend.webserver;

import com.beust.jcommander.Parameter;

/**
 * Command line arguments for the webserver.
 */
public class CommandLineArgs {
  @Parameter(names = "-port", description = "Port for server")
  public Integer port = 8080;
}
