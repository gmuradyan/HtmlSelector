package com.company;

public class SelectorDescendant extends Selectors {

    SelectorDescendant(String selector) {
        super(selector);
    }

    private class SelectorMembers {
        String[] tag;
        String attribut;

        SelectorMembers(String members[], String attribut, SIMPLE_SELECTOR_TYPE type) {

            if (type == SIMPLE_SELECTOR_TYPE.ID) {
                this.attribut = "id=\"" + attribut + "\"";
            } else if (type == SIMPLE_SELECTOR_TYPE.CLASS) {
                this.attribut = "class=\"" + attribut + "\"";
            } else {
                attribut = null;
            }
            tag = new String[members.length];
            tag = members;
        }
    }

    private SIMPLE_SELECTOR_TYPE detectSelector() {
        if (selector.indexOf(ID) > 0) {
            return SIMPLE_SELECTOR_TYPE.ID;
        } else if (selector.indexOf(CLASS) > 0) {
            return SIMPLE_SELECTOR_TYPE.CLASS;
        }
        return SIMPLE_SELECTOR_TYPE.ELEMENT;
    }

    private SelectorMembers replaseDescendantSelector(SelectorSimple.SIMPLE_SELECTOR_TYPE type) throws NullPointerException {
        String members[] = null;
        String attribut = null;
        try {
            if (type == SelectorSimple.SIMPLE_SELECTOR_TYPE.ID) {
                attribut = selector.substring(selector.indexOf(ID) + 1);
                selector = selector.substring(0, selector.indexOf(ID));
            } else if (type == SelectorSimple.SIMPLE_SELECTOR_TYPE.CLASS) {
                attribut = selector.substring(selector.indexOf(CLASS) + 1);
                selector = selector.substring(0, selector.indexOf(CLASS));
            }
            members = selector.split(PROBEL);
            if (members != null) {
                return new SelectorMembers(members, attribut, type);
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException e) {


        }
        return null;
    }

    private int chooseIndex(int indp, int indend) {
        int ind = 0;
        if (indp >= 0) {
            if (indp < indend) {
                ind = indp;
            } else if (indend >= 0) {
                ind = indend;
            } else if (indend <= 0) {
                ind = indp;
            }
        } else if (indend >= 0) {
            ind = indend;
        } else {
            return -1;
        }
        return ind;
    }

    private boolean checkTagSequence(SelectorMembers member, String htmlFile, int indStart) {

        for (int i = (member.attribut == null) ? member.tag.length - 2 : member.tag.length - 1; i >= 0; i--) {

            indStart = htmlFile.substring(0, indStart - 1).lastIndexOf(TAG_START);

            int tag_open = 0, tag_close = 0;
            while (true) {
                int indp = htmlFile.substring(indStart).indexOf(" ");
                int indend = htmlFile.substring(indStart).indexOf(">");
                int ind = chooseIndex(indp, indend);
                if (ind == -1) {
                    System.out.println(" checkTagSequence false");
                    return false;
                }
                //int n = htmlFile.length();
                //String tmp = htmlFile.substring(indStart);
                String tag = htmlFile.substring(indStart + 1, indStart + ind);
                if (tag.charAt(0) == '/' && tag.substring(1).equals(member.tag[i])) {
                    ++tag_close;
                } else if (tag.equals(member.tag[i])) {
                    ++tag_open;
                }
                if (tag_open - tag_close == 1) {
                    break;
                }
                htmlFile = htmlFile.substring(0, indStart - 1);
                indStart = htmlFile.lastIndexOf(TAG_START);
                if (indStart <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getBySelector(String htmlFile) {
        String htmlFile_original = htmlFile;
        StringBuilder result = new StringBuilder();
        try {
            SelectorMembers member = replaseDescendantSelector(detectSelector());
            if (member != null) {
                int counter = 0;
                while (true) {
                    int indSelector = 0, indStart, indCorrector, length;
                    if (member.attribut != null) {
                        indSelector = htmlFile.indexOf(member.attribut);
                        length = member.attribut.length();
                    } else {
                        indSelector = htmlFile.indexOf(member.tag[member.tag.length - 1]);
                        if (!isTag(new StringBuilder(htmlFile), indSelector) && indSelector > 0) {
                            counter += htmlFile.length();
                            htmlFile = htmlFile.substring(indSelector + member.tag[member.tag.length - 1].length());
                            counter -= htmlFile.length();
                            continue;
                        }
                        length = member.tag[member.tag.length - 1].length();
                    }
                    if (indSelector >= 0) {
                        indStart = htmlFile.substring(0, indSelector).lastIndexOf(TAG_START);

                        if (checkTagSequence(member, htmlFile_original, indStart + counter)) {
                            counter += htmlFile.length();
                            htmlFile = htmlFile.substring(indStart);
                            counter -= htmlFile.length();
                            int ind = chooseIndex(htmlFile.indexOf(" "), htmlFile.indexOf(TAG_END));
                            if (ind == -1) {
                                continue;
                            }
                            String tag = htmlFile.substring(1, ind);
                            if (HtmlValidator.HtmlTagsType.isSingleTeg(tag)) {
                                indCorrector = htmlFile.indexOf(TAG_END) + 1;
                            } else {
                                indCorrector = getEndIndx(tag, htmlFile);
                            }
                            result.append(htmlFile.substring(0, indCorrector));
                            // System.out.println("result=" + result);
                            counter += htmlFile.length();
                            htmlFile = htmlFile.substring(indCorrector + 1);
                            counter -= htmlFile.length();
                        } else {
//                                result.delete(0,result.length());
                            counter += htmlFile.length();
                            htmlFile = htmlFile.substring(indSelector + length);
                            counter -= htmlFile.length();
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
