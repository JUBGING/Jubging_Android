package com.jubging.jubging.ui.jubging

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.jubging.jubging.ApplicationClass.Companion.bluetoothThread
import com.jubging.jubging.ApplicationClass.Companion.handlerd
import com.jubging.jubging.data.remote.picture.PictureService
import com.jubging.jubging.data.remote.picture.PictureView
import com.jubging.jubging.databinding.ActivityBluetoothBinding
import com.mummoom.md.data.remote.auth.PictureResponse
import kotlinx.android.synthetic.main.activity_bluetooth.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class BluetoothActivity: AppCompatActivity(),PictureView {
    private var mBinding: ActivityBluetoothBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_CONNECT,

        )
    val PERMISSIONS_S_ABOVE = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val REQUEST_ALL_PERMISSION = 2

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private lateinit var broadcastReceiver: BroadcastReceiver

    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            var str = msg.data.getString("data")
            if(str?.get(0)?.equals('-') == true){
                str = "0.00"
            }
            Log.d("test", str.toString())
            Log.d("TEST", this.looper.toString())
            weight.setText(str)
        }
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, "활성화", Toast.LENGTH_SHORT).show()
            } else if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(applicationContext, "비활성화", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handlerd = handler
        if (bluetoothAdapter == null) {

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!hasPermissions(this, PERMISSIONS_S_ABOVE)) {
                    requestPermissions(PERMISSIONS_S_ABOVE, REQUEST_ALL_PERMISSION)
                }
            } else {
                if (!hasPermissions(this, PERMISSIONS)) {
                    requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
                }
            }
        }
        if(((Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) && hasPermissions(this,PERMISSIONS_S_ABOVE)) || ((Build.VERSION.SDK_INT < Build.VERSION_CODES.S)&&hasPermissions(this,PERMISSIONS))){
            val context: Context = this
            var result:Boolean = false
            val dialog = ProgressDialog(this@BluetoothActivity)
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setMessage("줍줍이 찾는중...")
            dialog.show()

            GlobalScope.launch {
                result = bluetoothConnection()
                dialog.dismiss()
                Log.d("test", result.toString())
                if(result == false){
                    finish()
                }
                else{

                }
            }

            mBinding = ActivityBluetoothBinding.inflate(layoutInflater)
            setContentView(binding.root)


            //확인 버튼 누르면 다음 액티비티 뜨도록
            binding.bluetoothConfirmTv.setOnClickListener {
                val intent = Intent(this, TrashNoticeActivity ::class.java)
                this.intent.getIntExtra("walk",0).let { intent.putExtra("walk", it)}
                this.intent.getStringExtra("time").let { intent.putExtra("time", it)}
                this.intent.getFloatExtra("distance",0.0f).let { intent.putExtra("distance", it)}
                this.intent.getIntExtra("kcal",0).let { intent.putExtra("kcal", it)}
                this.intent.getBooleanExtra("tongs_return",true).let { intent.putExtra("tongs_return", it)}
                this.intent.getIntExtra("jubjubi_id",0).let { intent.putExtra("jubjubi_id", it)}
                this.intent.getIntExtra("tongs_id",0).let { intent.putExtra("tongs_id", it)}
                this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it)}
                Log.d("줍깅블루투스",this.intent.getIntExtra("jubjubi_id",0).toString())
                intent.putExtra("WEIGHT", weight.text)
                putUri(this.intent.getStringExtra("URI")!!,weight.text.toString())
                startActivity(intent)
            }
        }
        else{
            finish()
        }

    }

    //사진 서버에 올리기 & 무게 보내주기
    private fun putUri(uri:String,weight:String){
        Log.d("카메라 uri", uri)
        val file = File(uri)
        var requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(),file)
        var body = MultipartBody.Part.createFormData("image",file.name,requestFile)
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),weight)
        PictureService.sendUri(this,body,requestBody)
        Log.d("카메라풋파일", file.toString())
        Log.d("카메라 weight requestBody", requestBody.toString())
        Log.d("카메라풋", body.toString())
    }

    private suspend fun bluetoothConnection():Boolean {
        //블루투스 어댑터 가져오기
        //블루투스 지원하지 않는 기기인 경우
        var ret: Boolean = false
        if (bluetoothAdapter == null) {

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!hasPermissions(this, PERMISSIONS_S_ABOVE)) {
                    requestPermissions(PERMISSIONS_S_ABOVE, REQUEST_ALL_PERMISSION)
                }
                else {
                    ret = jubjubiConnect();
                }
            } else {
                if (!hasPermissions(this, PERMISSIONS)) {
                    requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
                }
                else {
                    ret = jubjubiConnect();
                }
            }
        }
        if (bluetoothAdapter?.isEnabled == false) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activityResultLauncher.launch(intent)
        }
        return ret
    }

    @SuppressLint("MissingPermission")
    private fun jubjubiConnect(): Boolean{
        val pairedDevices: Set<BluetoothDevice> =
            bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
        var jubjubiDevice: BluetoothDevice? = null
        var ret: Boolean = false
        if (!pairedDevices.isEmpty()) {
            pairedDevices.forEach { device ->
                if (device.name == "jubjubi") {
                    jubjubiDevice = device
                    //연결
                    ret = connectDevice(jubjubiDevice!!.address)
                }
            }
        }

        // 블루투스 기기 검색 브로드캐스트
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // BluetoothDevice 객체 획득
                        val device =
                            intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        // 기기 이름
                        val deviceName = device?.name
                        // 기기 MAC 주소
                        val deviceHardwareAddress = device?.address
                        if (deviceName != null && deviceHardwareAddress != null) {

                            //맥주소로 바꾸자
                            if (deviceName == "jubjubi") {
                                jubjubiDevice = device
                                //연결
                                ret = connectDevice(jubjubiDevice!!.address)
                            }
                        }
                    }
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))

        if (jubjubiDevice == null) {
            return false
        }
        return ret
    }

    //줍줍이 연결 요청
    @SuppressLint("MissingPermission")
    private fun connectDevice(deviceAddress: String): Boolean {
        bluetoothAdapter?.let { adapter ->
            // 기기 검색을 수행중이라면 취소
            if (adapter.isDiscovering) {
                adapter.cancelDiscovery()
            }

            // 서버의 역할을 수행 할 Device 획득
            val device = adapter.getRemoteDevice(deviceAddress)
            // UUID 선언
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            try {
                bluetoothThread = ConnectThread(uuid, device, handler)
                bluetoothThread.run()
            } catch (e: Exception) { // 연결에 실패할 경우 호출됨
                return false
            }
        }
        return true
    }

    //블루투스 권한
    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            var permissionCheck = checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
            permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
            if (permissionCheck != 0) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1001
                )
            } else {
                Log.d("checkPermission", "No need to check permissions. SDK version < LoLLIPOP")
            }
        }
    }
    // 기기 검색
    @SuppressLint("MissingPermission")
    fun findDevice() {
        if(bluetoothAdapter?.isEnabled == true){
            checkBTPermissions()
            if(bluetoothAdapter?.isDiscovering == true){
                bluetoothAdapter?.cancelDiscovery()
                return
            }
            bluetoothAdapter.startDiscovery()
        } else{
            Toast.makeText(applicationContext, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show()
        }
    }
    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address
                    if (deviceName != null) {
                        Log.d("블루투스", deviceName)
                    } else {
                        Log.d("블루투스", "null")
                    }
                }
            }
        }
    }
    //권한요청 콜백
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                    //jubjubiConnect()
                } else {
                    requestPermissions(permissions, REQUEST_ALL_PERMISSION)
                    Toast.makeText(this, "Permissions must be granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }

    override fun onPictureLoading() {
    }

    override fun onPictureSuccess(pictureResponse: PictureResponse) {
        Log.d("카메라 성공",pictureResponse.toString())
    }

    override fun onPictureFailure(errorCode: Int, message: String) {
        Log.d("카메라 오류",message)
        when(errorCode){
            4043 ->{
                Toast.makeText(this, "진행 중인 줍깅 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }
}