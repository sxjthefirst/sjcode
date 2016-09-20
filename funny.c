/* http://www.verycomputer.com/174_ea2ff642bb3b9e4e_1.htm */
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

main(void)
{
 fork(); printf("hee\n");
 fork(); printf("ha\n");
 fork(); printf("ho\n");
}

