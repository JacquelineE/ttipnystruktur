package com.searcheveryaspect.backend.database.read;


/**
 * 
 */
public interface DatabaseReader<I, O> {

  O read(I input);
}
