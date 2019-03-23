package com.company;

public class Selectors {

    protected String selector;
    protected static final char ID = '#';
    protected static final char CLASS = '.';
    protected static final String PROBEL = " ";
    protected static final String TAG_START = "<";
    protected static final String TAG_END = ">";


    enum SIMPLE_SELECTOR_TYPE {
        ELEMENT,
        ID,
        CLASS
    }

    Selectors(String selector) {
        this.selector = new String(selector);
    }

    public String getBySelector(String htmlFile) {
        return "class Selectors - getBySelector ";
    }

    protected boolean isTag(StringBuilder htmlFile, int pos) {
        char tmp;
        try {
            while (pos > 0) {
                tmp = htmlFile.charAt(--pos);
                if (tmp == ' ') {
                    continue;
                } else if (tmp == '<') {
                    return true;
                } else if (tmp == '/') {
                    return false;
                } else {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {

        }
        return false;
    }

    protected int getEndIndx(String tag, String htmlFile) {
        StringBuilder htmlFile_tmp = new StringBuilder(htmlFile.substring(tag.length() + 1));
        int count = 1, indCorrector = 0, indEnd;
        boolean isTagExist = true;
        while (isTagExist) {
            if ((indEnd = htmlFile_tmp.indexOf(tag)) >= 0 && isTag(htmlFile_tmp, indEnd)) {
                ++count;
            } else if ((indEnd = htmlFile_tmp.indexOf("/" + tag)) >= 0 && isTag(htmlFile_tmp, indEnd)) {
                --count;
            } else {
                isTagExist = false;
            }
            indCorrector += indEnd + 1;
            String a = htmlFile_tmp.substring(indEnd);
            if (count == 0) {

                indCorrector += htmlFile_tmp.substring(indEnd).indexOf(TAG_END) + 1 + tag.length() + 1;
                break;
            }
            htmlFile_tmp = new StringBuilder(htmlFile_tmp.substring(indEnd + 1));
        }
        return indCorrector;
    }
}
