package com.power.filemanager.model

import java.io.File

enum class FileType {
    FILE, DIRECTORY, SYMLINK, ARCHIVE, UNKNOWN
}

data class FileItem(
    val name: String,
    val path: String,
    val size: Long = 0L,
    val lastModified: Long = 0L,
    val type: FileType = FileType.FILE,
    val isHidden: Boolean = false,
    val isReadable: Boolean = true,
    val isWritable: Boolean = true,
    val permissions: String = "rw-r--r--"
) {
    companion object {
        fun fromJavaFile(file: File): FileItem {
            val isDir = file.isDirectory
            val type = when {
                isDir -> FileType.DIRECTORY
                file.name.endsWith(".zip") || file.name.endsWith(".7z") || file.name.endsWith(".tar") -> FileType.ARCHIVE
                else -> FileType.FILE
            }
            
            return FileItem(
                name = file.name,
                path = file.absolutePath,
                size = if (isDir) 0L else file.length(),
                lastModified = file.lastModified(),
                type = type,
                isHidden = file.isHidden,
                isReadable = file.canRead(),
                isWritable = file.canWrite()
            )
        }
    }
}
