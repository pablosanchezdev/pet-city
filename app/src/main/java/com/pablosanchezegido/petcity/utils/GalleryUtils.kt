package com.pablosanchezegido.petcity.utils

import android.app.Activity
import android.content.Intent
import com.pablosanchezegido.petcity.R
import com.pablosanchezegido.petcity.models.PetCityException

class GalleryUtils(private val activity: Activity) {

    companion object {
        @JvmField val GALLERY_REQUEST_CODE = 2
    }

    @Throws(PetCityException::class)
    fun launchGallery() {
        if (!isExternalStorageReadable()) {
            throw PetCityException(activity.getString(R.string.external_storage_not_available))
        }

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        activity.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }
}
