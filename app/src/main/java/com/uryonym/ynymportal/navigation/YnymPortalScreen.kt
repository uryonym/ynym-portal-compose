package com.uryonym.ynymportal.navigation

import androidx.annotation.StringRes
import com.uryonym.ynymportal.R

sealed class YnymPortalScreen(val route: String, @StringRes val title: Int) {
    data object SignInScreen : YnymPortalScreen(route = "signIn", title = R.string.sign_in)

    data object TaskListScreen : YnymPortalScreen(route = "taskList", title = R.string.task_list)
    data object TaskListListScreen :
        YnymPortalScreen(route = "taskListList", title = R.string.task_list_list)

    data object TaskAddScreen :
        YnymPortalScreen(route = "taskAdd/{taskListId}", title = R.string.add_task) {
        fun createRoute(taskListId: String): String {
            return "taskAdd/$taskListId"
        }
    }

    data object TaskEditScreen :
        YnymPortalScreen(route = "taskEdit/{taskId}", title = R.string.edit_task) {
        fun createRoute(taskId: String): String {
            return "taskEdit/$taskId"
        }
    }

    data object ConfidentialListScreen :
        YnymPortalScreen(route = "confidentialList", title = R.string.confidential_list)

    data object ConfidentialAddScreen :
        YnymPortalScreen(route = "confidentialAdd", title = R.string.add_confidential)

    data object ConfidentialEditScreen : YnymPortalScreen(
        route = "confidentialEdit/{confidentialId}",
        title = R.string.edit_confidential
    ) {
        fun createRoute(confidentialId: String): String {
            return "confidentialEdit/$confidentialId"
        }
    }

    data object CarListScreen :
        YnymPortalScreen(route = "carList", title = R.string.car_list)

    data object CarAddScreen :
        YnymPortalScreen(route = "carAdd", title = R.string.add_car)

    data object CarEditScreen : YnymPortalScreen(
        route = "carEdit/{carId}",
        title = R.string.edit_car
    ) {
        fun createRoute(carId: String): String {
            return "carEdit/$carId"
        }
    }

    data object RefuelingListScreen :
        YnymPortalScreen(route = "refuelingList", title = R.string.refueling_list)

    data object RefuelingAddScreen : YnymPortalScreen(
        route = "refuelingAdd/{carId}",
        title = R.string.add_refueling
    ) {
        fun createRoute(carId: String): String {
            return "refuelingAdd/$carId"
        }
    }

    data object RefuelingEditScreen : YnymPortalScreen(
        route = "refuelingEdit/{refuelingId}",
        title = R.string.edit_refueling
    ) {
        fun createRoute(refuelingId: String): String {
            return "refuelingEdit/$refuelingId"
        }
    }
}
