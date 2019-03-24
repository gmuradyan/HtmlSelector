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
ex: 1) -f path\file.html -s #sel1
    2) -f path\file.html -s .sel2
    3) -f path\file.html -s div

Combined 
{
	tag+#class
	tag+.id
	
}
ex: 1) -f path\file.html -s div#sel1
    2) -f path\file.html -s p.sel2

Descendant 
{
	tag+ probel+ ..tag.+ #id
	tag+ probel+ ..tag.+ .class
	tag + tag + ...
}
ex: 1) -f path\file.html -s div #sel1
    2) -f path\file.html -s div p .sel2
    3) -f path\file.html -s div h3
