package com.tibi.treetable.di.domain

import com.tibi.treetable.domain.table.usecase.*
import org.koin.dsl.module

val domainTreeModule = module {
    factory {
        GetTreeUseCase(treeRepository = get())
    }
    factory {
        AddTreeUseCase(treeRepository = get())
    }
    factory {
        UpdateTreeUseCase(treeRepository = get())
    }
    factory {
        DeleteTreeUseCase(treeRepository = get())
    }
    factory {
        GetTreeListUseCase(treeRepository = get())
    }
    factory {
        GetSortedTreeTypeListUseCase(treeRepository = get())
    }
}