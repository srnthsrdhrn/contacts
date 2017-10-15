package com.example.android.displaycontacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Contact> contactList;
    FloatingActionButton call_log, add_contact;
    private int MY_PERMISSIONS_REQUEST_MAKE_CALL = 1;

    @Override
    protected void onResume() {
        super.onResume();
        RealmResults<Contact> lists = realm.where(Contact.class).findAll();
        contactList = realm.copyFromRealm(lists);
        adapter = new RecyclerViewAdapter(contactList, new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + contactList.get(position).getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.READ_CONTACTS)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_CONTACTS},
                                    MY_PERMISSIONS_REQUEST_MAKE_CALL);

                            // MY_PERMISSIONS_REQUEST_READ_CALL_LOGS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        startActivity(intent);
                    /*<<<<<<=======*/
                    }
                } else {
                    startActivity(intent);
                }

            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        RealmResults<Contact> lists = realm.where(Contact.class).findAll();
        contactList = realm.copyFromRealm(lists);
        adapter = new RecyclerViewAdapter(contactList, new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + contactList.get(position).getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.READ_CONTACTS)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_CONTACTS},
                                    MY_PERMISSIONS_REQUEST_MAKE_CALL);

                            // MY_PERMISSIONS_REQUEST_READ_CALL_LOGS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        startActivity(intent);
                    /*<<<<<<=======*/
                    }
                } else {
                    startActivity(intent);
                }

            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        call_log = (FloatingActionButton) findViewById(R.id.call_log);
        call_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CallLogs.class));
            }
        });
        add_contact = (FloatingActionButton) findViewById(R.id.contact_add);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactSelect.class));
            }
        });
    }


    //
}
