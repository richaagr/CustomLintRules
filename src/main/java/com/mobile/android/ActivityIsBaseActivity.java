package com.mobile.android;

import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.*;
import lombok.ast.AstVisitor;
import lombok.ast.ClassDeclaration;
import lombok.ast.ForwardingAstVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import static com.android.SdkConstants.CLASS_ACTIVITY;

public class ActivityIsBaseActivity extends Detector implements Detector.JavaScanner {

  public static final Issue ISSUE = Issue.create(
    "ActivityIsBaseActivity",
    "Check if every activity extends base activity",
    "Check if every activity extends base activity",
    Category.CORRECTNESS,
    7,
    Severity.ERROR,
    new Implementation(ActivityIsBaseActivity.class, EnumSet.of(Scope.ALL_JAVA_FILES))
  );

  @Override
  public Collection<String> getApplicableElements() {
    return Arrays.asList(CLASS_ACTIVITY);
  }

  @Override
  public AstVisitor createJavaVisitor(JavaContext context) {
    return new BaseActivityChecker(context);
  }

  private static class BaseActivityChecker extends ForwardingAstVisitor {
    private final JavaContext mContext;

    private BaseActivityChecker(JavaContext mContext) {
      this.mContext = mContext;
    }

    @Override
    public boolean visitClassDeclaration(ClassDeclaration node) {
      JavaParser.ResolvedNode rNode = mContext.resolve(node);
      JavaParser.ResolvedClass rClass = (JavaParser.ResolvedClass) rNode;
      if (rClass.isSubclassOf(CLASS_ACTIVITY, false) && !rClass.isSubclassOf("android.mobile.com.customlintdemoapp.BaseActivity", false)) {
        mContext.report(ISSUE, node, mContext.getLocation(node), "Activity is not Base Activity");
        return true;
      }
      return false;
    }
  }
}