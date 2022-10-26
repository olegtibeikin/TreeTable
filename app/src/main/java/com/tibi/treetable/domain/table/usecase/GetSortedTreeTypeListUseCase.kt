package com.tibi.treetable.domain.table.usecase

import com.tibi.treetable.domain.table.repository.TreeRepository

class GetSortedTreeTypeListUseCase(
    private val treeRepository: TreeRepository
) {
    fun execute(projectId: Int) = treeRepository.getSortedTreeTypeList(projectId)
}