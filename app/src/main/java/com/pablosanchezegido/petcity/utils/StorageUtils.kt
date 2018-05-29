package com.pablosanchezegido.petcity.utils

import android.content.Context
import android.os.Environment
import com.pablosanchezegido.petcity.R
import com.pablosanchezegido.petcity.models.PetCityException
import java.io.File
import java.io.IOException

@Throws(PetCityException::class)
fun createImageFile(context: Context): File {
    val DATE_FORMAT = "yyyyMMdd_HHmmss"
    val appName = context.getString(R.string.app_name)
    val IMAGES_DIR = "$appName/$appName Images"

    if (!isExternalStorageWritable()) {
        throw PetCityException(context.getString(R.string.external_storage_not_available))
    }

    val imageFileName = getDateFormatted(DATE_FORMAT)
    val storageDir = File(Environment.getExternalStorageDirectory(), IMAGES_DIR)
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }
    try {
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    } catch (ex: IOException) {
        throw PetCityException(ex.message)
    }
}

// Checks if external storage is available for read and write
fun isExternalStorageWritable(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

// Checks if external storage is available to at least read
fun isExternalStorageReadable(): Boolean =
        Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
