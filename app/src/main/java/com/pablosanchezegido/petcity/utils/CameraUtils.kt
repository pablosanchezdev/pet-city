package com.pablosanchezegido.petcity.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.pablosanchezegido.petcity.R
import com.pablosanchezegido.petcity.models.PetCityException
import java.io.File

class CameraUtils(private val activity: Activity) {

    companion object {
        @JvmField val CAMERA_REQUEST_CODE = 1
        @JvmField var CURRENT_PHOTO_URI: Uri? = null
    }

    private var photoPath = ""

    @Throws(PetCityException::class)
    fun launchCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (pictureIntent.resolveActivity(activity.packageManager) != null) {
            val photoFile = createImageFile(activity)
            photoPath = photoFile.absolutePath
            val photoURI = FileProvider.getUriForFile(activity, "com.pablosanchezegido.petcity.fileprovider", photoFile)
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            activity.startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
            galleryAddPic()
            CURRENT_PHOTO_URI = photoURI
        } else {
            throw PetCityException(activity.getString(R.string.camera_not_available))
        }
    }

    private fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(photoPath)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        activity.sendBroadcast(mediaScanIntent)
    }
}