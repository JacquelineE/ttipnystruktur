package com.searcheveryaspect.backend.webserver.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.searcheveryaspect.backend.database.read.DatabaseReader;
import com.searcheveryaspect.backend.database.read.TrendingRequest;
import com.searcheveryaspect.backend.webserver.TrendingSearchResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * 
 */
public class TrendingControllerTest {

  @Mock
  DatabaseReader<TrendingRequest, TrendingSearchResponse> readerMock;
  @Mock
  Response resMock;
  @Mock
  Request reqMock;

  // Mockito rule. Initialises mocks.
  public @Rule
  MockitoRule rule = MockitoJUnit.rule();
  // Rule for verifying that exceptions are thrown.
  public @Rule
  ExpectedException thrown = ExpectedException.none();

  @Test
  public void testInvalidQuantity_tooLow() {
    when(reqMock.getHeader("quantity")).thenReturn("0");
    verifyInvalidQuantity();
  }

  @Test
  public void testInvalidQuantity_tooHigh() {
    when(reqMock.getHeader("quantity")).thenReturn("137");
    verifyInvalidQuantity();
  }

  private void verifyInvalidQuantity() {
    // Controller to test behaviour on.
    TrendingController t = new TrendingController(readerMock);

    assertEquals(null, t.read(reqMock, resMock));

    ArgumentCaptor<IllegalArgumentException> captor =
        ArgumentCaptor.forClass(IllegalArgumentException.class);
    // Check that an IllegalArgumentException was set in the response.
    verify(resMock).setException(captor.capture());
    // Verify that the reader was never interacted with.
    verifyNoMoreInteractions(readerMock);
  }
}
