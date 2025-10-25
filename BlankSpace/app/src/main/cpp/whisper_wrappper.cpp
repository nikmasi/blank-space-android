#include <jni.h>
#include <string>
#include <android/log.h>
// #include <whisper.h> // ⬅️ DODAJTE OVO ako već niste

#define LOG_TAG "WhisperJNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern "C" {

// Koristite noviju, preporučenu funkciju za inicijalizaciju
// NAPOMENA: Potrebno je da prosledite "whisper_full_params" strukturu, 
// ali za osnovni rad, možete koristiti podrazumevane postavke.

// Deklaracija eksterne funkcije iz libwhisper.so
struct whisper_context; // Samo deklaracija tipa da bi kompajler znao šta je to
extern "C" struct whisper_context * whisper_init_from_file_with_params(
    const char *path_model,
    struct whisper_full_params params);


JNIEXPORT jlong JNICALL
Java_com_example_blankspace_whisper_WhisperRecognizer_initContext(JNIEnv *env, jobject thiz, jstring model_path) {
    // Definisanje podrazumevanih parametara za inicijalizaciju
    // NAPOMENA: Obično se za Android definišu fiksni parametri. 
    // Za sada, neka bude nula.
    // Vi biste ovde trebali da kreirate strukturu whisper_context_params i prosledite je.
    
    // Za primer i rešavanje grešaka, koristićemo stari deprecated poziv, ali 
    // samo ako nemate pristup strukturi 'whisper_context_params' u ovom trenutku.
    
    // Ako se i dalje kompajlira sa zastarelom funkcijom (kao što je ranije bilo):

    const char *path = env->GetStringUTFChars(model_path, 0);
    LOGD("initContext pozvan sa modelom: %s", path);

    // KOD KOJI TREBA DA IZMENITE/ODKOMENTARIŠETE
    
    // 1. Deklaracija konteksta
    struct whisper_context *ctx = nullptr;

    // 2. Poziv funkcije
    // Iako je deprecated, ovo je najjednostavniji poziv za demonstraciju inicijalizacije:
    ctx = whisper_init_from_file(path);
    
    // 3. Oslobađanje stringa
    env->ReleaseStringUTFChars(model_path, path);
    
    // 4. Vraćanje pointera kao jlong
    if (ctx == nullptr) {
        LOGD("GREŠKA: Nije uspešna inicijalizacija Whisper konteksta.");
    }

    return reinterpret_cast<jlong>(ctx); // Vraća pointer, nula ako je neuspešno
}
// ... ostale funkcije
}