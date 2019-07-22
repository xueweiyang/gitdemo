
#define TEXT_SZ 2048
typedef struct shared_use_st {
    int written;//0可写，非0可读
    char text[TEXT_SZ];//记录写入和读取的文本
} shared_use_st;
