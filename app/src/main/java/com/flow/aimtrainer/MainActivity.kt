package com.flow.aimtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FlowApp() }
    }
}

private val Ink = Color(0xFF10131B)
private val Panel = Color(0xFF1B2130)
private val Accent = Color(0xFF7C5CFC)
private val Muted = Color(0xFFAAB2C5)

@Composable
fun FlowApp() {
    var score by remember { mutableIntStateOf(0) }
    var target by remember { mutableIntStateOf(0) }
    var running by remember { mutableStateOf(false) }
    var targetStyle by remember { mutableStateOf("Cabeça") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Ink
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("FLOW", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                Text("AIM TRAINER  •  TREINO INDEPENDENTE", color = Muted, fontSize = 11.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("PONTOS", score.toString(), Modifier.weight(1f))
                    StatCard("ALVOS", target.toString(), Modifier.weight(1f))
                }

                Card(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Panel),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!running) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Pronto para treinar?", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(8.dp))
                                Text("Toque em iniciar e acerte os alvos.", color = Muted)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(if (targetStyle == "Cabeça") 72.dp else 92.dp)
                                    .background(Accent, CircleShape)
                                    .clickable {
                                        score += 1
                                        target += 1
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("＋", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Text("Formato do alvo (apenas treino)", color = Muted, fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Cabeça", "Ombro").forEach { style ->
                        val selected = targetStyle == style
                        OutlinedButton(
                            onClick = { targetStyle = style },
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(1.dp, if (selected) Accent else Muted),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Text(style)
                        }
                    }
                }

                Button(
                    onClick = {
                        if (running) {
                            running = false
                        } else {
                            score = 0
                            target = 0
                            running = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (running) "PAUSAR TREINO" else "INICIAR TREINO", fontWeight = FontWeight.Bold)
                }

                Text(
                    "FLOW é um app de prática separado. Não se conecta ao Free Fire nem modifica jogos.",
                    color = Muted,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(value, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
    }
}
