package com.example.jetpackcomposeplayground.customdrawer.model

import com.example.jetpackcomposeplayground.R

enum class NavigationItem (
    val title: String,
    val icon: Int

    ){
    Home(
        icon = R.drawable.baseline_home_24,
        title = "Home"
    ),
    Profile(
        icon = R.drawable.baseline_camera_alt_24,
        title = "Photos"
    ),
    Search(
        icon = R.drawable.search,
        title = "Search"
    ),
    Setting(
        icon = R.drawable.baseline_settings_24,
        title = "Settings"
    )
}