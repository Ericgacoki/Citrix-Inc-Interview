package com.ericdev.citrixincinterview.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import java.time.format.TextStyle
import java.util.*

@Composable
fun OrganizationItem(organizationName: String) {
    AppTheme {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
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
                        text = organizationName.first().toString().uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    maxLines = 1,
                    text = organizationName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun OrganizationPreview() {
    OrganizationItem(organizationName = "Test Organization")
}
