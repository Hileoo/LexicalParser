
/**
 * This class store the array of special keyword, and it could determine whether is keyword.
 */
public class PreservedWords {
    private String p_word[] = {"void", "double", "int", "struct", "float", "if", "else", "for", "do", "while", "case", "loop", "break", "long", "switch", "enum", 	"typedef",
            "char", "return", "union", "continue", "signed", "static", "default", "goto", "sizeof", "volatile", "const", "short","unsigned", "#include"};
    
    public boolean isKeyword(String s) {
        boolean bool = false;
        for (int i=0;i<p_word.length;i++) {
            if(p_word[i].equals(s)) {
                bool = true;
                break;
            }
        }
        return bool;
    }
}
