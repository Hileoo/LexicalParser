
/**
 * This class used to determine the type of the number.
 */
public class Number {
	public static int isNumber(String str)
    {
        int result_number = 1;
        int state = 1;
        int key;
        int i = 0;

        while (i < str.length()) {
            switch (state) {
                case 1:
                    key = str.charAt(i);
                    i++;
                    /* Beginning with "0" */
                    if(key==48 && i<str.length()) {
                        int next_key = str.charAt(i);
                        //beginning with "0x"
                        if(next_key == 120) {
                            state = 2;
                            result_number = 3;
                        }
                        //next key char is 0-9
                        else if(next_key>=48 && next_key<=57) {
                        	result_number = 2;
                            state = 2; //octal
                        }
                        //decimal with "."
                        else if(next_key == 46)
                        	result_number = 4;
                    }
                    /* Beginning with 1-9 */
                    else if(key>=49 && key<=57)
                        state = 2;
                    else {
                    	result_number = -1;
                        break;
                    }
                    break;
                    
                case 2:
                    key = str.charAt(i);
                    /* Beginning with 1-9 */
                    if(key>=48 && key<=57)
                        state=2;
                    /* decimal with "." */
                    else if(key == 46) {
                        state = 3;
                        if(result_number == 1)
                        	result_number = 4;
                    }
                    else {
                    	result_number = -1;
                        break;
                    }
                    break;
                case 3:
                    key = str.charAt(i);
                    /* Beginning with 1-9 */
                    if(key>=48 && key<=57)
                        state = 3;
                    /* Scientific Computing */
                    else if(key == 'e') {
                        if(result_number == 4)
                            state = 4;
                        else
                            return -1;
                    }
                    else {
                    	result_number = -1;
                        break;
                    }
                    break;
                case 4:
                    key = str.charAt(i);
                    if(key>=48 && key<=57) {
                    	result_number = -1;
                        break;
                    }
            }
            i++;
        }
        return result_number;
    }

}
