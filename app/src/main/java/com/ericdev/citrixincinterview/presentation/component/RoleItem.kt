package com.ericdev.citrixincinterview.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import java.util.*

@Composable
fun RoleItem(
    roleName: String,
    roleDescription: String,
    roleStatus: String,
    createdOn: String
) {
    AppTheme {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = .50F),
                                    ),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 2.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    text = roleName.first().toString().uppercase(Locale.ROOT),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            Column(
                                modifier = Modifier.padding(start = 12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = roleName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "Created on ${createdOn.take(10)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 12.sp,
                                )
                            }
                        }

                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                width = (.5).dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = (.5).dp, horizontal = 6.dp),
                            text = roleStatus,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = roleDescription,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun RoleItemPreview() {
    RoleItem(
        roleName = "Netadmin",
        roleStatus = "Active" ,
        roleDescription = "The choice of which approach to use depends on the programming language, the framework, and your application's design. ",
        createdOn = "Sept 2023"
    )
}
