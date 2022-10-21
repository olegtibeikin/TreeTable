package com.tibi.treetable.domain.table.usecase

import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.repository.TreeRepository

class GetTreeListUseCase(
    private val treeRepository: TreeRepository
) {
    suspend fun execute(projectId: Int) = treeRepository.getTreeList(projectId)
}