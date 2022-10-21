package com.tibi.treetable.domain.table.usecase

import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.repository.TreeRepository

class GetTreeUseCase(
    private val treeRepository: TreeRepository
) {
    suspend fun execute(id: Int) = treeRepository.getTree(id)
}