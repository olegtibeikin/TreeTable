package com.tibi.treetable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tibi.treetable.MainDestinations.PROJECT_ID_KEY
import com.tibi.treetable.MainDestinations.TREE_ID_KEY
import com.tibi.treetable.screens.project.projectAdd.ProjectAddScreen
import com.tibi.treetable.screens.project.projectList.ProjectListScreen
import com.tibi.treetable.screens.table.TableScreen
import com.tibi.treetable.screens.tree.TreeScreen

object MainDestinations {
    const val PROJECT_LIST_ROUTE = "projectList"
    const val PROJECT_ADD_ROUTE = "projectAdd"
    const val PROJECT_ID_KEY = "projectId"
    const val TREE_ID_KEY = "treeId"
    const val TABLE_ROUTE = "table"
    const val TREE_ROUTE = "tree"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.PROJECT_LIST_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.PROJECT_LIST_ROUTE) {
            ProjectListScreen(
                onAddProject = actions.onAddProject,
                onOpenTable = actions.onOpenTable
            )
        }
        composable(MainDestinations.PROJECT_ADD_ROUTE) {
            ProjectAddScreen(
                onOpenTable = actions.onOpenTable
            )
        }
        composable(
            route = "${MainDestinations.TABLE_ROUTE}/{${PROJECT_ID_KEY}}",
            arguments = listOf(
                navArgument(PROJECT_ID_KEY) { type = NavType.LongType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val projectId = arguments.getLong(PROJECT_ID_KEY)
            TableScreen(
                projectId = projectId,
                onOpenTree = actions.onOpenTree
            )
        }
        composable(
            route = "${MainDestinations.TREE_ROUTE}/{${PROJECT_ID_KEY}}/{$TREE_ID_KEY}",
            arguments = listOf(
                navArgument(PROJECT_ID_KEY) { type = NavType.LongType },
                navArgument(TREE_ID_KEY)  { type = NavType.LongType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val projectId = arguments.getLong(PROJECT_ID_KEY)
            val treeId = arguments.getLong(TREE_ID_KEY)
            TreeScreen(
                projectId = projectId,
                onOpenTable = actions.onOpenTable,
                treeId = treeId
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val onAddProject: () -> Unit = {
        navController.navigate(MainDestinations.PROJECT_ADD_ROUTE)
    }
    val onOpenTable: (projectId: Long) -> Unit = { projectId ->
        navController.navigate("${MainDestinations.TABLE_ROUTE}/$projectId") {
            popUpTo(MainDestinations.PROJECT_LIST_ROUTE)
        }
    }
    val onOpenTree: (projectId: Long, treeId: Long) -> Unit = { projectId, treeId ->
        navController.navigate("${MainDestinations.TREE_ROUTE}/$projectId/$treeId")
    }
}