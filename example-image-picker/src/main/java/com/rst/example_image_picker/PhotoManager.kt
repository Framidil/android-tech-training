package com.rst.example_image_picker

import android.content.ContentUris
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Photo(
    val uri: Uri,
    val name: String,
    val size: Int,
    val id: Long
)

class PhotoManager(val activity: MainActivity) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val photoList = mutableListOf<Photo>()

    init {
        getAllUrls()
    }

    private fun getAllUrls() {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )

        val query = activity.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                photoList.add(Photo(contentUri, name, size, id))
                if (photoList.size == 33) return
            }
        }
        Log.d("d", "photoList: $photoList")
    }

    suspend fun get(index: Int): Drawable? {
        return withContext(Dispatchers.IO) {
//            delay(Random.nextLong(500, 3000))
            val drawable = getDrawable()
            if (index >= photoList.size) {
                return@withContext null
            }
            val photo = photoList[index]
            getThumbnail(photo)
        }
    }

    private fun getThumbnail(photo: Photo): Drawable? {
        val thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val cs = CancellationSignal()
            try {
                activity.contentResolver.loadThumbnail(photo.uri, Size(100, 100), cs)
            } catch (e: Exception) {
                null
            }
        } else {
            MediaStore.Images.Thumbnails.getThumbnail(
                activity.contentResolver,
                photo.id,
                MediaStore.Images.Thumbnails.MINI_KIND, null
            )
        }
        return thumbnail?.toDrawable(activity.resources)
    }

    private fun getDrawable(): Drawable? {
        return AppCompatResources.getDrawable(activity, R.drawable.success)
    }
}

