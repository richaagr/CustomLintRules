package com.mobile.android;

import com.android.annotations.NonNull;
import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.MethodInvocation;

import static com.android.SdkConstants.DOT_JAVA;

public class HardcodedColorDetector extends Detector implements Detector.JavaScanner{
  public static final Issue HARDCODED_COLOR_DETECTOR = Issue.create(
      "Hardcoded Colors",
      "Colors are hardcoded instead of being read from color files.",
      "Colors should not be hardcoded, and instead read from a color xml file.",
      Category.CORRECTNESS, 3, Severity.ERROR,
      new Implementation(HardcodedColorDetector.class, EnumSet.of(Scope.JAVA_FILE)));
  public static final String PARSE_COLOR_METHOD = "parseColor";
  public static final String RGB_METHOD = "rgb";
  public static final String ARGB_METHOD = "argb";
  public static final String COLOR_CLASS = "android.graphics.Color";

  @Override
  public boolean appliesTo(@NonNull Context context, @NonNull File file) {
    return LintUtils.endsWith(file.getName(), DOT_JAVA);
  }

  @Override
  public List<String> getApplicableMethodNames() {
    return Arrays.asList(PARSE_COLOR_METHOD, RGB_METHOD, ARGB_METHOD);
  }

  @Override
  public void visitMethod(@NonNull JavaContext context, AstVisitor visitor, @NonNull MethodInvocation node) {
    JavaParser.ResolvedNode resolved = context.resolve(node);
    if (!(resolved instanceof JavaParser.ResolvedMethod)) {
      return;
    }

    JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolved;
    if (method.getContainingClass().matches(COLOR_CLASS)) {
      String message = "Usage of Color.parseColor, Color.argb or Color.rgb detected in the app. Avoid hardcoding colors, and define them in the colors xml files instead.";
      context.report(HARDCODED_COLOR_DETECTOR, node, context.getLocation(node), message);
    }

  }
}
