#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "WhisperJNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern "C" {

// Ovo MORA da se zove tačno ovako prema Kotlin paketu i klasi:
JNIEXPORT jlong JNICALL
Java_com_example_blankspace_whisper_WhisperRecognizer_initContext(JNIEnv *env, jobject thiz, jstring model_path) {
    const char *path = env->GetStringUTFChars(model_path, 0);
    LOGD("initContext pozvan sa modelom: %s", path);

    // Ovde pozovi Whisper init funkciju iz whisper.cpp (primer)
    // whisper_context *ctx = whisper_init_from_file(path);
    // return reinterpret_cast<jlong>(ctx);

    env->ReleaseStringUTFChars(model_path, path);
    return 0; // privremeno
}

}
