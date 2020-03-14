
/**
 * This class used to determine whether the context is identifier.
 */
public  class Identifier {
    public static boolean isIdentifier(String str) {
        char key;
        boolean identifier = true;
        int i = 0;
        int state = 1;
        while(i < str.length()) {
            switch (state) {
                case 1:
                    key = str.charAt(i);
                    if(str.equals("stdio.h")) {
                    	break;
                    }
                    /* Capital Letter, or Lower-case Letter, or "_" */
                    else if((key>=65 && key<=90) || (key>=97 && key<=122) || key==95)
                        state = 2;
                    else {
                    	identifier = false;
                        break;
                    }
                    
                case 2:
                case 3:
                	key = str.charAt(i);
                	/* Capital Letter, or Lower-case Letter, or "_" */
                    if((key>=65 && key<=90) || (key>=97 && key<=122) || key==95)
                        state = 2;
                    /* Integer number from 0 to 9 */
                    else if(key>=48 && key<=57)
                        state = 3;
                    else {
                    	identifier = false;
                        break;
                    }
                    break;
            }
            i++;
        }
        return identifier;
    }
}
