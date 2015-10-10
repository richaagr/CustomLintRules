package com.mobile.android;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class ActivityIsBaseActivityTest extends LintDetectorTest {

  @Override
  protected Detector getDetector() {
    return new ActivityIsBaseActivity();
  }

  @Override
  protected InputStream getTestResource(String relativePath, boolean expectExists) {
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(relativePath);
    if (!expectExists && stream == null) {
      return null;
    }
    return stream;
  }

  @Override
  protected List<Issue> getIssues() {
    return Collections.singletonList(ActivityIsBaseActivity.ISSUE);
  }

  public void testActivityIsBaseActivity() throws Exception {
    String result = lintProject("data/CorrectSample.java.data=>src/main/java/CorrectSample.java", "data/TestSample.java.data=>src/main/java/TestSample.java");
    System.out.println("Result is"+ result);
    assertTrue(result.contains("1 error"));
  }
}