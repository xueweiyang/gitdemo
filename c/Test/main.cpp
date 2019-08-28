#include <iostream>
#include <ctime>
#include <cstring>
#include <fstream>
#include <iostream>
#include <vector>
#include <cstdlib>
#include <string>
#include <stdexcept>
#include <csignal>
#include <unistd.h>
#include <pthread.h>

using namespace std;
typedef int fee;
enum color {red,green=4,blue} c;
void func(void);
 int count=10;
extern void write_extern();
void printBook(struct Books book);
void printBook2(struct Books *book);
struct Books{
char title[50];
char author[50];
int bookid;
};
//class Shape{
//public:
//    void setWidth(int w) {
//        width = w;
//    }
//    void setHeight(int h) {
//        height = h;
//    }
//protected:
//    int width;
//    int height;
//};
//class PaintCost{
//public:
//    int getCost(int area){
//        return area*70;
//    }
//};
//class Rectangle : public Shape,public PaintCost{
//
//public:
//    int getArea() {
//    return (width*height);
//    }
//};

class D {
public:
    D(){cout<<"D()"<<endl;}
    ~D(){cout<<"~D()"<<endl;}
protected:
    int d;
};

class B: virtual public D{
public:
    B(){cout<<"B()"<<endl;}
    ~B(){cout<<"~B()"<<endl;}
protected:
    int b;
};

class A: virtual public D{
public:
    A(){cout<<"A()"<<endl;}
    ~A(){cout<<"~A()"<<endl;}
protected:
    int a;
};

class C:public B,public A{
public:
    C(){cout<<"C()"<<endl;}
    ~C(){cout<<"~C()"<<endl;}
protected:
    int c;
};

class PrintData{
public:
    void print(int i){
    cout<<"整数:"<<i<<endl;
    }
    void print(char c[]) {
        cout<<"字符串:"<<c<<endl;
    }
};

class Box{
public:
    double getVolume(){
    return length * breadth*height;
    }
    void setLength(double len){
    length=len;
    }
    void setBreadth(double bre){
    breadth=bre;
    }
    void setHeight(double hei){
    height=hei;
    }
    //重载+运算符
    Box operator+(const Box& b){
        Box box;
        box.length=this->length +b.length;
        box.breadth=this->breadth+b.breadth;
        box.height = this->height+b.height;
        return box;
    }
private:
    double length;
    double breadth;
    double height;
};

class Shape {
   protected:
      int width, height;
   public:
      Shape( int a=0, int b=0)
      {
         width = a;
         height = b;
      }
      //纯虚函数
      virtual int area() = 0;
};
class Rectangle: public Shape{
   public:
      Rectangle( int a=0, int b=0):Shape(a, b) { }
      int area ()
      {
         cout << "Rectangle class area :" <<endl;
         return (width * height);
      }
};
class Triangle: public Shape{
   public:
      Triangle( int a=0, int b=0):Shape(a, b) { }
      int area ()
      {
         cout << "Triangle class area :" <<endl;
         return (width * height / 2);
      }
};

namespace first_space{
void func() {
cout<<"inside first_space"<<endl;
}
}

namespace second_space{
void func(){
cout<<"inside second_space"<<endl;
}
}

template <typename T>
inline T const& Max(T const& a, T const& b)
{
    return a < b?b:a;
}

template <class T>
class Stack{
private:
    vector<T> elems;
public:
    void push(T const&);
    void pop();
    T top() const;
    bool empty() const{
    return elems.empty();}
};

template <class T>
void Stack<T>::push(T const& elem)
{
    elems.push_back(elem);
}

template <class T>
void Stack<T>::pop()
{
    if(elems.empty()) {
        throw out_of_range("Stack<>::pop(): empty stack");
    }
    elems.pop_back();
}

template <class T>
T Stack<T>::top() const
{
    if (elems.empty()){
        throw out_of_range("Stack<>::top():empty stack");
    }
    return elems.back();
}

#define MKSTR( x ) #x
#define contact(x,y) x##y

void signalHandler(int signum)
{
    cout<<"Interrupt signal"<<signum<<"received"<<endl;
    exit(signum);
}

void *say_hello(void* args)
{
    cout<<"hello runoob"<<endl;
    return 0;
}
#define NUM_THREADS 5

