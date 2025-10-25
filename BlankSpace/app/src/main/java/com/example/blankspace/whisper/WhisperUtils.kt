package com.example.blankspace.whisper


import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun loadAudioAsFloatArray(filePath: String): FloatArray {
    val file = File(filePath)
    val bytes = FileInputStream(file).use { it.readBytes() }

    val audioBytes = bytes.copyOfRange(44, bytes.size)

    val floatBuffer = FloatArray(audioBytes.size / 2)
    val byteBuffer = ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN)

    for (i in floatBuffer.indices) {
        val sample = byteBuffer.short.toInt()
        floatBuffer[i] = sample / 32768.0f
    }

    return floatBuffer
}

fun transcribeAudioWhisper(audioFilePath: String): String {
    val recognizer = WhisperRecognizer()
    val ctxPointer = recognizer.initContext("/data/user/0/com.example.blankspace/files/ggml-tiny.bin")

    val audioFloats = loadAudioAsFloatArray(audioFilePath)
    val result = recognizer.transcribe(ctxPointer, audioFloats)

    recognizer.freeContext(ctxPointer)
    return result
}
