package com.jubging.jubging.ui.jubging

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.jubging.jubging.databinding.ActivityBluetoothBinding
import kotlinx.android.synthetic.main.activity_bluetooth.*
import java.io.IOException
import kotlin.reflect.KProperty


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //블루투스 어댑터 가져오기
        //블루투스 지원하지 않는 기기인 경우
        if (bluetoothAdapter == null) {
        }

        //권한 확인
//        if (bluetoothAdapter?.isEnabled == false) {
//            //안드로이드 12 이전
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
//                val registerForResult = registerForActivityResult(
//                    ActivityResultContracts.StartActivityForResult()
//                ) { result ->
//                    if (result.resultCode == Activity.RESULT_OK) {
//                        val intent = result.data
//                        // Handle the Intent
//                    }
//                }
//                //권한 없는 경우
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.BLUETOOTH_CONNECT
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//                    registerForResult.launch(enableBtIntent)
//
//                    //권한을 허용해주세요??
//                    return
//                }
//                bluetoothAdapter.enable()
//            }
//            //안드로이드 12 이후
//            else {
//                var requestBluetooth =
//                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                        if (result.resultCode == RESULT_OK) {
//                            //granted
//                            this.finish()
//                        } else {
//                            //deny
//                        }
//                    }
//
//                val requestMultiplePermissions =
//                    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//                        permissions.entries.forEach {
//                        }
//                    }
//                if (bluetoothAdapter?.isEnabled == false) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        requestMultiplePermissions.launch(
//                            arrayOf(
//                                Manifest.permission.BLUETOOTH_SCAN,
//                                Manifest.permission.BLUETOOTH_CONNECT
//                            )
//                        )
//                    } else {
//                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//                        requestBluetooth.launch(enableBtIntent)
//                    }
//                }
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("TEST", "안드로이드 12")
            if (!hasPermissions(this, PERMISSIONS_S_ABOVE)) {
                Log.d("TEST", "권한 요청")
                requestPermissions(PERMISSIONS_S_ABOVE, REQUEST_ALL_PERMISSION)
            }
        } else {
            if (!hasPermissions(this, PERMISSIONS)) {
                Log.d("TEST", "권한요청")
                requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
            }
        }

        if (bluetoothAdapter?.isEnabled == false) {
            Log.d("TEST", "블루투스 꺼져있음")
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activityResultLauncher.launch(intent)
        }

        mBinding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
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