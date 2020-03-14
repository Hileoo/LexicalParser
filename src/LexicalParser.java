
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class is the main class of the lexical parser, could analysis the context of code in C
 */
public class LexicalParser {
    
    public static FileReader file_reader;
	private static Scanner input;
    
    /**
     * This is the main method, which analysis the context and output the result
     * @param args  Initial Setting
     * @throws FileNotFoundException  File Not Found
     * @throws IOException  IO Error
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int char_read = 1000;  //Initial char defined by myself, any number except from ASCII
        System.out.println("Please input the file name which you want to analyze: ");
        input = new Scanner(System.in);
        String codeName = input.nextLine();
        file_reader = new FileReader(codeName + ".c");  //Read context from the given code written in C 
        
        /* Write the output to the file */
        File f = new File(codeName + "_result.txt");
        if(f.exists()) 
        	f.delete();
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        PrintStream ps = new PrintStream(fos);
        PrintStream out = System.out;
        System.setOut(ps);
        
        /* Lexical Analysis */
        StringBuilder s_builder = new StringBuilder();
        PreservedWords my_keyword = new PreservedWords();
        Punctuation my_punctuation = new Punctuation();
        int readable_flag = 1;
        char ch = ' ';  
        punctuationType(readable_flag, char_read, ch, my_punctuation, s_builder, my_keyword);
        
