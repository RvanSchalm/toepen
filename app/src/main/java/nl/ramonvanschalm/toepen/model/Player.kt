package nl.ramonvanschalm.toepen.model

import java.util.UUID

data class Player(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val score: Int = 0,
    val kleineSpelersKlassement: Int = 0
)
