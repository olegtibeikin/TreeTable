package com.tibi.treetable.domain.table.usecase

import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.repository.TreeRepository

class AddTreeUseCase(
    private val treeRepository: TreeRepository
) {
    suspend fun execute(tree: Tree) = treeRepository.addTree(tree)
}