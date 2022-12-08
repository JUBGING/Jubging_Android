package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.hardware.camera2.*
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.jubging.jubging.databinding.ActivityCameraBinding
import java.io.File
import java.util.*


private const val REQUEST_CODE_FOR_IMAGE_CAPTURE = 100
const val TAG = "CameraActivity"


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val dir = externalCacheDir
            val newFile: File = File.createTempFile("photo_", ".jpg", dir)
            val uri = FileProvider.getUriForFile(this, "$packageName.provider", newFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, REQUEST_CODE_FOR_IMAGE_CAPTURE)
            photoFile = newFile
            Log.d("카메라파일",photoFile.toString())
            Log.d("카메라파일이름",photoFile.name)
            binding.cameraConfirmTv.setOnClickListener() {
                val nextIntent = Intent(this, BluetoothActivity::class.java)
                this.intent.getIntExtra("walk",0).let { nextIntent.putExtra("walk", it)}
                this.intent.getIntExtra("time",0).let { nextIntent.putExtra("time", it)}
                this.intent.getFloatExtra("distance",0.0f).let { nextIntent.putExtra("distance", it)}
                this.intent.getIntExtra("kcal",0).let { nextIntent.putExtra("kcal", it)}
                this.intent.getBooleanExtra("tongs_return",true).let { nextIntent.putExtra("tongs_return", it)}
                this.intent.getIntExtra("jubjubi_id",0).let { nextIntent.putExtra("jubjubi_id", it)}
                this.intent.getIntExtra("tongs_id",0).let { nextIntent.putExtra("tongs_id", it)}
                Log.d("줍깅카메라",this.intent.getIntExtra("jubjubi_id",0).toString())
                nextIntent.putExtra("URI", newFile.path)
                startActivity(nextIntent)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FOR_IMAGE_CAPTURE -> {
                if(resultCode == RESULT_OK){
                    Glide.with(this).load(photoFile).into(binding.cameraImageIv)
                } else{
                    Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

}