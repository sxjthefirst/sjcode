/* using the atexit library fn */
#include <stdio.h>
#include <stdlib.h>
int main()
{
	void f2(void), f3(void);
	void f1(void);
	atexit(f1);
	atexit(f2);
	atexit(f3);
	f2();
	printf("gettting ready to return \n");
	//return(0);
	printf("gettting ready to exit \n");
	exit(0);
}

void f1(void)
{
	printf("Doing F1\n");
	// exit(0);
}
void f2(void)
{
	printf("Doing F2\n");
	exit(0);
}
void f3(void)
{
	printf("Doing F3\n");
	exit(-1);
}
