package com.power.filemanager.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.power.filemanager.model.FileItem
import com.power.filemanager.model.FileType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileListScreen(viewModel: MainViewModel) {
    val files by viewModel.files.collectAsState()
    val currentPath by viewModel.currentPath.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = currentPath, maxLines = 1) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(files) { file ->
                FileListItem(
                    fileItem = file,
                    onFileClick = {
                        if (file.type == FileType.DIRECTORY) {
                            viewModel.loadDirectory(file.path)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FileListItem(
    fileItem: FileItem,
    onFileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onFileClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (fileItem.type == FileType.DIRECTORY) Icons.Default.Folder else Icons.Default.InsertDriveFile,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = fileItem.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = fileItem.formattedSize, style = MaterialTheme.typography.bodySmall)
        }
    }
}
