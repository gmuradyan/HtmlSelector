package com.company;

public class SelectorSimple extends Selectors {


    SelectorSimple(String selector) {
        super(selector);
    }

    private SIMPLE_SELECTOR_TYPE detectSelector() {

        switch (selector.charAt(0)) {
            case ID:
                return SIMPLE_SELECTOR_TYPE.ID;

            case CLASS:
                return SIMPLE_SELECTOR_TYPE.CLASS;
            default:
                return SIMPLE_SELECTOR_TYPE.ELEMENT;

        }
    }

    private String replaseSimpleSelector(SIMPLE_SELECTOR_TYPE type) {
        try {

            if (type == SIMPLE_SELECTOR_TYPE.ID) {
                return "id=\"" + selector.substring(selector.indexOf(ID) + 1) + '"';
            } else if (type == SIMPLE_SELECTOR_TYPE.CLASS) {
                return "class=\"" + selector.substring(selector.indexOf(CLASS) + 1) + '"';
            }

        } catch (IndexOutOfBoundsException e) {

        }
        return selector;
    }

//    private boolean isTag(StringBuilder htmlFile, int pos) {
//        char tmp;
//        try {
//
//            while (pos > 0) {
//                tmp = htmlFile.charAt(--pos);
//                if (tmp == ' ') {
//                    continue;
//                } else if (tmp == '<') {
//                    return true;
//                } else if (tmp == '/') {
//                    return false;
//                } else {
//                    return false;
//                }
//            }
//        } catch (IndexOutOfBoundsException e) {
//
//        }
//        return false;
//    }

//    private int getEndIndx(String tag,String htmlFile){
//        StringBuilder htmlFile_tmp = new StringBuilder(htmlFile.substring(tag.length() + 1));
//        int count = 1, indCorrector = 0,indEnd;
//        boolean isTagExist = true;
//        while (isTagExist) {
//            if ((indEnd = htmlFile_tmp.indexOf(tag)) >= 0 && isTag(htmlFile_tmp, indEnd)) {
//                ++count;
//            } else if ((indEnd = htmlFile_tmp.indexOf("/" + tag)) >= 0 && isTag(htmlFile_tmp, indEnd)) {
//                --count;
//            } else {
//                isTagExist = false;
//            }
//            indCorrector += indEnd;
//            if (count == 0) {
//                String a = htmlFile_tmp.substring(indEnd);
//                indCorrector += htmlFile_tmp.substring(indEnd).indexOf(TAG_END) + 1 + tag.length() + 1;
//                break;
//            }
//            htmlFile_tmp = new StringBuilder(htmlFile_tmp.substring(indEnd));
//        }
//        return indCorrector;
//    }

    public String getBySelector(String htmlFile) {
        StringBuilder result = new StringBuilder();

        SIMPLE_SELECTOR_TYPE type = detectSelector();
        String htmlSelector = replaseSimpleSelector(type);

        int indStart, indSelector;
        while (true) {
            if ((indSelector = htmlFile.indexOf(htmlSelector)) >= 0) {
                if (type == SIMPLE_SELECTOR_TYPE.ELEMENT) {
                    if (!isTag(new StringBuilder(htmlFile), indSelector)) {
                        htmlFile = htmlFile.substring(indSelector + selector.length());
                        continue;
                    }
                }
                indStart = htmlFile.substring(0, indSelector).lastIndexOf(TAG_START);
                htmlFile = htmlFile.substring(indStart);

                String tag;
                if (type == SIMPLE_SELECTOR_TYPE.ELEMENT) {
                    tag = selector;
                } else {
                    tag = htmlFile.substring(1, htmlFile.indexOf(" "));
                }
                int indCorrector;
                if (HtmlValidator.HtmlTagsType.isSingleTeg(tag)) {
                    indCorrector = htmlFile.indexOf(TAG_END) + 1;
                } else {
                    indCorrector = getEndIndx(tag, htmlFile);
                }
                result.append(htmlFile.substring(0, indCorrector));
                //System.out.println("result=" +result);
                htmlFile = htmlFile.substring(indCorrector + 1);
            } else {
                break;
            }
        }
        return result.toString();
    }
}
