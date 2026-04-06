package nl.ramonvanschalm.toepen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.ramonvanschalm.toepen.ui.components.PlayerListItem
import nl.ramonvanschalm.toepen.ui.theme.Maroon
import nl.ramonvanschalm.toepen.ui.theme.MaroonDark
import nl.ramonvanschalm.toepen.ui.theme.White
import nl.ramonvanschalm.toepen.viewmodel.ToepViewModel

@Composable
fun SpelersScreen(
    viewModel: ToepViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState
    var playerName by remember { mutableStateOf("") }

    val addPlayer = {
        if (playerName.isNotBlank()) {
            viewModel.addPlayer(playerName)
            playerName = ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Spelers",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonDark,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add player input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                placeholder = { Text("Naam van nieuwe speler") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { addPlayer() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Maroon,
                    cursorColor = Maroon
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = addPlayer,
                enabled = playerName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Maroon,
                    contentColor = White
                )
            ) {
                Text("Voeg toe")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Player list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.players, key = { it.id }) { player ->
                PlayerListItem(
                    player = player,
                    onDelete = { viewModel.removePlayer(player.id) }
                )
            }
        }
    }
}
