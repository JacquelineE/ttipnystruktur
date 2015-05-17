package com.searcheveryaspect.backend.database.update;

import com.beust.jcommander.Parameter;

/**
 * Command line arguments for the manual database update. Provides flags for the regular
 * request consisting of two dates.
 */
public class CommandLineArgs {
  @Parameter(names = "-from", description = "Start of published interval for documents")
  public String from;
  @Parameter(names = "-to", description = "End of published interval for documents")
  public String to;
}
