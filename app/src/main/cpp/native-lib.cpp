#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_kklv_mytest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++ kklv";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_kklv_mytest_MainActivity_stringHello(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("hello lzd");
}