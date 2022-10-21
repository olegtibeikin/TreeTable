package com.tibi.treetable.screens.tree

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tibi.treetable.domain.table.model.TreeType

class TreeViewModel(
    projectId: Long,
    treeId: Long
) : ViewModel() {
    var treeNumber = mutableStateOf("")
        private set

    var selectedTreeType by mutableStateOf(TreeType.STATION)
        private set

    var diameters by mutableStateOf(listOf(20))
        private set

    fun onSelectTreeType(treeType: TreeType) {
        selectedTreeType = treeType
    }

    fun addDiameter(diameter: Int) {
        diameters = diameters.toMutableList() + diameter
    }
}

