package com.example.blankspace.whisper

// Pakovanje (Package) mora biti isto kao u C++ implementaciji

class WhisperRecognizer {

    companion object {
        init {
            System.loadLibrary("omp")
            System.loadLibrary("ggml-base")
            System.loadLibrary("ggml-cpu")
            System.loadLibrary("ggml")
            // A. Učitavanje biblioteke
            // Ime biblioteke je "whisper" jer je fajl "libwhisper.so"
            System.loadLibrary("whisper")

        }
    }

    // B. Definicija Native Metoda

    /**
     * Inicijalizuje Whisper motor sa modelom.
     * @param modelPath Putanja do ggml modela (.bin).
     * @return C++ pointer (long) na kontekst ili 0 ako inicijalizacija ne uspe.
     */
    external fun initContext(modelPath: String): Long

    /**
     * Pokreće transkripciju za dati audio bafer.
     * @param contextPointer Pointer na inicijalizovani Whisper kontekst.
     * @param audioData Bafer sa 16-bit float audio podacima (16kHz).
     * @return Transkribovani tekst.
     */
    external fun transcribe(contextPointer: Long, audioData: FloatArray): String

    /**
     * Oslobađa memoriju (briše C++ kontekst).
     * @param contextPointer Pointer na kontekst.
     */
    external fun freeContext(contextPointer: Long)

}

object WhisperHelper {

    init {
        // Učitaj nativnu biblioteku pri startu
        System.loadLibrary("whisper")
    }

    external fun initContext(modelPath: String): Long
    external fun transcribe(ctxPointer: Long, audioFilePath: String): String?
    external fun freeContext(ctxPointer: Long)

    fun transcribeAudioWhisper(audioFilePath: String): String {
        val ctxPointer = initContext("/data/user/0/com.example.blankspace/files/ggml-tiny.bin")
        val result = transcribe(ctxPointer, audioFilePath)
        freeContext(ctxPointer)
        return result ?: ""
    }
}