        /* Indicate the program end */
        System.setOut(out);
        System.out.println("Complete! Please see the result file: " + codeName + "_result.txt");
    }
    
    /**
     * This method is used to read the character, and catch the exception
     * @return c  The single char read from file
     */
    public static int readChar() {
        int c = 999;  //Initial Value, set by myself
        try {
            c = file_reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c;
    }
    
    /**
     * This method is used to pass the comments of code
     */
    public static void printComment() {
        int c = 0; //Initialize with null
        String comment = new String();
        char temp = '/';
        /* Read the comment until line feed */
        while (c != 10) {
            try {
                c = file_reader.read();
                char ch = (char) c;
                comment += ch;
                if(ch=='/' && temp=='*')
                	comment = comment.substring(0, comment.length()-2);
                temp = ch;
            } catch (IOException e) {
                e.printStackTrace();
            }  
        }
        System.out.print("<Comment, 7>:  " + comment);
    }
    
    /**
     * This method is used to analyze the number type
     */
    public static void numberType(int numberCode, String str, char ch, StringBuilder temp_s_builder, Punctuation my_punctuation, int readable_flag) {
    	if(numberCode != -1) {
        	/* Scientific Computing */
            if(str.charAt(str.length() - 1) == 'e' ) {
                if(ch != '+' && ch != '-') {
                    str = "Error Number Format at  " + str;
                    numberCode = -1;
                }
                else
                    temp_s_builder.append(ch);
                
                int temp_int_char = readChar();;
                char temp_char = (char)temp_int_char;
                /* Non punctuation */
                while(my_punctuation.punctuationCode(temp_char) == -1) {
                	/* Next char is 0-9 and this string is not number */
                    if(temp_int_char>=48 && temp_int_char<=57 && numberCode!=-1)
                        temp_s_builder.append(temp_char);
                    else {
                        System.out.print("Error Format at  " + str);
                        numberCode = -1;
                    }
                    temp_int_char = readChar();
                    temp_char = (char) temp_int_char;
                }
                readable_flag = 0;
                ch = temp_char;
            }
            /* String is number with state 1 */
            if(numberCode == 1)
                System.out.print("<Decimal Number, 10>:  ");
            /* String is number with state 1 */
            else if(numberCode == 2)
                System.out.print("<Octal Number, 11>:  ");
            /* String is number with state 3 */
            else if(numberCode ==3)
                System.out.print("<Hexa Number, 12>:  ");
            /* String is number with state 4 */
            else if(numberCode == 4)
                System.out.print("<Float Number, 9>:  ");
            System.out.println(str + temp_s_builder.toString());
        }
        else
        	System.out.println("Error Format at  " + str);	
    }
    
    /**
     * This method is used to analyze the punctuation type
     */
    public static void punctuationType(int readable_flag, int char_read, char ch, Punctuation my_punctuation, StringBuilder s_builder, PreservedWords my_keyword) {
        while (true) {
        	/* If readable, read until the end of code */
            if (readable_flag == 1) {
                char_read = readChar();
                if (char_read == -1)
                    break;
                else
                    ch = (char) char_read;
            }
            
            int punc_result = my_punctuation.punctuationCode(ch);
            /* If the char is not punctuation */
            if (punc_result == -1) {
                s_builder.append(ch);
                readable_flag = 1;
                } 
            /* If the char is punctuation */
            else {
                /* The context reach to empty or line break */
                if(s_builder.toString().isEmpty() || s_builder.toString().equals("\n")) {
                	
                	/* The punctuation is +,-,=,>,<  or | or & */
                	if((punc_result >= 0 && punc_result <= 4) || ch == '|' || ch == '&') {
               			int temp_char_int = readChar();
               			char temp_char = (char) temp_char_int;
               			int double_flag = 0; //If the flag=1, it means double punctuation
               			switch(ch) 
               			{
                           	case '+':
                           		if(temp_char == ch || temp_char == '=') //++ or +=
                           			double_flag = 1;
                           		break;
                           	case '-':
                           		if(temp_char == ch || temp_char == '-') //-- or -=
                           			double_flag = 1;
                           		break;
                           	case '<':         
                           		if(temp_char == '=') // <=
                            		double_flag = 1;
                            	break;
                           	case '>':         
                           		if(temp_char == '=') // >=
                           			double_flag = 1;
                           		break;
                           	case '=':
                           		if(temp_char == ch) // ==
                           			double_flag = 1;
                           		break;
                           	case '|':
                           		if(temp_char == ch)
                           			double_flag = 1;  // ||
                           		break;
                           	case '&':
                           		if(temp_char == ch)
                           			double_flag = 1;  // &&
                           		break;
               			}
                		/* For the previous check result of double punctuation, append the char twice */
                		if(double_flag == 1) {
                			s_builder = new StringBuilder();
               				s_builder.append(ch);
               				s_builder.append(temp_char);
               				System.out.println("<Operator, 1>:  " + s_builder.toString());
               				readable_flag = 1;
                		}
                		/* Print the analysis result */
                		else {
               				s_builder = new StringBuilder();
               				s_builder.append(ch);
               				System.out.println("<Operator, 1>:  " + s_builder.toString());
               				//Jump across the next char, because the temp_char occurred
               				ch = temp_char;
               				readable_flag = 0;
               			}
               		}
                	
                	/* The punctuation is / */
                	else if(punc_result == 6) {
                		int temp_int = readChar();
                		char temp_char= (char) temp_int;
                		int temp_punc_code = my_punctuation.punctuationCode(temp_char);
                		/* The double punctuation is double "/", means comments */
                		if(temp_punc_code == punc_result) {
                			System.out.println("<Comment Indicator, 6>:  " + "//");
               				printComment();
                		}
                		/* The double punctuation is "/*", means comment */
                		else if(temp_punc_code == 5) {
                			System.out.println("<Comment Indicator, 6>:  " + "/*");
                			printComment();
                			System.out.println("<Comment Indicator, 6>:  " + "*/");
                		}
                		
                		/* Output the analysis Result */
               			else {
               				s_builder= new StringBuilder();
               				s_builder.append(ch);
               				System.out.println("<Operator, 1>:  " + s_builder.toString());
               				readable_flag = 0;
               				ch = temp_char;
               			}
                	}
                	
                	/* The punctuation is '*' or '%' */
                	else if ((punc_result == 5 || punc_result == 7)) {
                		s_builder= new StringBuilder();
               			s_builder.append(ch);
               			System.out.println("<Operator, 1>:  " + s_builder.toString());
               			readable_flag = 1;
                	}
                	
                	/* The punctuation is '"' */
                	else if(punc_result == 17) {
               			int temp = readChar();
               			char temp_char= (char) temp;
               			StringBuilder temps_builder= new StringBuilder();
               			temps_builder.append(ch);
               			temps_builder.append(temp_char);
               			/* The following context is literal string */
               			while (temp_char != '"') {
               				temp = readChar();
               				temp_char= (char) temp;
               				temps_builder.append(temp_char);
               			}
               			System.out.println("<Literal String, 5>:  " + temps_builder.toString());
               			readable_flag = 1;
               		}
                	
                	/* The char is not space nor \n nor \r */
                	else if(ch != ' ' && ch != '\n' && ch != '\r') {
               			s_builder= new StringBuilder();
               			s_builder.append(ch);
               			System.out.println("<Separator, 3>:  " + s_builder.toString());
               			readable_flag = 1;
               		}
                	
                	/* Any other statement, the next could still read */
               		else
               			readable_flag = 1;
               		s_builder = new StringBuilder();
               		continue;
               }
               
               String str = s_builder.toString();
               /* The string following is not list in keyword */
               if (my_keyword.isKeyword(str) == false) {
                    boolean isIdentifier = Identifier.isIdentifier(str);
                    /* The context is identifier */
                    if (isIdentifier)
                    	System.out.println("<Identifier, 2>:  " + str);
                    /* The context as the form 'c' */
                    else if(str.charAt(0)=='\'' && str.charAt(2)=='\'' && str.length()==3)
                    	System.out.println("<Character, 4>:  " + str.charAt(1));
                    else {
                        int isNumber = Number.isNumber(str);
                        StringBuilder temp_s_builder = new StringBuilder();
                        /* The string is not number */
                        numberType(isNumber, str, ch, temp_s_builder, my_punctuation, readable_flag);
                    }
               }
               /* The string is keyword */
               else
            	   System.out.println("<Keyword, 8>:  " + str);
               s_builder = new StringBuilder();
               readable_flag = 0;
            }
        }
    }
}
