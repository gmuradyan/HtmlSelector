package com.company;

public class SelectorCombined extends Selectors {

    private class SelectorMembers {
        private String tag;
        private String atribut;

        SelectorMembers(String members[], SIMPLE_SELECTOR_TYPE type) {
            this.tag = members[0];
            if (type == SIMPLE_SELECTOR_TYPE.ID) {
                this.atribut = "id=\"" + members[1] + "\"";
            } else {
                this.atribut = "class=\"" + members[1] + "\"";
            }
        }
    }

    SelectorCombined(String selector) {
        super(selector);
    }

    private SIMPLE_SELECTOR_TYPE detectSelector() throws NullPointerException {
        if (selector.indexOf(ID) > 0) {
            return SIMPLE_SELECTOR_TYPE.ID;
        } else if (selector.indexOf(CLASS) > 0) {
            return SIMPLE_SELECTOR_TYPE.CLASS;
        }
        return null;
    }

    private SelectorMembers replaseCombinedSelector(SelectorSimple.SIMPLE_SELECTOR_TYPE type) throws NullPointerException {
        if (type == null) {
            return null;
        }
        String members[] = null;
        try {
            if (type == SelectorSimple.SIMPLE_SELECTOR_TYPE.ID) {
                members = selector.split(String.valueOf(ID), 2);
            } else if (type == SelectorSimple.SIMPLE_SELECTOR_TYPE.CLASS) {
                // members=selector.split(String.valueOf(CLASS),2);
                int ind = selector.indexOf(CLASS);
                members = new String[2];
                members[0] = selector.substring(0, ind);
                members[1] = selector.substring(ind + 1);
            }
            if (members != null && members.length == 2) {
                return new SelectorMembers(members, type);
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException e) {

        }
        return null;
    }

    public String getBySelector(String htmlFile) {
        StringBuilder result = new StringBuilder();
        try {
            SIMPLE_SELECTOR_TYPE type=detectSelector();
            SelectorMembers member = replaseCombinedSelector(type);
            if (member != null) {
                while (true) {
                    int indSelector = 0, indStart, indCorrector;
                    //htmlFile.indexOf(member.atribut)
                    if ((indSelector =findSelector(htmlFile,member.atribut,type)) >= 0) {
                        indStart = htmlFile.substring(0, indSelector).lastIndexOf(TAG_START);
                        htmlFile = htmlFile.substring(indStart);
                        String tag = htmlFile.substring(1, htmlFile.indexOf(" "));
                        if (tag.equals(member.tag)) {
                            if (HtmlValidator.HtmlTagsType.isSingleTeg(member.tag)) {
                                indCorrector = htmlFile.indexOf(TAG_END) + 1;
                            } else {
                                indCorrector = getEndIndx(member.tag, htmlFile);
                            }
                            result.append(htmlFile.substring(0, indCorrector));
                            // System.out.println("result=" + result);
                            htmlFile = htmlFile.substring(indCorrector + 1);
                        } else {
                            htmlFile = htmlFile.substring(1 + member.atribut.length());
                            continue;
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        return result.toString();
    }
}
