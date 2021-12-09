package com.example.aiedudemo.main

//import com.divyanshu.draw.widget.DrawView
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.mtjin.library.DrawView
import kotlin.math.floor
import android.widget.Toast

import android.graphics.Bitmap.CompressFormat

import android.os.Environment
import androidx.core.app.ActivityCompat
import java.io.*


class DrawSelf : AppCompatActivity(){
    private var drawView: DrawView? = null
    private var resetButton: Button? = null
    private var saveButton: Button? = null
    private var pics: TextView? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        drawView = findViewById(R.id.draw_view)
        drawView?.setStrokeWidth(70.0f)
        drawView?.setPenColor(Color.WHITE)
        drawView?.setBackgroundColor(Color.BLACK)
        resetButton = findViewById(R.id.resetBtn)
        saveButton = findViewById(R.id.saveBtn)
        pics = findViewById(R.id.picsnum)


        /// リセットボタン
        resetButton?.setOnClickListener {
            drawView?.clear()  /// bitmap初期化メソッドを呼び出す
        }

        /// 画像の保存
        saveButton?.setOnClickListener {
            var x = pics?.text.toString()
            var add = x.toInt() + 1

            if(add <= 5){
                pics?.text = add.toString()
                var x = drawView?.saveFileDrawViewGetUri()
                var bitmap : Bitmap? = x?.let { it1 -> convertBitmap(it1) }
                outputStream(bitmap, add)

                Log.d(TAG,"===============$x=================")
                var y = x?.let { it1 -> getFileName(this, it1) }
                Log.d(TAG,"===============$y=================")
                drawView?.clear()
            }else {

            }
        }
    }

    private fun outputStream(bitmap: Bitmap?, add: Int) {
        try {
            val extStorageDir: File = Environment.getExternalStorageDirectory()
            val file = File(
                extStorageDir.absolutePath
                    .toString() + "/" + Environment.DIRECTORY_DCIM,
                getFileName(add)
            )
            val outStream = FileOutputStream(file)
            bitmap?.compress(CompressFormat.PNG, 100, outStream)
            outStream.close()
            Toast.makeText(
                this,
                "Image saved",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFileName(add : Int): String? {
        return "Image$add"
    }


    private fun convertBitmap(uri: Uri): Bitmap? {
        var input : InputStream? = this.contentResolver.openInputStream(uri)

        var onlyBoundsOptions : BitmapFactory.Options? = BitmapFactory.Options()
        onlyBoundsOptions?.inJustDecodeBounds = true
        onlyBoundsOptions?.inPreferredConfig = Bitmap.Config.ARGB_8888
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
        input?.close()

        if((onlyBoundsOptions?.outWidth == -1) || (onlyBoundsOptions?.outHeight == -1)){
            return null
        }

        val originalSize =
            if (onlyBoundsOptions!!.outHeight > onlyBoundsOptions!!.outWidth) onlyBoundsOptions!!.outHeight else onlyBoundsOptions!!.outWidth
        val THUMBNAIL_SIZE = 64
        val ratio = if (originalSize > THUMBNAIL_SIZE) originalSize / THUMBNAIL_SIZE else 1.0

        var bitmapOptions : BitmapFactory.Options? = BitmapFactory.Options()
        bitmapOptions?.inSampleSize = getPowerOfTwoForSampleRatio(ratio)
        bitmapOptions?.inDither = true
        bitmapOptions?.inPreferredConfig = Bitmap.Config.ARGB_8888
        input = this.contentResolver.openInputStream(uri)

        var bitmap :Bitmap? = BitmapFactory.decodeStream(input, null, bitmapOptions)
        input?.close()
        return bitmap
    }

    private fun getPowerOfTwoForSampleRatio(ratio: Number): Int {
        val k = Integer.highestOneBit(floor(ratio as Double).toInt())
        return if (k == 0) 1 else k
    }

    //Debug
    private fun getFileName(context: Context, uri: Uri): String? {

        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }

        return uri.path?.substring(uri.path!!.lastIndexOf('/') + 1)
    }



}