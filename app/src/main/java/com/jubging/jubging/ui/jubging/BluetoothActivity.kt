package com.jubging.jubging.ui.jubging

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityBluetoothBinding
import com.jubging.jubging.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_bluetooth.*
import java.util.*
import kotlin.collections.ArrayList


class BluetoothActivity: AppCompatActivity() {
    private var mBinding: ActivityBluetoothBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val PERMISSIONS_S_ABOVE = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val REQUEST_ALL_PERMISSION = 2

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private lateinit var broadcastReceiver: BroadcastReceiver

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            var str = msg.data.getString("data")
            if(str?.get(0)?.equals('-') == true){
                str = "0.00"
            }
            Log.d("test", str.toString())
            Log.d("TEST", this.looper.toString())
            weight.setText(str + "KG")
        }
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("TEST", "블투 켜기 요청")
            if (it.resultCode == RESULT_OK) {
                Log.d("TEST", "활성화")
                Toast.makeText(applicationContext, "활성화", Toast.LENGTH_SHORT).show()
            } else if (it.resultCode == RESULT_CANCELED) {
                Log.d("TEST", "비활성화")
                Toast.makeText(applicationContext, "비활성화", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //블루투스 어댑터 가져오기
        //블루투스 지원하지 않는 기기인 경우
        if (bluetoothAdapter == null) {
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("TEST", "안드로이드 12")
                if (!hasPermissions(this, PERMISSIONS_S_ABOVE)) {
                    Log.d("TEST", "권한 요청")
                    requestPermissions(PERMISSIONS_S_ABOVE, REQUEST_ALL_PERMISSION)
                }
                else {
                    jubjubiConnect();
                }
            } else {
                if (!hasPermissions(this, PERMISSIONS)) {
                    Log.d("TEST", "권한요청")
                    requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
                }
                else {
                    jubjubiConnect();
                }
            }
            mBinding = ActivityBluetoothBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        //확인 버튼 누르면 다음 액티비티 뜨도록

        binding.bluetoothConfirmTv.setOnClickListener {
            val intent = Intent(this, FinishJubgingActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun jubjubiConnect(){
        val pairedDevices: Set<BluetoothDevice> =
            bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
        var jubjubiDevice: BluetoothDevice? = null

        if (!pairedDevices.isEmpty()) {
            pairedDevices.forEach { device ->
                if (device.name == "jubjubi") {
                    jubjubiDevice = device
                    //연결
                    connectDevice(jubjubiDevice!!.address)
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
                                connectDevice(jubjubiDevice!!.address)
                            }
                        }
                    }
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))

        if (jubjubiDevice == null) {
            findDevice()
        }
    }

    //줍줍이 연결 요청
    @SuppressLint("MissingPermission")
    private fun connectDevice(deviceAddress: String) {
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
                val thread = ConnectThread(uuid, device, handler)
                thread.run()
                Log.d("connectDevice", "${device.name}과 연결되었습니다.")
            } catch (e: Exception) { // 연결에 실패할 경우 호출됨
                Log.d("connectDevice", "기기의 전원이 꺼져 있습니다. 기기를 확인해주세요.")
                return
            }
        }
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
            Log.d("TEST", "블루투스 검색 시작")
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
                    jubjubiConnect()
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
}