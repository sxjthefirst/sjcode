#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

main(void)
{
 fork(); printf("hee\n");
 fork(); printf("ha\n");
 fork(); printf("ho\n");
}

