#include <jni.h>
#include <string>
#include "whisper.h"

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_blankspace_whisper_WhisperRecognizer_initContext(
        JNIEnv *env,
        jobject thiz,
        jstring jModelPath
) {
    const char *modelPath = env->GetStringUTFChars(jModelPath, 0);
    struct whisper_context *ctx = whisper_init_from_file(modelPath);
    env->ReleaseStringUTFChars(jModelPath, modelPath);
    return (jlong) ctx;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_blankspace_whisper_WhisperRecognizer_transcribe(
        JNIEnv *env,
        jobject thiz,
        jlong ctxPtr,
        jfloatArray jAudioData
) {
    struct whisper_context *ctx = (struct whisper_context *) ctxPtr;
    if (ctx == nullptr)
        return env->NewStringUTF("Context null");

    jfloat *audioData = env->GetFloatArrayElements(jAudioData, 0);
    jsize n_samples = env->GetArrayLength(jAudioData);

    whisper_full_params wparams = whisper_full_default_params(WHISPER_SAMPLING_GREEDY);
    int ret = whisper_full(ctx, wparams, audioData, n_samples);

    env->ReleaseFloatArrayElements(jAudioData, audioData, 0);

    if (ret != 0)
        return env->NewStringUTF("Failed");

    std::string result;
    int n_segments = whisper_full_n_segments(ctx);
    for (int i = 0; i < n_segments; ++i)
        result += whisper_full_get_text(ctx, i);

    return env->NewStringUTF(result.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_blankspace_whisper_WhisperRecognizer_freeContext(
        JNIEnv *env,
        jobject thiz,
        jlong ctxPtr
) {
    struct whisper_context *ctx = (struct whisper_context *) ctxPtr;
    if (ctx != nullptr)
        whisper_free(ctx);
}
