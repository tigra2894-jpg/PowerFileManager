package com.power.filemanager.data

import com.power.filemanager.model.FileItem
import com.power.filemanager.model.FileType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class StorageRepository {

    suspend fun getFilesInDirectory(directoryPath: String): Result<List<FileItem>> = withContext(Dispatchers.IO) {
        runCatching {
            val directory = File(directoryPath)
            if (!directory.exists() || !directory.isDirectory) {
                return@runCatching emptyList()
            }

            val files = directory.listFiles() ?: return@runCatching emptyList()

            files.map { FileItem.fromJavaFile(it) }
                .sortedWith(
                    compareBy(
                        { it.type != FileType.DIRECTORY },
                        { it.name.lowercase() }
                    )
                )
        }
    }
}
