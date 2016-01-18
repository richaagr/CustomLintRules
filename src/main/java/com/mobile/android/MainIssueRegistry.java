package com.mobile.android;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.ArrayList;
import java.util.List;

public class MainIssueRegistry extends IssueRegistry {
  @Override
  public List<Issue> getIssues() {

    return new ArrayList<Issue>(){{
      add(HardcodedColorDetector.HARDCODED_COLOR_DETECTOR);
      add(ColorHardcodingInLayoutDetector.HARDCODED_COLOR_IN_LAYOUT_ISSUE);
      add(ColorHardcodingInDrawableDetector.HARDCODED_COLOR_IN_DRAWABLE_ISSUE);
    }};
  }
}
