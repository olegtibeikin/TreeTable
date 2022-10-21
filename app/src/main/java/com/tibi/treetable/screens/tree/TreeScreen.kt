package com.tibi.treetable.screens.tree

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.tibi.treetable.R
import com.tibi.treetable.domain.table.model.TreeType
import com.tibi.treetable.ui.common.CommonTextField
import com.tibi.treetable.ui.common.TextChip
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TreeScreen(
    projectId: Long,
    treeId: Long,
    onOpenTable: (projectId: Long) -> Unit
) {
    val viewModel = getViewModel<TreeViewModel> { parametersOf(projectId, treeId) }

    val treeNumber = viewModel.treeNumber

    val selectedTreeType = viewModel.selectedTreeType

    Column {
        CommonTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            valueState = treeNumber,
            placeholder = stringResource(R.string.tree_number),
            keyboardType = KeyboardType.Number
        )
        TreeChips(
            viewModel = viewModel,
            selectedChip = selectedTreeType,
            onChipSelected = viewModel::onSelectTreeType
        )
    }
}

@Composable
private fun TreeChips(
    selectedChip: TreeType,
    onChipSelected: (TreeType) -> Unit,
    viewModel: TreeViewModel
) {
    val treeTypes = TreeType.values()

    val diameters = viewModel.diameters

    FlowRow(
        modifier = Modifier.padding(8.dp)
    ) {
        treeTypes.forEach { treeType ->
            TextChip(
                selected = treeType == selectedChip,
                text = treeType.name,
                onClick = { onChipSelected(treeType) }
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Диаметры:")

        FlowRow {
            diameters.forEach {
                Button(onClick = {  }) {
                    Text(it.toString())
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            Button(onClick = { viewModel.addDiameter(50) }) {
                Text("+")
            }
        }
    }
}

@Composable
fun NumberPicker() {
    LazyRow {

    }
}