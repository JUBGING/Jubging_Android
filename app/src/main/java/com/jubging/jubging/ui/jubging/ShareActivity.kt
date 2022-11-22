package com.jubging.jubging.ui.jubging

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.databinding.ActivityShareBinding
import com.jubging.jubging.ui.banner.Banner2Fragment
import com.jubging.jubging.ui.banner.BannerFragment
import com.jubging.jubging.ui.base.BaseActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ShareActivity: BaseActivity<ActivityShareBinding>(ActivityShareBinding::inflate){

    override fun initAfterBinding() {
        //배너 연결
        val bannerAdapter = ShareBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment())
        bannerAdapter.addFragment(Banner2Fragment())

        binding.shareBannerVp.adapter = bannerAdapter
        binding.shareBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        //클릭시
        binding.shareDownloadBtnIv.setOnClickListener{
            imgSaveOnClick()
        }
        binding.shareDownloadIv.setOnClickListener{
            imgSaveOnClick()
        }

        binding.shareShareBtnIv.setOnClickListener{
            val feedIntent = Intent(Intent.ACTION_SEND)
            startActivity(intent)
        }
        binding.shareShareIv.setOnClickListener{
            val feedIntent = Intent(Intent.ACTION_SEND)
            startActivity(intent)
        }


    }

    private fun shareInstagram(uri: Uri) {
        val feedIntent = Intent(Intent.ACTION_SEND)
        feedIntent.type = "image/*"
        feedIntent.putExtra(Intent.EXTRA_STREAM, uri)
        feedIntent.setPackage("com.instagram.android")
        val storiesIntent = Intent("com.instagram.android")
        storiesIntent.setDataAndType(uri, "image/*")
        storiesIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        storiesIntent.setPackage("com.instagram.android")
        grantUriPermission(
            "com.instagram.android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        feedIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Intent>(storiesIntent))
        startActivity(feedIntent)
    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBitmap(): Bitmap {
        //기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels
        val totalBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
        val canvas = Canvas(totalBitmap) // 캔버스에 비트맵을 Mapping.
        canvas.drawColor(Color.WHITE) // 캔버스에 현재 설정된 배경화면색으로 칠한다.


        val imageView = binding.shareImgIv
        val imageViewBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        imageView.draw(imageViewCanvas)
        val imageViewLeft = ((backgroundWidth - imageView.width) / 2).toFloat()
        val imageViewTop = ((backgroundHeight - imageView.height) / 2).toFloat()
        canvas.drawBitmap(imageViewBitmap, imageViewLeft, imageViewTop, null)

        val logoImageView = binding.shareLogoIv
        val logoImageViewBitmap = Bitmap.createBitmap(logoImageView.width, logoImageView.height, Bitmap.Config.ARGB_8888)
        val logoImageViewCanvas = Canvas(logoImageViewBitmap)
        logoImageView.draw(logoImageViewCanvas)
        val logoImageViewLeft = ((imageViewLeft+imageView.width) - 50 * resources.displayMetrics.density).toFloat()
        val logoImageViewTop = (imageViewTop + 10 * resources.displayMetrics.density).toFloat()
        canvas.drawBitmap(logoImageViewBitmap, logoImageViewLeft, logoImageViewTop, null)

        val dataTextView = binding.shareDataLl
        val dataTextViewBitmap = Bitmap.createBitmap(dataTextView.width, dataTextView.height, Bitmap.Config.ARGB_8888)
        val dataTextViewCanvas = Canvas(dataTextViewBitmap)
        dataTextView.draw(dataTextViewCanvas)
        val marginBottom = (180 * resources.displayMetrics.density).toInt() // 180dp의 마진
        val dataTextViewLeft = (imageViewLeft + 10 * resources.displayMetrics.density).toFloat()
        val dataTextTextViewTop = imageViewTop + imageView.height - marginBottom
        canvas.drawBitmap(dataTextViewBitmap, dataTextViewLeft, dataTextTextViewTop, null)

        return totalBitmap
    }


    private fun imgSaveOnClick() {
        val bitmap = drawBitmap()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Q 버전 이상일 경우. (안드로이드 10, API 29 이상일 경우)
            saveImageOnAboveAndroidQ(bitmap)
            Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // Q 버전 이하일 경우. 저장소 권한을 얻어온다.
            val writePermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if(writePermission == PackageManager.PERMISSION_GRANTED) {
                saveImageOnUnderAndroidQ(bitmap)
                Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val requestExternalStorageCode = 1

                val permissionStorage = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                ActivityCompat.requestPermissions(this, permissionStorage, requestExternalStorageCode)
            }
        }
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        /*
        * ContentValues() 객체 생성.
        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
        * */
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        try {
            val fileItem = File("$dir/$fileName")
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}