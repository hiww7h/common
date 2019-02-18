package com.ww7h.ww.common.utils

import android.os.Environment
import java.io.File
import java.nio.file.Files.delete
import java.nio.file.Files.exists
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStreamWriter


class CreateFileUtil {

    companion object {
        /**
         * 生成.json格式文件
         */
        fun createJsonFile(jsonString: String, filePath: String, fileName: String): String {
            // 拼接文件完整路径
            var fullPath = Environment.getExternalStorageDirectory().absolutePath+filePath + File.separator + fileName + ".json"

            // 生成json格式文件
            try {
                // 保证创建一个新文件
                val file = File(fullPath)
                if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                    file.getParentFile().mkdirs()
                }
                if (file.exists()) { // 如果已存在,删除旧文件
                    file.delete()
                }
                file.createNewFile()

                // 将格式化后的字符串写入文件
                val write = OutputStreamWriter(FileOutputStream(file), "UTF-8")
                write.write(jsonString)
                write.flush()
                write.close()
            } catch (e: Exception) {
                fullPath = ""
                e.printStackTrace()
            }

            // 返回是否成功的标记
            return fullPath
        }



    }
}