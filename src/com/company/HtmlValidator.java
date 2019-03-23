package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public  class HtmlValidator{

    enum  TAG_TYPE{open,close}
    private static final String HTML_DELIMITER= ">";
    private static final String HTML_TAG_PATTERN = "<(?!.*<.*<)(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    private  static Pattern pattern=Pattern.compile(HTML_TAG_PATTERN);
    private static Matcher matcher;

    private static Map<String,HtmlTagsType> TagsStatistic=new HashMap<String,HtmlTagsType>();

    public static  class HtmlTagsType {
        public int tagCount;
        private static final HashSet<String> HTML_SINGLE_TEGS = new HashSet<>(Arrays.asList("area ", "base", "br", "col", "command", "embed", "hr", "input",
                "img", "keygen", "link", "meta", "source", "param", "track", "wbr", "!DOCTYPE"));

        public HtmlTagsType(TAG_TYPE type) {
            if (type == TAG_TYPE.open) {
                tagCount = 1;
            } else {
                tagCount = -1;
            }
        }

        private void incCount() {
            ++tagCount;
        }

        private void decCount() {
            --tagCount;
        }

        public static boolean isSingleTeg(String tag) {
              return HTML_SINGLE_TEGS.contains(tag);
        }

        private static boolean checkPaireTags() {
            try {

                for (String key : TagsStatistic.keySet()) {
                    if (!TagsStatistic.get(key).isSingleTeg(key) && TagsStatistic.get(key).tagCount!=0 ) {
                        System.out.print(" return false warning: tag=" + key + " =");
                        System.out.println("=" + TagsStatistic.get(key).tagCount);
                        //  return false;
                    }
                }
                return true;
            } catch (NullPointerException e) {

            }
            return false;
        }

    }
        private static boolean chackByPattern(final String tag) {
            matcher = pattern.matcher(tag);
            return matcher.matches();
        }

        private static boolean checkSingleSymbol(final String tag, String symbol) {
            try {
                return (tag.indexOf(symbol) == tag.lastIndexOf(symbol));
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
            return false;
        }

        private static boolean validateTag(String tag) {
            return chackByPattern(tag) && checkSingleSymbol(tag, "<") && checkSingleSymbol(tag, ">");
        }

        private static void supplyTagsStatistic(TAG_TYPE type, String tag) {
            int tagNameEnd;
            try {
                if ((tagNameEnd = tag.indexOf(" ")) > 0 || (tagNameEnd = tag.indexOf(">")) > 0) {
                    if (TagsStatistic.containsKey(tag.substring(1, tagNameEnd))) {
                        if (type == TAG_TYPE.close) {
                            TagsStatistic.get(tag.substring(1, tagNameEnd)).decCount();
                        } else {
                            TagsStatistic.get(tag.substring(1, tagNameEnd)).incCount();
                        }
                    } else {
                        TagsStatistic.put(tag.substring(1, tagNameEnd), new HtmlTagsType(type));
                    }
                }
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
        }

        public static boolean validateFile(String htmlFile) {
            try {
                for (String tag : htmlFile.split(HTML_DELIMITER)) {
                    if (!tag.isEmpty()) {
                        tag = tag + ">";
                        int begin, end;
                        if ((begin = tag.indexOf("<")) > 0 && (end = tag.indexOf(">")) > 1) {
                            tag = tag.substring(begin, end + 1);
                        }
                        if ((validateTag(tag))) {
                            int isCloseTag = tag.indexOf("</");
                            if (isCloseTag >= 0) {
                                tag = tag.substring(isCloseTag + 1).trim();
                                supplyTagsStatistic(TAG_TYPE.close, tag);
                            } else {
                                tag = tag.trim();
                                supplyTagsStatistic(TAG_TYPE.open, tag);
                            }
                        } else {
                            System.out.println("teg is invalid" + tag);
                            return false;
                        }
                    }
                }
                return HtmlTagsType.checkPaireTags();
            } catch (IndexOutOfBoundsException e) {

            }
            return false;
        }
    }

