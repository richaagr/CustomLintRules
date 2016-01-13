package com.mobile.android;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Collection;
import static com.android.SdkConstants.ANDROID_URI;
/**
 * Created by rimarg on 12/01/16.
 */
public class ColorHardcodingInLayoutDetector extends LayoutDetector {
    public static final Issue HARDCODED_COLOR_IN_LAYOUT_ISSUE = Issue.create(
            "Color Hardcoding in Layout Detector",
            "Detects HardCoded color in layout files",
            "Detects HardCoded color in layout files",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            new Implementation(
                    ColorHardcodingInLayoutDetector.class,
                    Scope.RESOURCE_FILE_SCOPE)
    );

    @Override
    public void visitAttribute(XmlContext context, Attr attribute) {
        if(attribute.getValue().charAt(0) == '#'){

            if (!ANDROID_URI.equals(attribute.getNamespaceURI())) {
                return;
            }
            context.report(HARDCODED_COLOR_IN_LAYOUT_ISSUE,attribute, context.getLocation(attribute), "Hardcoded color in xml");
        }
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        ArrayList<String> listOfAttribute = new ArrayList<String>();
        listOfAttribute.add("background");
        listOfAttribute.add("textColor");
        listOfAttribute.add("textColorLink");
        listOfAttribute.add("cacheColorHint");
        listOfAttribute.add("color");
        listOfAttribute.add("listSelector");
        return listOfAttribute;
    }
}
