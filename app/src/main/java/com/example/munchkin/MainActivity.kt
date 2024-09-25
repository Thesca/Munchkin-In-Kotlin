package com.example.munchkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Player(
    var name: String,
    var level: Int,
    var equipmentBonus: Int,
    var modifiers: Int
) {
    val totalPower: Int
        get() = level + equipmentBonus + modifiers
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProgress(player: Player, onUpdate: (Player) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = player.name,
            onValueChange = { newName -> onUpdate(player.copy(name = newName)) },
            label = { Text("Nome do Jogador") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text("Level: ${player.level}", fontSize = 20.sp)
        Row {
            Button(onClick = { if (player.level > 1) onUpdate(player.copy(level = player.level - 1)) }) {
                Text("-")
            }
            Button(onClick = { if (player.level < 10) onUpdate(player.copy(level = player.level + 1)) }) {
                Text("+")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text("Poder Total: ${player.totalPower}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Bônus de Equipamento: ${player.equipmentBonus}", fontSize = 20.sp)
        Row {
            Button(onClick = { onUpdate(player.copy(equipmentBonus = player.equipmentBonus - 1)) }) {
                Text("-")
            }
            Button(onClick = { onUpdate(player.copy(equipmentBonus = player.equipmentBonus + 1)) }) {
                Text("+")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Modificadores: ${player.modifiers}", fontSize = 20.sp)
        Row {
            Button(onClick = { onUpdate(player.copy(modifiers = player.modifiers - 1)) }) {
                Text("-")
            }
            Button(onClick = { onUpdate(player.copy(modifiers = player.modifiers + 1)) }) {
                Text("+")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PlayerScreen() {
    var players by remember { mutableStateOf(List(6) { Player("Jogador ${it + 1}", 1, 0, 0) }) }

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(players.size) { index ->
            PlayerProgress(player = players[index]) { updatedPlayer ->
                players = players.toMutableList().apply { set(index, updatedPlayer) }
            }
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        PlayerScreen()
    }
}