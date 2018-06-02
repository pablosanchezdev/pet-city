package com.pablosanchezegido.petcity.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationManager {

    public interface OnLocationFetchListener {
        void onLocationFetched(@Nullable Location location);
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public static void getLastLocation(Context context, OnLocationFetchListener listener) {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(context);

        client.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null) {
                    listener.onLocationFetched(task.getResult());
                } else {
                    listener.onLocationFetched(null);
                }
            } else {
                listener.onLocationFetched(null);
            }
        });
    }
}
