package com.ericdev.citrixincinterview.presentation.component

import com.ericdev.citrixincinterview.R
import com.ericdev.citrixincinterview.presentation.screen.destinations.AllUsersDestination
import com.ericdev.citrixincinterview.presentation.screen.destinations.Destination
import com.ericdev.citrixincinterview.presentation.screen.destinations.ProfileDestination

sealed class BottomNavItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val destination: Destination,
    val title: String
) {
    object Profile : BottomNavItem(
        selectedIcon = R.drawable.profile_filled,
        unselectedIcon = R.drawable.profile_outline,
        destination = ProfileDestination,
        title = "Profile"
    )

    object AllUsers : BottomNavItem(
        selectedIcon = R.drawable.list_filled,
        unselectedIcon = R.drawable.list_outline,
        destination = AllUsersDestination,
        title = "Users"
    )
}
