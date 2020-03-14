//Author: Xinlong
#include <stdio.h>
int main()
{
    int length;
    char ch = 'c';
    printf("The number of books is 8");
    
    /* It is a loop */
    for(int i=2; i<length; i++)
    {
        for(int j=1; j<length; j++)
        {
            if(i == 5) {
                break;
            }
            printf("*");
        }
    }
    
}
