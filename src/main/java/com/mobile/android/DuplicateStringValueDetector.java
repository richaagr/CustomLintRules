package com.mobile.android;


import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.*;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.android.SdkConstants.TAG_STRING;

public class DuplicateStringValueDetector extends ResourceXmlDetector implements Detector.XmlScanner {

  public static final Issue DUPLICATE_STRING_VALUE_ISSUE = Issue.create(
      "Duplicate String Values",
      "Different string resources that have the same value",
      "Finds different string resources which have the same values",
      Category.CORRECTNESS,
      7,
      Severity.WARNING,
      new Implementation(DuplicateStringValueDetector.class, Scope.ALL_RESOURCES_SCOPE));

  private Map<String, StringResource> stringResources;

  @Override
  public Collection<String> getApplicableElements() {
    return Collections.singleton(TAG_STRING);
  }

  @Override
  public void beforeCheckProject(@NonNull Context context) {
    stringResources = new HashMap<String, StringResource>();
  }

  @Override
  public void visitElement(XmlContext context, Element element) {
    StringResource stringResource = parseStringResource(context, element);
    String resourceValue = stringResource.getResourceValue();
    if (stringResources.containsKey(resourceValue)) {
      StringResource originalResource = stringResources.get(resourceValue);
      context.report(DUPLICATE_STRING_VALUE_ISSUE, stringResource.getLocation(),
          "Matches resource with id: " + originalResource.getResourceId() + " in file " + originalResource.getFilename());
    } else {
      stringResources.put(resourceValue, stringResource);
    }
  }

  private StringResource parseStringResource(XmlContext context, Element element) {
    String resourceId = element.getAttribute("name");
    String resourceValue = element.getTextContent();
    return new StringResource(resourceId, resourceValue, context.getLocation(element));
  }

}
