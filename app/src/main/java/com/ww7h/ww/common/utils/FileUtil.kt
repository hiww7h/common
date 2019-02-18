package com.ww7h.ww.common.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class FileUtil {

    companion object {
        /**
         * 通过Uri获取文件在本地存储**的**真实路径
         */
        fun getRealPathFromURI(contentUri: Uri,contentResolver: ContentResolver): String? {
            val poj = arrayOf(MediaStore.MediaColumns.DATA)
            val cursor: Cursor? = contentResolver.query(contentUri, poj, null, null, null)
            if (cursor!=null&&cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
            }else{
                cursor?.close()
                return contentUri.path
            }

        }
    }

}