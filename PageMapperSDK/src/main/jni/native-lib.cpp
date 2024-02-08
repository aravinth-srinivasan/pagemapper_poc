#include <jni.h>
#include <malloc.h>
#include <string.h>

extern "C" JNIEXPORT jobject
Java_com_raweng_pagemapper_pagemappersdk_utils_JNIUtils_jniCreateInstance(JNIEnv *env,
                                                                         jobject /* this */,
                                                                         jstring className) {
    const char *classNameCStr = env->GetStringUTFChars(className, nullptr);

    // Create a modifiable copy of the string
    char *classNameCopy = strdup(classNameCStr);

    // Replace dots with slashes in the copy
    for (int i = 0; classNameCopy[i] != '\0'; i++) {
        if (classNameCopy[i] == '.') {
            classNameCopy[i] = '/';
        }
    }

    jclass clazz = env->FindClass(classNameCopy);
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "()V");
    jobject instance = env->NewObject(clazz, constructor);

    env->ReleaseStringUTFChars(className, classNameCStr);
    free(classNameCopy);  // Don't forget to free the memory!

    return instance;
}