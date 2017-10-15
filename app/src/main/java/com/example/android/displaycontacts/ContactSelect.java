package com.example.android.displaycontacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ContactSelect extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    // ---------->
    int permissionCheck;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Contact> contact_list;
    Realm realm;

    @Override
    protected void onStart() {
        super.onStart();
    }

    RecyclerViewAdapter.ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        permissionCheck = ContextCompat.checkSelfPermission(ContactSelect.this,
                Manifest.permission.READ_CONTACTS);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        contact_list = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        listener = new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                realm.beginTransaction();
                realm.copyToRealm(contact_list.get(position));
                realm.commitTransaction();
                realm.close();
                Toast.makeText(ContactSelect.this, "Contact Added: " + contact_list.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        };

    /* =====>>>> run time permission */
        if (ContextCompat.checkSelfPermission(ContactSelect.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactSelect.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ContactSelect.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            loadContacts();
                    /*<<<<<<=======*/
        }

    }

    // Run time dialog box and user approval----->>>
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContacts();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(ContactSelect.this, "Contact Permission is needed to Access Contact", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // <<<<=====
    private void loadContacts() {
//        Toast.makeText(ContactSelect.this, "Contact Loading...", Toast.LENGTH_LONG).show();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Uri photo;
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String uri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                if (uri != null) {
                    photo = Uri.parse(uri);
                } else {
                    photo = Uri.parse("android.resource://com.example.android.displaycontacts/drawable/contact");
                }
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));


                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id}, null);
                    if (cursor2 != null) {
                        cursor2.moveToFirst();

                        while (!cursor2.isAfterLast()) {
                            String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Contact contact = new Contact();
                            contact.setName(name);
                            contact.setPhoneNumber(phoneNumber);
                            contact.setImage(photo);
                            contact_list.add(contact);
                            break;
                        }
                        cursor2.close();
                    }
                }
                cursor.moveToNext();

            }
            adapter = new RecyclerViewAdapter(contact_list, listener);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            cursor.close();
        }
    }
}
