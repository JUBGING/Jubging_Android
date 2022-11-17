package com.jubging.jubging.ui.jubging

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityBluetoothBinding
import com.jubging.jubging.ui.main.MainActivity


class BluetoothActivity: AppCompatActivity() {
    private var mBinding: ActivityBluetoothBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //블루투스 어댑터 가져오기
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        //블루투스 지원하지 않는 기기인 경우
        if (bluetoothAdapter == null) {
        }

        //권한 확인
        if (bluetoothAdapter?.isEnabled == false) {
            //안드로이드 12 이전
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                val registerForResult = registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val intent = result.data
                        // Handle the Intent
                    }
                }
                //권한 없는 경우
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    registerForResult.launch(enableBtIntent)

                    //권한을 허용해주세요??
                    return
                }
                bluetoothAdapter.enable()
            }
            //안드로이드 12 이후
            else {
                var requestBluetooth =
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == RESULT_OK) {
                            //granted
                            this.finish()
                        } else {
                            //deny
                        }
                    }

                val requestMultiplePermissions =
                    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                        permissions.entries.forEach {
                        }
                    }
                if (bluetoothAdapter?.isEnabled == false) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        requestMultiplePermissions.launch(
                            arrayOf(
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                            )
                        )
                    } else {
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        requestBluetooth.launch(enableBtIntent)
                    }
                }
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        mBinding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //확인 버튼 누르면 다음 액티비티 뜨도록

        binding.bluetoothConfirmTv.setOnClickListener {
            val intent = Intent(this, JubgingDataActivity::class.java)
            startActivity(intent)
        }
//        mBinding.bluetoothConfirmTv.setOnClickListener {
//
//            val intent = Intent(this,JubgingDataActivity::class.java)
//            startActivity(intent)
//            intent.putExtra("index",1)
//            MainActivity().changeFragment(1)
//            finish()
//            supportFragmentManager.beginTransaction().replace(R.id.jubgingFragment,JubgingDataFragment()).commit()
//        val intent = Intent(requireContext(),JubgingDataFragment::class.java)
//        startActivity(intent)
//
//        }


    }
    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address
                    if (deviceName != null) {
                        Log.d("블루투스",deviceName)
                    }
                    else{
                        Log.d("블루투스","null")
                    }
                }
            }
        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        unregisterReceiver(receiver)
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }
}