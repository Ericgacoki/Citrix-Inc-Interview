package com.ericdev.citrixincinterview.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericdev.citrixincinterview.R
import com.ericdev.citrixincinterview.presentation.theme.AppTheme

@Composable
fun UserListItem(
    name: String,
    username: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    AppTheme() {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "Person Icon"
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
                        .height(46.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "@$username",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Badge

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = (.5).dp, horizontal = 6.dp),
                    text = if (isActive) "Active" else "Inactive",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemPreview() {
    UserListItem(name = "John Doe", username = "johnDoe", isActive = true) {

    }
}
