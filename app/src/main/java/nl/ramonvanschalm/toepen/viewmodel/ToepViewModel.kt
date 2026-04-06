package nl.ramonvanschalm.toepen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import nl.ramonvanschalm.toepen.model.Player

data class ToepUiState(
    val players: List<Player> = emptyList(),
    val currentRound: Int = 1
)

class ToepViewModel : ViewModel() {

    var uiState by mutableStateOf(ToepUiState())
        private set

    val lowestScorePlayerIds: Set<String>
        get() {
            val players = uiState.players
            if (players.isEmpty()) return emptySet()
            val minScore = players.minOf { it.score }
            return players.filter { it.score == minScore }.map { it.id }.toSet()
        }

    fun addPlayer(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return
        uiState = uiState.copy(
            players = uiState.players + Player(name = trimmed)
        )
    }

    fun removePlayer(playerId: String) {
        uiState = uiState.copy(
            players = uiState.players.filter { it.id != playerId }
        )
    }

    fun incrementScore(playerId: String) {
        updatePlayer(playerId) { it.copy(score = (it.score + 1).coerceAtMost(15)) }
    }

    fun decrementScore(playerId: String) {
        updatePlayer(playerId) { it.copy(score = (it.score - 1).coerceAtLeast(0)) }
    }

    fun incrementKleineSpelers(playerId: String) {
        updatePlayer(playerId) {
            it.copy(kleineSpelersKlassement = (it.kleineSpelersKlassement + 1).coerceAtMost(2))
        }
    }

    fun resetKleineSpelers(playerId: String) {
        updatePlayer(playerId) { it.copy(kleineSpelersKlassement = 0) }
    }

    fun nextRound() {
        uiState = uiState.copy(currentRound = uiState.currentRound + 1)
    }

    fun resetGame() {
        uiState = uiState.copy(
            currentRound = 1,
            players = uiState.players.map { it.copy(score = 0, kleineSpelersKlassement = 0) }
        )
    }

    private fun updatePlayer(playerId: String, transform: (Player) -> Player) {
        uiState = uiState.copy(
            players = uiState.players.map { if (it.id == playerId) transform(it) else it }
        )
    }
}
