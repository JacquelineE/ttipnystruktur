package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import com.searcheveryaspect.backend.shared.Category;

import java.util.Objects;

/**
 * CategoryResponse represents the response for a categories request, specifying the
 * possible categories for motions.
 */
public class CategoryResponse {
  private final ImmutableList<String> categories;

  public CategoryResponse() {
    Builder<String> builder = new ImmutableList.Builder<String>();
    for (Category c : Category.values()) {
      builder.add(c.toString());
    }
    categories = builder.build();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof CategoryResponse) {
      CategoryResponse that = (CategoryResponse) o;
      return categories.equals(that);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("categories", categories).toString();
  }
}
