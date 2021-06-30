package com.ridho.skripsi.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ridho.skripsi.R;
import com.ridho.skripsi.model.CustomBluetoothDevice;
import com.ridho.skripsi.utility.Commons;
import com.ridho.skripsi.utility.Constant;
import com.ridho.skripsi.utility.UserNotificationManager;
import com.ridho.skripsi.view.dialog.DeviceBottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ridho.skripsi.utility.Commons.calcBleDistance;
import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DOMS MainActivity";

    private static MainActivity instance;
    private Button cari_data;

    private ConstraintLayout layoutTopOn;
    private ConstraintLayout layoutTopOff;
    private ConstraintLayout layoutBottom;

    private TextView tvBluetoothDescription;
    private TextView tvSaturationValue;
    private TextView tvHeartbeatValue;

    private LinearLayout layoutBleCount;
    private TextView tvBleCount;

    private ImageView ivCenter;

    private ImageView ivBluetoothSwitch1;
    private ImageView ivBluetoothSwitch2;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<CustomBluetoothDevice> mBTDevices = new ArrayList<>();
    private Map<String, CustomBluetoothDevice> deviceMap = new HashMap<>();

    private DeviceBottomSheetDialog deviceBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        Commons.checkPermission(MainActivity.this);

        layoutTopOn = findViewById(R.id.layout_top_on);
        layoutTopOff = findViewById(R.id.layout_top_off);
        layoutBottom = findViewById(R.id.layout_bottom);

        tvBluetoothDescription = findViewById(R.id.tv_bluetooth_description);
        tvSaturationValue = findViewById(R.id.tv_saturation_value);
        tvHeartbeatValue = findViewById(R.id.tv_heartbeat_value);

        layoutBleCount = findViewById(R.id.layout_ble_count);
        layoutBleCount.setOnClickListener(bluetoothCountClickListener);
        tvBleCount = findViewById(R.id.tv_ble_count);

        ivCenter = findViewById(R.id.iv_center);

        ivBluetoothSwitch1 = findViewById(R.id.iv_bluetooth_switch1);
        ivBluetoothSwitch1.setOnClickListener(bluetoothSwitchClickListener);
        ivBluetoothSwitch2 = findViewById(R.id.iv_bluetooth_switch2);
        ivBluetoothSwitch2.setOnClickListener(bluetoothSwitchClickListener);
        
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        cari_data = (Button)  findViewById(R.id.cari_data);
        cari_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivtiy2();
            }
        });

        if(bluetoothAdapter == null){
            showMainMenu(false);
            UserNotificationManager.showGeneralError(this, Constant.ALERT_NO_BLUETOOTH_DEVICE);
        } else if(!bluetoothAdapter.isEnabled()){
            showMainMenu(false);
        } else{
            showMainMenu(true);
        }

        setupBluetoothBroadcast();
    }
    public void openActivtiy2() {
        Intent intent = new Intent(this, cari_data.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(bluetoothBroadcastReceiver);
        unregisterReceiver(bluetoothDiscoveryBrodcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i=0; i<grantResults.length; i++){
            if(grantResults[i] == -1) {
                UserNotificationManager.showErrorPermission(MainActivity.this);
                break;
            }
        }
    }

    public static MainActivity getInstace(){
        return instance;
    }

    private void setupBluetoothBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bluetoothBroadcastReceiver, intentFilter);

        if(bluetoothAdapter.isDiscovering()) bluetoothAdapter.cancelDiscovery();
        bluetoothAdapter.startDiscovery();
        IntentFilter discoverDeviceFilter = new IntentFilter();
        discoverDeviceFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoverDeviceFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(bluetoothDiscoveryBrodcastReceiver, discoverDeviceFilter);
    }

    public void updateBleCount() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvBleCount.setText(String.valueOf(deviceMap.size()));
            }
        });
    }

    View.OnClickListener bluetoothSwitchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
            } else {
                bluetoothAdapter.enable();
            }
        }
    };

    View.OnClickListener bluetoothCountClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: bluetoothCountClickListener");
//            UserNotificationManager.showErrorNotification(getApplicationContext(), getString(R.string.distance_alert_body));
//            UserNotificationManager.showDistanceDialog(MainActivity.this, Constant.ALERT_DISTANCE_WARNING, new String[]{"haha", "hehe", "hihi"});
//            UserNotificationManager.showGeneralError(MainActivity.this, Constant.ALERT_NO_BLUETOOTH_DEVICE);

            deviceBottomSheetDialog = new DeviceBottomSheetDialog(MainActivity.this, deviceMap);
            deviceBottomSheetDialog.show();
        }
    };

    private final BroadcastReceiver bluetoothBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch(state) {
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: bluetooth status: STATE_ON");
                        showMainMenu(true);
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: bluetooth status: STATE_OFF");
                        showMainMenu(false);
                        break;
                }
            } else if(action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)){
                Log.d(TAG, "onReceive: bluetooth status: BluetoothDevice.ACTION_ACL_CONNECTED "+ device.getName());
                setViewBluetoothConnected();

            } else if(action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
                Log.d(TAG, "onReceive: bluetooth status: BluetoothDevice.ACTION_ACL_DISCONNECTED");
                setViewBluetoothOn();

            }
        }
    };

    private final BroadcastReceiver bluetoothDiscoveryBrodcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                int value = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                double distance = Math.floor(calcBleDistance(value)*100)/100;
                deviceMap.put(device.getName(), new CustomBluetoothDevice(device.getName(), device.getAddress(), distance));
                MainActivity.getInstace().updateBleCount();
//                Log.d(TAG, "onReceive: name: " + device.getName() + ", address: " + device.getAddress() + ", distance: " + distance);

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "refreshDeviceList: start discovery");
                deviceMap.clear();
                bluetoothAdapter.startDiscovery();
            }
        }
    };


    private void setViewBluetoothOff(){
        tvBluetoothDescription.setText(getString(R.string.off));
        ivBluetoothSwitch1.setImageResource(R.drawable.ic_switch_off);
        setViewRadarBlue();
    }
    private void setViewBluetoothOn(){
        tvBluetoothDescription.setText(getString(R.string.on));
        ivBluetoothSwitch1.setImageResource(R.drawable.ic_switch_on);
        setViewRadarGreen();
    }
    private void setViewBluetoothConnected(){
        tvBluetoothDescription.setText(getString(R.string.connected));
        ivBluetoothSwitch1.setImageResource(R.drawable.ic_switch_on);
        setViewRadarRed();
    }

    private void setViewRadarGreen(){
        ivCenter.setImageResource(R.drawable.center_green);
    }
    private void setViewRadarBlue(){
        ivCenter.setImageResource(R.drawable.center_blue);
    }
    private void setViewRadarRed(){
        ivCenter.setImageResource(R.drawable.center_red);
    }

    private void showMainMenu(boolean isVisible){
        if(isVisible){
            layoutTopOn.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.VISIBLE);
            layoutTopOff.setVisibility(View.GONE);
            setViewBluetoothOn();
        } else{
            layoutTopOn.setVisibility(View.GONE);
            layoutBottom.setVisibility(View.GONE);
            layoutTopOff.setVisibility(View.VISIBLE);
            setViewBluetoothOff();
        }
    }
}