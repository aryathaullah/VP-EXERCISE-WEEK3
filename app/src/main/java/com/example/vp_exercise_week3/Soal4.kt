
package com.example.vp_exercise_week3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


enum class RpsMove(val label: String, val emoji: String) {
    ROCK("Rock", "✊"),
    PAPER("Paper", "✋"),
    SCISSORS("Scissors", "✌️")
}

enum class RpsState {
    INITIAL, PICK, REVEAL, FINISHED
}

enum class RpsResult {
    WIN, LOSE, DRAW
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal4(){

    var state by remember { mutableStateOf(RpsState.INITIAL) }

    var bestOf by rememberSaveable { mutableIntStateOf(5) }
    var round by remember { mutableIntStateOf(1) }

    var yourScore by rememberSaveable { mutableIntStateOf(0) }
    var cpuScore by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }

    var yourMove by remember { mutableStateOf<RpsMove?>(null) }
    var cpuMove by remember { mutableStateOf<RpsMove?>(null) }
    var result by remember { mutableStateOf<RpsResult?>(null) }

    var buttons by remember {
        mutableStateOf(RpsMove.values().toList().shuffled())
    }

    val targetScore = bestOf / 2 + 1
    val buttonColor = Color(0xFFB4C5CE)


    fun startGame(){
        yourScore = 0
        cpuScore = 0
        round = 1

        yourMove = null
        cpuMove = null
        result = null

        buttons = RpsMove.values().toList().shuffled()
        state = RpsState.PICK
    }


    fun play(move: RpsMove){

        if(state != RpsState.PICK){
            return
        }

        yourMove = move
        cpuMove = RpsMove.values().random()

        result = when {
            yourMove == cpuMove -> RpsResult.DRAW

            yourMove == RpsMove.ROCK && cpuMove == RpsMove.SCISSORS -> RpsResult.WIN

            yourMove == RpsMove.PAPER && cpuMove == RpsMove.ROCK -> RpsResult.WIN

            yourMove == RpsMove.SCISSORS && cpuMove == RpsMove.PAPER -> RpsResult.WIN

            else -> RpsResult.LOSE
        }

        if(result == RpsResult.WIN){
            yourScore++
        }else if(result == RpsResult.LOSE){
            cpuScore++
        }

        if(yourScore - cpuScore > bestScore){
            bestScore = yourScore - cpuScore
        }

        state = RpsState.REVEAL
    }


    LaunchedEffect(state, round){

        if(state == RpsState.REVEAL){

            delay(700)

            if(yourScore >= targetScore || cpuScore >= targetScore){
                state = RpsState.FINISHED
            }else{
                yourMove = null
                cpuMove = null
                result = null

                buttons = RpsMove.values().toList().shuffled()

                round++
                state = RpsState.PICK
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 65.dp, start = 20.dp, end = 20.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = "🧑 $yourScore – $cpuScore 🤖",
                fontSize = 15.sp,
                color = Color.DarkGray
            )

            Text(
                text = "Best of $bestOf",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-45).dp)
                .padding(20.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            when(state){

                RpsState.INITIAL -> {

                    Text(
                        text = "Rock · Paper · Scissors",
                        fontSize = 26.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "Best Score: $bestScore",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "Choose Best of",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){

                        for(n in listOf(3, 5, 7)){

                            Button(
                                onClick = {
                                    bestOf = n
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(bestOf == n){
                                        Color(0xFF829FAB)
                                    }else{
                                        buttonColor
                                    }
                                ),
                                shape = RoundedCornerShape(30.dp)
                            ){
                                Text(
                                    text = n.toString(),
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { startGame() },
                        modifier = Modifier.width(180.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(30.dp)
                    ){
                        Text(
                            text = "Start",
                            fontSize = 17.sp,
                            color = Color.DarkGray
                        )
                    }
                }


                RpsState.PICK -> {

                    Text(
                        text = "Round $round",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Pick your move!",
                        fontSize = 22.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "❔ VS ❔",
                        fontSize = 40.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){

                        for(move in buttons){

                            Button(
                                onClick = { play(move) },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = "Choose ${move.label}"
                                    },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor
                                ),
                                shape = RoundedCornerShape(30.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                    horizontal = 4.dp, vertical = 8.dp
                                )
                            ){
                                Text(
                                    text = "${move.emoji} ${move.label}",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }


                RpsState.REVEAL -> {

                    Text(
                        text = "Round $round",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "${yourMove?.emoji} VS ${cpuMove?.emoji}",
                        fontSize = 45.sp,
                        modifier = Modifier.semantics {
                            contentDescription = "${yourMove?.label} versus ${cpuMove?.label}"
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = when(result){
                            RpsResult.WIN -> "You Win!"
                            RpsResult.LOSE -> "You Lose!"
                            RpsResult.DRAW -> "Draw!"
                            else -> ""
                        },
                        fontSize = 25.sp,
                        color = Color.DarkGray
                    )
                }


                RpsState.FINISHED -> {

                    Text(
                        text = if(yourScore > cpuScore){
                            "You Win the Match!"
                        }else{
                            "You Lose the Match!"
                        },
                        fontSize = 25.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "$yourScore - $cpuScore",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Best Score: $bestScore",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ){

                        Button(
                            onClick = { startGame() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor
                            )
                        ){
                            Text(
                                text = "Restart",
                                color = Color.DarkGray
                            )
                        }

                        Button(
                            onClick = {
                                yourScore = 0
                                cpuScore = 0
                                state = RpsState.INITIAL
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor
                            )
                        ){
                            Text(
                                text = "Exit",
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}
