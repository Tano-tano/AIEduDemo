package com.example.aiedudemo.main

import android.content.ContentValues
import android.content.Intent
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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawCanvas
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawSelf : AppCompatActivity(){
    private var page = 1
    private var directoryName = ""

    private lateinit var drawView: DrawCanvas
    private var resetBtn: Button? = null
    private var saveBtn: Button? = null
    private var nextBtn: Button? = null
    private var backBtn: Button? = null
    private var picsNum: TextView? = null
    private var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        //５）Viewの取得⇒クリア処理
        drawView = findViewById(R.id.draw_view)
        resetBtn = findViewById(R.id.resetBtn)
        saveBtn = findViewById(R.id.saveBtn)
        nextBtn = findViewById(R.id.nextBtn)
        backBtn = findViewById(R.id.backBtn)
        picsNum = findViewById(R.id.picsnum)
        editText = findViewById(R.id.edit_text)

        picsNum?.text = page.toString()
        saveBtn?.isEnabled = false

        //キャンバスのリセット
        resetBtn?.setOnClickListener {
            drawView.clearCanvas()
        }

        //モデルを取り込む画面へ移行
        nextBtn?.setOnClickListener {
            val intent = Intent(application, ImportModel::class.java)
            startActivity(intent)
        }

        //戻るボタン
        backBtn?.setOnClickListener {
            finish()
        }


        //画像の保存
        saveBtn?.setOnClickListener {
            directoryName = editText?.text.toString()
            val filename = picsNum?.text.toString()
            val bitmap = getScreenShotFromView(drawView)
            if(bitmap != null){
                saveMediaToStorage(bitmap, filename, directoryName)
            }
            page += 1
            picsNum?.text = page.toString()
            drawView.clearCanvas()
        }

        //文字の更新を監視
        editText?.doAfterTextChanged {
            saveBtn?.isEnabled = editText?.text.toString() != ""
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
    private fun saveMediaToStorage(bitmap: Bitmap, f: String, d: String) {
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
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/AIEduDemo/$d")
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = File("${Environment.getExternalStorageDirectory()}/AIEduDemo/$d")
            if(!imagesDir.exists()){
                imagesDir.mkdirs()
            }
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Toast.makeText(this , "$filename saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
}