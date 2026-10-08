
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random


enum class ColorGameState {
    INITIAL, COUNTDOWN, RUNNING, FINISHED
}

enum class ColorMode {
    COLOR, TEXT
}

enum class ColorChoice {
    INK, WORD
}

enum class ColorName(val label: String, val color: Color) {
    RED("RED", Color.Red),
    BLUE("BLUE", Color(0xFF2196F3)),
    GREEN("GREEN", Color.Green),
    YELLOW("YELLOW", Color(0xFFFFB300))
}


fun randomWord(): ColorName {
    return ColorName.values().random()
}

fun randomInk(word: ColorName): ColorName {
    return ColorName.values().filter { it != word }.random()
}

fun randomMode(): ColorMode {
    return ColorMode.values().random()
}

fun checkAnswer(mode: ColorMode, choice: ColorChoice): Boolean {

    val correctChoice = if(mode == ColorMode.COLOR){
        ColorChoice.INK
    }else{
        ColorChoice.WORD
    }

    return choice == correctChoice
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal3(){

    var state by remember { mutableStateOf(ColorGameState.INITIAL) }
    var mode by remember { mutableStateOf(ColorMode.COLOR) }

    var word by remember { mutableStateOf(ColorName.RED) }
    var ink by remember { mutableStateOf(ColorName.BLUE) }

    var leftIsInk by remember { mutableStateOf(true) }

    var score by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }

    var countdown by remember { mutableIntStateOf(3) }
    var timeLeft by remember { mutableIntStateOf(5000) }
    var question by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }

    val buttonColor = Color(0xFFB4C5CE)


    LaunchedEffect(state){

        if(state == ColorGameState.COUNTDOWN){

            countdown = 3
            delay(1000)

            countdown = 2
            delay(1000)

            countdown = 1
            delay(1000)

            countdown = 0
            delay(700)

            word = randomWord()
            ink = randomInk(word)
            mode = randomMode()
            leftIsInk = Random.nextBoolean()

            timeLeft = 5000
            answered = false
            question++

            state = ColorGameState.RUNNING
        }
    }


    LaunchedEffect(state, question){

        if(state == ColorGameState.RUNNING){

            val currentQuestion = question

            while(timeLeft > 0 && !answered &&
                state == ColorGameState.RUNNING &&
                question == currentQuestion){

                delay(50)

                if(!answered && question == currentQuestion &&
                    state == ColorGameState.RUNNING){

                    timeLeft = (timeLeft - 50).coerceAtLeast(0)
                }
            }

            if(timeLeft <= 0 && !answered &&
                question == currentQuestion &&
                state == ColorGameState.RUNNING){

                answered = true
                mistakes++

                if(mistakes >= 3){
                    state = ColorGameState.FINISHED
                }else{

                    word = randomWord()
                    ink = randomInk(word)
                    mode = randomMode()
                    leftIsInk = Random.nextBoolean()

                    timeLeft = 5000
                    answered = false
                    question++
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ){

        if(state == ColorGameState.RUNNING){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 115.dp, start = 35.dp, end = 35.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = "Mode: ${mode.name}",
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "✅ $score  ❌ $mistakes/3",
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(10.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            when(state){

                ColorGameState.INITIAL -> {

                    Text(
                        text = "Welcome\nto\nColor Word Matching",
                        fontSize = 27.sp,
                        lineHeight = 31.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Button(
                        onClick = {
                            score = 0
                            mistakes = 0
                            state = ColorGameState.COUNTDOWN
                        },
                        modifier = Modifier.width(160.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ){
                        Text(
                            text = "Start Game",
                            fontSize = 17.sp,
                            color = Color.DarkGray
                        )
                    }
                }


                ColorGameState.COUNTDOWN -> {

                    Text(
                        text = if(countdown == 0){
                            "Start!"
                        }else{
                            countdown.toString()
                        },
                        fontSize = 25.sp,
                        color = Color.DarkGray
                    )
                }


                ColorGameState.RUNNING -> {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-25).dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Text(
                            text = "${(timeLeft + 999) / 1000} s",
                            fontSize = 23.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = word.label,
                            fontSize = 42.sp,
                            color = ink.color
                        )

                        Spacer(modifier = Modifier.height(100.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp),

                            horizontalArrangement = Arrangement.SpaceBetween
                        ){

                            Button(
                                onClick = {

                                    if(!answered){

                                        answered = true

                                        val choice = if(leftIsInk){
                                            ColorChoice.INK
                                        }else{
                                            ColorChoice.WORD
                                        }

                                        if(checkAnswer(mode, choice)){
                                            score++

                                            if(score > bestScore){
                                                bestScore = score
                                            }
                                        }else{
                                            mistakes++
                                        }

                                        if(mistakes >= 3){
                                            state = ColorGameState.FINISHED
                                        }else{

                                            word = randomWord()
                                            ink = randomInk(word)
                                            mode = randomMode()
                                            leftIsInk = Random.nextBoolean()

                                            timeLeft = 5000
                                            answered = false
                                            question++
                                        }
                                    }
                                },
                                modifier = Modifier.width(85.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor
                                ),
                                shape = RoundedCornerShape(30.dp)
                            ){
                                Text(
                                    text = if(leftIsInk){
                                        ink.label
                                    }else{
                                        word.label
                                    },
                                    fontSize = 11.sp,
                                    color = Color.DarkGray
                                )
                            }


                            Button(
                                onClick = {

                                    if(!answered){

                                        answered = true

                                        val choice = if(leftIsInk){
                                            ColorChoice.WORD
                                        }else{
                                            ColorChoice.INK
                                        }

                                        if(checkAnswer(mode, choice)){
                                            score++

                                            if(score > bestScore){
                                                bestScore = score
                                            }
                                        }else{
                                            mistakes++
                                        }

                                        if(mistakes >= 3){
                                            state = ColorGameState.FINISHED
                                        }else{

                                            word = randomWord()
                                            ink = randomInk(word)
                                            mode = randomMode()
                                            leftIsInk = Random.nextBoolean()

                                            timeLeft = 5000
                                            answered = false
                                            question++
                                        }
                                    }
                                },
                                modifier = Modifier.width(85.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor
                                ),
                                shape = RoundedCornerShape(30.dp)
                            ){
                                Text(
                                    text = if(leftIsInk){
                                        word.label
                                    }else{
                                        ink.label
                                    },
                                    fontSize = 11.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }


                ColorGameState.FINISHED -> {

                    Text(
                        text = "Game Over!",
                        fontSize = 30.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Your Score",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = score.toString(),
                        fontSize = 35.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Best Score: $bestScore",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {
                            score = 0
                            mistakes = 0
                            state = ColorGameState.COUNTDOWN
                        },
                        modifier = Modifier.width(160.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ){
                        Text(
                            text = "Restart Game",
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Button(
                        onClick = {
                            state = ColorGameState.INITIAL
                        },
                        modifier = Modifier.width(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor
                        ),
                        shape = RoundedCornerShape(30.dp)
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
