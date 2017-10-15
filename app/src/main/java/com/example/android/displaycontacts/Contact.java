package com.example.android.displaycontacts;

import android.net.Uri;

import io.realm.RealmObject;

/**
 * Created by Srinath on 15-10-2017.
 */

public class Contact extends RealmObject {
    String Name;

    String Image;

    String PhoneNumber;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Uri getImage() {

        return Uri.parse(Image);
    }

    public void setImage(Uri image) {
        Image = String.valueOf(image);
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setImage(String image) {

        Image = image;
    }
}
