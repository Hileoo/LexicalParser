
/**
 * This class store the array of special punctuation, and it could return the code of punctuation..
 */
public class Punctuation {
    char []punctuations = {
    		'+',
            '-',
            '=',
            '>',
            '<',
            '*',
            '/',
            '%',
            ';',
            ',',
            '{',
            '}',
            '(',
            ')',
            ' ',
            '\n',
            '\r',
            '"',
            '[',
            ']',
            '&',
            '|'
    };
    
    public int punctuationCode(char c) {
        int result;
        result = -1; //Defined by myself, as a symbol of the result is non-punctuation
        for(int i=0;i<punctuations.length;i++) {
            if(punctuations[i] == c) {
                //Defined by myself, as a symbol of the result is a general punctuation
            	result = 100 ;
                //The punctuation is +,-,=,>,<,*,/,% or "
                if(i < 8 || i == 17) 
                    result = i;
                break;
            }
        }
        return result;
    }

}
