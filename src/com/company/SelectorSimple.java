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
                return "\"" + selector.substring(selector.indexOf(ID) + 1) + '"';//id=
            } else if (type == SIMPLE_SELECTOR_TYPE.CLASS) {
                return "class=\"" + selector.substring(selector.indexOf(CLASS) + 1) + '"';
            }

        } catch (IndexOutOfBoundsException e) {

        }
        return selector;
    }



    public String getBySelector(String htmlFile) {
        StringBuilder result = new StringBuilder();

        SIMPLE_SELECTOR_TYPE type = detectSelector();
        String htmlSelector = replaseSimpleSelector(type);

        int indStart, indSelector;
        while (true) {
            //(indSelector = htmlFile.indexOf(htmlSelector))
            if ((indSelector=findSelector(htmlFile,htmlSelector,type)) >= 0) {
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
