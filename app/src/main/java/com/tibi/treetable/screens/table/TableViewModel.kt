package com.tibi.treetable.screens.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.usecase.GetTreeListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class TableViewModel(
    private val projectId: Long,
    private val getTreeListUseCase: GetTreeListUseCase
) : ViewModel() {
    private val _treeListState = MutableStateFlow<TreeListState>(TreeListState.Loading)
    val treeListState = _treeListState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getTreeListUseCase.execute(projectId.toInt()).distinctUntilChanged()
                    .collect { list ->
                        _treeListState.value = TreeListState.Success(list)
                    }
            } catch (e: Throwable) {
                _treeListState.value = TreeListState.Error(e)
            }
        }
    }

}

sealed interface TreeListState {
    object Loading : TreeListState
    data class Success(val treeList: List<Tree>) : TreeListState
    data class Error(val e: Throwable) : TreeListState
}