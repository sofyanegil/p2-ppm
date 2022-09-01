package id.ac.unpas.naviapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.unpas.naviapp.ui.theme.Purple500

@Composable
fun Main() {
    val navController = rememberNavController()

//    val route = remember { mutableStateOf(NavScreen.Dashboard.route) }
    val title = remember { mutableStateOf(MainTab.DASHBOARD.name) }

    val tabs = MainTab.values()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 6.dp,
                backgroundColor = Purple500,
                modifier = Modifier.height(50.dp)
            ) {
                Text(
                    text = title.value,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                elevation = 15.dp
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        selected = tab.name == title.value,
                        onClick = {
                            navController.navigate(MainTab.getTabFromResources(tab.title).name)
                            title.value = MainTab.getTabFromResources(tab.title).name
                        },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = null )
                        },
                        label = {
                            Text(text = stringResource(id = tab.title), color = Color.White)
                        },
                        selectedContentColor = Color.Blue,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            NavHost(navController = navController, startDestination = "dashboard" ){
                composable("dashboard") { Dashboard()}
                composable("profile") { Profile() }
            }
        }
    }
}

enum class MainTab(
    @StringRes val title: Int,
    val icon: ImageVector
) {

    DASHBOARD(R.string.dashboard, Icons.Filled.Home),
    PROFILE(R.string.profile, Icons.Filled.Person);

    companion object {
        fun getTabFromResources(@StringRes resources: Int): MainTab {
            return when (resources) {
                R.string.profile -> PROFILE
                else -> DASHBOARD
            }
        }
    }
}

sealed class NavScreen(val route: String) {
    object Dashboard : NavScreen("Dashboard")
    object Profile : NavScreen("Profile")
}