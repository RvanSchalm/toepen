package nl.ramonvanschalm.toepen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.ramonvanschalm.toepen.ui.components.PlayerScoreCard
import nl.ramonvanschalm.toepen.ui.theme.MaroonDark
import nl.ramonvanschalm.toepen.ui.theme.White
import nl.ramonvanschalm.toepen.viewmodel.ToepViewModel

@Composable
fun ToepScreen(
    viewModel: ToepViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Herstart spel") },
            text = { Text("Weet je zeker dat je het spel wilt herstarten?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetGame()
                    showResetDialog = false
                }) {
                    Text("Ja")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Nee")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaroonDark)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(
                text = "TOEPEN",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(
                onClick = { showResetDialog = true },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Herstart\nspel",
                    color = White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
            }
        }

        // Player list
        if (state.players.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Voeg spelers toe via het Spelers tabblad",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.players, key = { it.id }) { player ->
                    PlayerScoreCard(
                        player = player,
                        onIncrementScore = { viewModel.incrementScore(player.id) },
                        onDecrementScore = { viewModel.decrementScore(player.id) },
                        onIncrementKsk = { viewModel.incrementKleineSpelers(player.id) },
                        onResetKsk = { viewModel.resetKleineSpelers(player.id) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
