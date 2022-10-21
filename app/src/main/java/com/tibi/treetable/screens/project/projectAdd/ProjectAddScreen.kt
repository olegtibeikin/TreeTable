package com.tibi.treetable.screens.project.projectAdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tibi.treetable.ui.common.CommonTextField
import org.koin.androidx.compose.getViewModel
import com.tibi.treetable.R

@Composable
fun ProjectAddScreen(onOpenTable: (projectId: Long) -> Unit) {
    val viewModel = getViewModel<ProjectAddViewModel>()

    val projectName = viewModel.projectName

    Column {
        CommonTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            valueState = projectName,
            placeholder = stringResource(R.string.table_name_placeholder),
            onAction = KeyboardActions { viewModel.onAddProject(onOpenTable) }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(20),
            onClick = { viewModel.onAddProject(onOpenTable) },

            ) {
            Text(
                text = stringResource(R.string.create_table),
                style = MaterialTheme.typography.h6
            )
        }
    }
}