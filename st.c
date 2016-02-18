#include <sys/stat.h>
#include <stdio.h>
main(int argc, char *argv[])
{
 struct stat buff;
 char *filename=argv[1];
 stat(filename, &buff);
 printf("%s takes %ld blocks of %ld each \n",filename, buff.st_blocks, buff.st_blksize);
 printf("and is owned by %d of group %d with mode %d \n",buff.st_uid, buff.st_gid, buff.st_mode);
}
