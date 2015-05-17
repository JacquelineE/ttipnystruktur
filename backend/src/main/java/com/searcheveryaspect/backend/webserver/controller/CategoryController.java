package com.searcheveryaspect.backend.webserver.controller;

import com.searcheveryaspect.backend.webserver.CategoryResponse;

import org.restexpress.Request;
import org.restexpress.Response;

/**
 * CategoryController provides a way to query for all the possible categories that can be
 * used to classify a motion.
 */
public class CategoryController extends ReadOnlyController {

  public CategoryResponse read(Request request, Response response) {
    return new CategoryResponse();
  }
}
