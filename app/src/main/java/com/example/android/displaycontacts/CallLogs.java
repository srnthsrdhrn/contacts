package com.example.android.displaycontacts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogs extends AppCompatActivity {
    int MY_PERMISSIONS_REQUEST_READ_CALL_LOGS = 3;
    ListView callLogList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        callLogList = (ListView) findViewById(R.id.call_log_list);
        callLogList.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CALL_LOGS);

                // MY_PERMISSIONS_REQUEST_READ_CALL_LOGS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getCallDetails(this);
                    /*<<<<<<=======*/
        }


    }
    private void getCallDetails(Context context) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)){

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CALL_LOGS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
            Cursor cursor = context.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,
                    null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
            int number = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case android.provider.CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case android.provider.CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case android.provider.CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                com.example.android.displaycontacts.CallLog log = new com.example.android.displaycontacts.CallLog();
                log.setPhoneNumber(phNumber);
                log.setTime(callDayTime.toString());
                log.setDuration(callDuration);
                log.setType(dir);
                adapter.add(log.toString());
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCallDetails(CallLogs.this);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(CallLogs.this, "Call Logs Permission is needed to Access Call Logs", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
