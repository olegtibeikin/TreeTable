package com.tibi.treetable.screens.project.projectList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import com.tibi.treetable.R
import com.tibi.treetable.domain.project.model.Project
import com.tibi.treetable.ui.common.ProgressCircular
import androidx.compose.foundation.lazy.items
import com.tibi.treetable.extensions.formatDate

@Composable
fun ProjectListScreen(
    onAddProject: () -> Unit,
    onOpenTable: (projectId: Long) -> Unit
) {
    val viewModel = getViewModel<ProjectListViewModel>()

    ProjectScaffold(
        onAddProject = onAddProject,
        onOpenTable = onOpenTable,
        viewModel = viewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectScaffold(
    onAddProject: () -> Unit,
    onOpenTable: (projectId: Long) -> Unit,
    viewModel: ProjectListViewModel,
) {
    val projectListState = viewModel.projectListState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tables)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProject) {
                Icon(
                    Icons.Filled.Add, stringResource(R.string.add_table_button),
                    tint = Color.White
                )
            }
        }
    ) {
        when (projectListState) {
            is ProjectListState.Loading -> ProgressCircular()
            is ProjectListState.Error -> Text(text = projectListState.e.message.toString())
            is ProjectListState.Success -> ProjectLazyColumn(
                projectList = projectListState.projectList,
                onOpenTable = onOpenTable,
                viewModel = viewModel
            )
        }

    }
}

@Composable
fun ProjectLazyColumn(
    projectList: List<Project>,
    onOpenTable: (projectId: Long) -> Unit,
    viewModel: ProjectListViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            LazyColumn {
                items(projectList) {
                    ProjectRow(
                        project = it,
                        onOpenTable = onOpenTable,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectRow(
    project: Project,
    onOpenTable: (projectId: Long) -> Unit,
    viewModel: ProjectListViewModel
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 4.dp,
        onClick = {
            viewModel.updateProjectDate(project)
            onOpenTable(project.id.toLong())
        }
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Filled.Folder,
                contentDescription = null,
                tint = MaterialTheme.colors.secondary
            )
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = project.name,
                    fontWeight = FontWeight.Bold
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(project.date.formatDate(), style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

