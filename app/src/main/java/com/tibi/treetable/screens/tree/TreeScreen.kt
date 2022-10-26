package com.tibi.treetable.screens.tree

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlin.reflect.KFunction1

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
            selectedChip = selectedTreeType,
            onChipSelected = viewModel::onSelectTreeType,
            viewModel = viewModel
        )
        TopTrees(
            selectedChip = selectedTreeType,
            onChipSelected = viewModel::onSelectTreeType,
            viewModel = viewModel
        )
        Diameters(viewModel = viewModel)
        AddButton(
            viewModel = viewModel,
            onOpenTable = { onOpenTable(projectId) }
        )
    }
}

@Composable
fun TopTrees(
    selectedChip: TreeType,
    onChipSelected: KFunction1<TreeType, Unit>,
    viewModel: TreeViewModel,
) {
    val topTreeTypeList = viewModel.topTreeTypeList.collectAsState().value
    if (topTreeTypeList.isEmpty()) return

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(topTreeTypeList) {
            TextChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = it.name,
                selected = false
            ) {

            }
        }
    }
}

@Composable
private fun AddButton(
    viewModel: TreeViewModel,
    onOpenTable: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20),
        onClick = {
            viewModel.addTree()
            onOpenTable()
        },

        ) {
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
private fun Diameters(viewModel: TreeViewModel) {

    val diameters = viewModel.diameters

    val selectedDiameterIndex = viewModel.selectedDiameterIndex

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Диаметры:")

        FlowRow {
            diameters.forEachIndexed { index, diameter ->
                Button(onClick = { viewModel.onDiameterSelected(index) }) {
                    Text(diameter.toString())
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            Button(onClick = {
                viewModel.onDiameterSelected(viewModel.addDiameter(20))
            }) {
                Text("+")
            }
        }
        if (selectedDiameterIndex >= 0) {
            NumberPicker()
        }
    }
}

@Composable
private fun TreeChips(
    selectedChip: TreeType,
    onChipSelected: (TreeType) -> Unit,
    viewModel: TreeViewModel
) {
    val treeTypeList = viewModel.treeTypeList.collectAsState().value

    FlowRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
        crossAxisSpacing = 8.dp
    ) {
        treeTypeList.forEach { treeType ->
            TextChip(
                selected = treeType == selectedChip,
                text = treeType.name,
                onClick = { onChipSelected(treeType) }
            )
        }
    }
}

@Composable
fun NumberPicker() {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items((8..200 step 2).toList()) {
            NumberItem(text = it.toString()) {

            }
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NumberItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.size(50.dp),
        onClick = onClick,
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.secondary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h6
            )
        }

    }
}