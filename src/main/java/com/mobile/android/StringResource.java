package com.mobile.android;

import com.android.tools.lint.detector.api.Location;

public class StringResource {

  private final String resourceId;
  private final String resourceValue;
  private final Location location;

  public StringResource(String resourceId, String resourceValue, Location location) {
    this.resourceId = resourceId;
    this.resourceValue = resourceValue;
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }

  public String getResourceId() {
    return resourceId;
  }

  public String getResourceValue() {
    return resourceValue;
  }

  public String getFilename() {
    return location.getFile().getName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StringResource that = (StringResource) o;

    return resourceValue.equals(that.resourceValue);

  }

  @Override
  public int hashCode() {
    return resourceValue.hashCode();
  }

  @Override
  public String toString() {
    return "StringResource{" +
        "location=" + location +
        ", resourceId='" + resourceId + '\'' +
        ", resourceValue='" + resourceValue + '\'' +
        '}';
  }
}
