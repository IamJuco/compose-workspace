package com.workspace.app.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.workspace.app.main.navigation.MainMenu

@Composable
fun BottomNavigationBar(
    currentMenu: MainMenu?,
    onMenuSelected: (MainMenu) -> Unit,
    onShowLoginSnackBar: () -> Unit,
    hasToken: Boolean
) {
    NavigationBar {
        MainMenu.entries.forEach { menu ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(menu.iconResId),
                        contentDescription = menu.contentDescription
                    )
                },
                label = { Text(menu.contentDescription) },
                selected = menu == currentMenu,
                onClick = {
                    if (hasToken) {
                        onMenuSelected(menu)
                    } else {
                        onShowLoginSnackBar()
                    }
                }
            )
        }
    }
}
