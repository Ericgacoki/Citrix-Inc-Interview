package com.ericdev.citrixincinterview.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun ProfileHeaderItem(
    name: String,
    status: String,
    email: String,
    username: String,
    createdOn: String,
    showBackArrow: Boolean = false,
    onClickBackArrow: () -> Unit
) {
    AppTheme() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showBackArrow) {
                        IconButton(onClick = { onClickBackArrow() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back Arrow"
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        text = name,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = (.5).dp, horizontal = 6.dp),
                        text = status,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row {
                Box(
                    modifier = Modifier
                        .size(90.dp)
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
                        .height(86.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "@$username",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text = "Created on ${createdOn.take(10)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderItemPreview() {
    ProfileHeaderItem(
        name = "John Doe",
        status = "Active",
        email = "johndoe@gmail.com",
        username = "JohnDoe",
        createdOn = "2023-08"
    ) { }
}
