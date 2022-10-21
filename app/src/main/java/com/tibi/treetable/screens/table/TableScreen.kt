package com.tibi.treetable.screens.table

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tibi.treetable.R
import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.ui.common.ProgressCircular
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

const val NEW_TREE = -1L

@Composable
fun TableScreen(
    projectId: Long,
    onOpenTree: (projectId: Long, treeId: Long) -> Unit,
) {
    val viewModel = getViewModel<TableViewModel> { parametersOf(projectId) }
    TableScaffold(
        projectId = projectId,
        onOpenTree = onOpenTree,
        viewModel = viewModel
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TableScaffold(
    projectId: Long,
    onOpenTree: (projectId: Long, treeId: Long) -> Unit,
    viewModel: TableViewModel,
) {
    val treeListState = viewModel.treeListState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.trees)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onOpenTree(projectId, NEW_TREE) }) {
                Icon(
                    Icons.Filled.Add, stringResource(R.string.add_table_button),
                    tint = Color.White
                )
            }
        }
    ) {
        when (treeListState) {
            TreeListState.Loading -> ProgressCircular()
            is TreeListState.Error -> Text(text = treeListState.e.message.toString())
            is TreeListState.Success -> TreeLazyColumn(
                projectId = projectId,
                treeList = treeListState.treeList,
                onOpenTree = onOpenTree
            )
        }
    }
}

@Composable
fun TreeLazyColumn(
    projectId: Long,
    treeList: List<Tree>,
    onOpenTree: (projectId: Long, treeId: Long) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            LazyColumn {
                items(treeList) {
                    TreeRow(
                        projectId = projectId,
                        tree = it,
                        onOpenTree = onOpenTree
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TreeRow(
    projectId: Long,
    tree: Tree,
    onOpenTree: (projectId: Long, treeId: Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 4.dp,
        onClick = {
            onOpenTree(projectId, tree.id.toLong())
        }
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = tree.type.toString())
        }
    }
}
