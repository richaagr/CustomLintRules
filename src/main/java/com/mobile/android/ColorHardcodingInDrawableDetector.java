package com.mobile.android;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Attr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static com.android.SdkConstants.ANDROID_URI;


public class ColorHardcodingInDrawableDetector extends ResourceXmlDetector{

    public static final Issue HARDCODED_COLOR_IN_DRAWABLE_ISSUE = Issue.create(
            "Color Hardcoding in Drawable Detector",
            "Detects HardCoded color in Drawable files",
            "Detects HardCoded color in Drawable files",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            new Implementation(
                    ColorHardcodingInDrawableDetector.class,
                    Scope.RESOURCE_FILE_SCOPE)
    );

    @Override
    public boolean appliesTo(@NonNull Context context,
                             @NonNull File file) {
        return LintUtils.isXmlFile(file);
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return folderType == ResourceFolderType.DRAWABLE;
    }
    @Override
    public void visitAttribute(XmlContext context, Attr attribute) {
        if(attribute.getValue().charAt(0) == '#'){
            context.report(HARDCODED_COLOR_IN_DRAWABLE_ISSUE,attribute, context.getLocation(attribute), "Hardcoded color in drawable");
        }
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        ArrayList<String> listOfAttributes = new ArrayList<String>();
        listOfAttributes.add(com.android.SdkConstants.TAG_COLOR);
        return listOfAttributes;
    }


}
