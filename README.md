# LexicalParser
C language grammar rules for Languages and Compilation  
Given some symbols describing C language grammar rules, design and implement lexical analysis parser.  
  
[description]  
This is a report for the program design of the lexical analysis parser about C language.   
The parser is programmed in Java, with 5 classes and approximately 400 lines which including the comments and blanks.  
Overall, it involves the functions following:  
a.	Detect input string errors: including some typical error types, and indicating the line number of error occurring;  
b.	Distinguish the lexical type: including identifier, operator, separator, number type, preserved words, comments, literal string and some other delimiters; More detail will be illustrated in the following section;  
c.	The code is read from filename.c (entered by user in the console), and it stores the results in output.txt;  
d.	The grammar rules could be simply modified though other 4 class, which stores the symbol in separate arrays.  

More detail information listed in the file "report.pdf"  

[hints]  
(1) The upper and lower character are treated the same  
(2) “/*......*/ and “//” are comments of the program  

[Output Demo]  
![Demo](https://github.com/Hileoo/LexicalParser/blob/master/Output_Demo.jpeg)  

