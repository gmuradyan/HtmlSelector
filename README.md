# HtmlSelector
find and print from html file code parts by selectors.

Existing commands:
-h : Help
-f : validate html file
-f file -s selector : print code block by selector

Selector types: 


simple 
{	tag - <>
	class- .
	id - #
}

Combined 
{
	tag+#class
	tag+.id
	
}

Descendant 
{
	tag+ probel+ ..tag.+ #id
	tag+ probel+ ..tag.+ .class
	tag + tag + ...
}

