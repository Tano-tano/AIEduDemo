package com.example.aiedudemo.main

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawCanvas
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawSelf : AppCompatActivity(){
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        //５）Viewの取得⇒クリア処理
        val drawView: DrawCanvas = findViewById(R.id.draw_view)
        val resetBtn: Button = findViewById(R.id.resetBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)
        val picsNum: TextView = findViewById(R.id.picsnum)

        picsNum.text = page.toString()

        resetBtn.setOnClickListener {
            drawView.clearCanvas()
        }

        saveBtn.setOnClickListener {
            var filename = picsNum.text.toString()
            val bitmap = getScreenShotFromView(drawView)
            if(bitmap != null){
                saveMediaToStorage(bitmap, filename)
            }
            page += 1
            picsNum.text = page.toString()
            drawView.clearCanvas()
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap?{
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    // this method saves the image to gallery
    private fun saveMediaToStorage(bitmap: Bitmap, f: String) {
        // Generating a file name
        val filename = "$f.png"


        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/AIEduDemo/")
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = File("${Environment.getExternalStorageDirectory()}/AIEduDemo/")
            if(!imagesDir.exists()){
                imagesDir.mkdirs()
            }
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
}