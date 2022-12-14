package com.tibi.treetable.domain.table.usecase

import com.tibi.treetable.domain.table.repository.TreeRepository

class GetTreeListUseCase(
    private val treeRepository: TreeRepository
) {
    fun execute(projectId: Int) = treeRepository.getTreeList(projectId)
}