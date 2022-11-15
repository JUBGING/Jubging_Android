package com.jubging.jubging.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.jubging.jubging.databinding.ActivityBluetoothtestBinding
import com.jubging.jubging.ui.base.BaseActivity

class BluetoothTestActivity:  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //블루투스 어댑터 가져오기
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        //블루투스 지원하지 않는 기기인 경우
        if (bluetoothAdapter == null) {
        }

        //권한 확인
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
            //블루투스 활성화되어있지 않은 경우
            if (bluetoothAdapter?.isEnabled == false) {
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
        }

        //권한 확인
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


}