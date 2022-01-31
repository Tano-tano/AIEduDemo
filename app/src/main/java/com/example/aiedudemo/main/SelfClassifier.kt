package com.example.aiedudemo.main

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.nio.MappedByteBuffer

import android.os.Environment

import java.io.File


class SelfClassifier(private val context: Context) {
    // TODO: Add a TF Lite interpreter as a field.
    private var interpreter: Interpreter? = null
    var isInitialized = false
        private set

    /** Executor to run inference task in the background. */
    private val executorService: ExecutorService = Executors.newCachedThreadPool()

    private var inputImageWidth: Int = 0 // will be inferred from TF Lite model.
    private var inputImageHeight: Int = 0 // will be inferred from TF Lite model.
    private var modelInputSize: Int = 0 // will be inferred from TF Lite model.

    fun initialize(): Task<Void?> {
        val task = TaskCompletionSource<Void?>()
        executorService.execute {
            try {
                initializeInterpreter()
                task.setResult(null)
            } catch (e: IOException) {
                task.setException(e)
            }
        }
        return task.task
    }

    @Throws(IOException::class)
    private fun initializeInterpreter() {
        // TODO: Load the TF Lite model from file and initialize an interpreter.
        val model = loadMappedFile("improved.tflite")
        val interpreter = Interpreter(model)
        // Read input shape from model file
        val inputShape = interpreter.getInputTensor(0).shape()
        inputImageWidth = inputShape[1]
        inputImageHeight = inputShape[2]
        modelInputSize = FLOAT_TYPE_SIZE * inputImageWidth * inputImageHeight * PIXEL_SIZE

        // Finish interpreter initialization
        this.interpreter = interpreter
        isInitialized = true
        Log.d(TAG, "Initialized TFLite interpreter.")
    }

    @Throws(IOException::class)
    fun loadMappedFile(filePath: String): MappedByteBuffer {
        val file = File(Environment.getExternalStorageDirectory().toString() + "/Pictures/AIEdudemo/" + filePath)
        val var9: MappedByteBuffer
        try {
            val inputStream = FileInputStream(file)
            var9 = try {
                val fileChannel = inputStream.channel
                fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            } catch (var12: Throwable) {
                try {
                    inputStream.close()
                } catch (var11: Throwable) {
                    var12.addSuppressed(var11)
                }
                throw var12
            }
            inputStream.close()
        } catch (var13: Throwable) {
            throw var13
        }
        return var9
    }

    private fun classify(bitmap: Bitmap): String {
        check(isInitialized) { "TF Lite Interpreter is not initialized yet." }

        // TODO: Add code to run inference with TF Lite.
        // Preprocessing: resize the input image to match the model input shape.
        val resizedImage = Bitmap.createScaledBitmap(
            bitmap,
            inputImageWidth,
            inputImageHeight,
            true
        )
        val byteBuffer = convertBitmapToByteBuffer(resizedImage)

        // Define an array to store the model output.
        val output = Array(1) { FloatArray(OUTPUT_CLASSES_COUNT) }

        // Run inference with the input data.
        interpreter?.run(byteBuffer, output)

        // Post-processing: find the digit that has the highest probability
        // and return it a human-readable string.
        val result = output[0]
        val maxIndex = result.indices.maxByOrNull { result[it] } ?: -1

        return "%s".format(LETTERS[maxIndex])
    }

    fun classifyAsync(bitmap: Bitmap?): Task<String> {
        val task = TaskCompletionSource<String>()
        executorService.execute {
            val result = bitmap?.let { classify(it) }
            task.setResult(result)
        }
        return task.task
    }

    fun close() {
        executorService.execute {
            // TODO: close the TF Lite interpreter here
            interpreter?.close()

            Log.d(TAG, "Closed TFLite interpreter.")
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(modelInputSize)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputImageWidth * inputImageHeight)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixelValue in pixels) {
            val r = (pixelValue shr 16 and 0xFF)
            val g = (pixelValue shr 8 and 0xFF)
            val b = (pixelValue and 0xFF)

            // Convert RGB to grayscale and normalize pixel value to [0..1].
            val normalizedPixelValue = (r + g + b) / 3.0f / 255.0f
            byteBuffer.putFloat(normalizedPixelValue)
        }

        return byteBuffer
    }

    companion object {
        private const val TAG = "SelfClassifier"

        private const val FLOAT_TYPE_SIZE = 4
        private const val PIXEL_SIZE = 1
        private const val LETTERS = "0123456789"
        private const val OUTPUT_CLASSES_COUNT = LETTERS.length
    }
}