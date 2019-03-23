package com.company;

public class CommandHtmlSelector extends CommndHtmlLoader {

    private String selector;

    CommandHtmlSelector(String path, String selector) {
        super(path);
        this.selector = selector;
    }

    private Selectors detectSelectorType() throws NullPointerException {
        try {

            selector = selector.trim();
            if (!selector.contains(Selectors.PROBEL)) {
                if (selector.indexOf(Selectors.ID) == 0 || selector.indexOf(Selectors.CLASS) == 0) {
                    return new SelectorSimple(selector);
                } else if (selector.indexOf(Selectors.ID) < 0 && selector.indexOf(Selectors.CLASS) < 0) {
                    return new SelectorSimple(selector);
                } else {
                    return new SelectorCombined(selector);
                }
            } else {
                return new SelectorDescendant(selector);
            }
        } catch (IndexOutOfBoundsException e) {

        } catch (ClassCastException e) {

        }
        return null;
    }

    public void commandHandel() throws NullPointerException {
        try {


            if (super.loadHtmlFile() && !selector.isEmpty()) {
                Selectors selector = detectSelectorType();
                String result = selector.getBySelector(htmlFile);
                inOut.printFile(result);
            } else {
                System.out.println("detectSelectorType method- html not valid, what to do?");
            }
        } catch (NullPointerException e) {

        }
    }
}
