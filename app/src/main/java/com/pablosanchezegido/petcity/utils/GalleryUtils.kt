package com.pablosanchezegido.petcity.utils

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.pablosanchezegido.petcity.R
import com.pablosanchezegido.petcity.models.PetCityException

class GalleryUtils(private val activity: AppCompatActivity?, private val fragment: Fragment?) {

    companion object {
        @JvmField val GALLERY_REQUEST_CODE = 2
    }

    @Throws(PetCityException::class)
    fun launchGallery() {
        if (activity != null) {
            if (!isExternalStorageReadable()) {
                throw PetCityException(activity.getString(R.string.external_storage_not_available))
            }

            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            activity.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        } else if (fragment != null) {
            if (!isExternalStorageReadable()) {
                throw PetCityException(fragment.getString(R.string.external_storage_not_available))
            }

            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            fragment.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }
    }
}
