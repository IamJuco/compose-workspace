package com.workspace.core.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Home : RouteModel {
        const val ROUTE = "home"
    }

    @Serializable
    data object MyPage : RouteModel {
        const val ROUTE = "mypage"
    }
}