void *printHello(void *threadid)
{
    int tid=*((int*)threadid);
    cout <<"hello 线程id:"<<tid<<endl;
    pthread_exit(NULL);
}
int main()
{

    pthread_t threads[NUM_THREADS];
    int indexes[NUM_THREADS];
    int rc;
    int i;
    for(i=0;i<NUM_THREADS;i++){
        cout<<"main():创建线程"<<i<<endl;
        indexes[i]=i;
        rc=pthread_create(&threads[i],NULL,printHello,(void *)&(indexes[i]));
        if(rc){
                            cout<<"error:无法创建线程"<<rc<<endl;
                            exit(-1);
                          }
    }
    pthread_exit(NULL);

//    pthread_t tids[NUM_THREADS];
//    for(int i=0;i<NUM_THREADS;++i){
//        int ret = pthread_create(&tids[i],NULL,say_hello,NULL);
//        if(ret!=0){
//            cout<<"pthread_create error:code="<<ret<<endl;
//        }
//    }
//    pthread_exit(NULL);

//    signal(SIGINT, signalHandler);
//
//    while(1)
//    {
//        cout<<"going to sleep.."<<endl;
//        sleep(1);
//    }

//cout << MKSTR(hello c++) << endl;
//cout<<contact(1,234)<<endl;

//try{
//    Stack<int> intStack;
//    Stack<string> stringStack;
//
//    intStack.push(7);
//    cout<<intStack.top()<<endl;
//
//    stringStack.push("hello");
//    cout<<stringStack.top()<<endl;
//    stringStack.pop();
//    stringStack.pop();
//}catch(exception const& ex) {
//    cerr<<"Exception:"<<ex.what()<<endl;
//    return -1;
//}

//int i=39;
//int j=20;
//cout << "Max(i,j):"<<Max(i,j)<<endl;
//double f1=13.5;
//double f2=20.7;
//cout<<"Max(f1,f2):"<<Max(f1,f2)<<endl;
//string s1="hello";
//string s2="world";
//cout<<"Max(s1,s2):"<<Max(s1,s2)<<endl;

//first_space::func();
//second_space::func();

//    int **p;
//    int i,j;
//    p=new int *[4];
//    for (i=0;i<4;i++){
//        p[i]=new int[8];
//    }
//    for(i=0;i<4;i++){
//        for(j=0;j<8;j++){
//            p[i][j]=j*i;
//        }
//    }
//    for(i=0;i<4;i++){
//        for(j=0;j<8;j++){
//            if(j==0)cout<<endl;
//            cout<<p[i][j]<<endl;
//        }
//    }
//    for(i=0;i<4;i++){
//        delete [] p[i];
//    }
//    delete [] p;

//    double* pvalue=NULL;
//    pvalue = new double;
//    *pvalue=2949.44;
//    cout<<"value of p:"<<*pvalue<<endl;
//    delete pvalue;

//    char data[100];
//    ofstream outfile;
//    outfile.open("afile.txt");
//    cout<<"Enter your name: ";
//    cin.getline(data,100);
//
//    outfile<<data<<endl;
//    outfile.close();
//
//    //读模式打开文件
//    ifstream infile;
//    infile.open("afile.txt");
//    cout<<"read from file"<<endl;
//    infile>>data;
//    cout<<data<<endl;
//    infile.close();

//    Shape *shape;
//    Rectangle rec(10,7);
//    Triangle tri(10,5);
//
//    shape=&rec;
//    shape->area();
//
//    shape=&tri;
//    shape->area();

//    Box box1;
//    Box box2;
//    Box box3;
//
//    box1.setLength(1.0);
//    box1.setBreadth(2.0);
//    box1.setHeight(3.0);
//
//    box2.setLength(1.0);
//    box2.setBreadth(2.0);
//    box2.setHeight(3.0);
//
//    cout << "volume box1:"<<box1.getVolume()<<endl;
//    cout << "volume box2:"<<box2.getVolume()<<endl;
//
//    box3=box1+box2;
//    cout << "volume box3:"<<box3.getVolume()<<endl;

//    PrintData printData;
//    printData.print(2);
//    printData.print(2.4d);
//    printData.print("hello c++");


//    C c;
//    cout << "size:"<<sizeof(c) <<endl;

//    Rectangle rect;
//    rect.setWidth(5);
//    rect.setHeight(7);
//    cout << "total area:"<<rect.getArea()<<" 花费:"<<rect.getCost(rect.getArea())<<endl ;

//    Books books1;
//    Books books2;
//
//    strcpy(books1.title,"c++教程");
//    strcpy(books1.author,"Runoob");
//    books1.bookid=123;
//
//    cout<<"一书标题:"<<books1.title<<" 一作者:"<<books1.author<<" id:"<<books1.bookid<<endl;
//    printBook(books1);
//    printBook2(&books1);

//    cout << "bool字数:" << sizeof(bool) << endl;
//    cout << "int字节数:" << sizeof(int) << endl;
//    fee a = 1;
//    cout << a << endl;
//
//    c=blue;
//    cout << "blue" << c << endl;
//    while(count--){
//        func();
//    }
//count=3;
//write_extern();
//int var1;
//char var2[10];
//cout << "var1地址:"<<&var1<<endl;
//cout << "var2地址:"<<&var2<<endl;

//int var=20;
//int *ip;
//ip=&var;
//cout<<"value of var:"<<var<<endl;
//cout<<"address of var:"<<&var<<endl;
//cout << "address in ip:"<<ip<<endl;
//cout << "value of *ip:"<<*ip<<endl;

//int i;
//double d;
//int& r=i;
//double& s=d;
//i=5;
//cout<<"value of i:"<<i<<endl;
//cout<<"value of i refrence:"<<r<<endl;
//d=11.4;
//cout<<"value of d:"<<d<<endl;
//cout<<"value of d refrence:"<<s<<endl;

//time_t now =time(0);
//char* dt=ctime(&now);
//cout<<"本地日期:"<<dt<<endl;
//
//tm *gmtm=gmtime(&now);
//dt=asctime(gmtm);
//cout<<"utc 日期:"<<dt<<endl;

    return 0;
}
void printBook2(struct Books *book){
    cout << "标题:"<<book->title<<" 作者"<<book->author<<endl;
}
void printBook(struct Books book){
    cout << "标题:"<<book.title<<" 作者"<<book.author<<endl;
}

void func(void) {
    static int i =5;
    i++;
    cout << "变量i:"<<i << " 变量count:" << count<<endl;
}
