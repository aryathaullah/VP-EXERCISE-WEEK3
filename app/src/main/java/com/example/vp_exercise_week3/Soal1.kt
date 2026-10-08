
package com.example.vp_exercise_week3

import android.os.SystemClock
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random


enum class GamePage{
    BEGIN, WAITING, START, RESULT, FAILED, FINAL
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1(modifier: Modifier = Modifier){

    var gamePage by remember { mutableStateOf(GamePage.BEGIN) }

    var trial by remember { mutableIntStateOf(1) }
    var trial1 by remember { mutableIntStateOf(-2) }
    var trial2 by remember { mutableIntStateOf(-2) }
    var trial3 by remember { mutableIntStateOf(-2) }

    var startTime by remember { mutableLongStateOf(0L) }
    var reactionTime by remember { mutableIntStateOf(0) }

    val hasil = listOf(trial1, trial2, trial3)
    val valid = hasil.filter { it >= 0 }

    val average = if(valid.isNotEmpty()){
        valid.average().toInt()
    }else{
        0
    }


    if(gamePage == GamePage.BEGIN){

        halamanAwal(
            onClick = {
                gamePage = GamePage.WAITING
            }
        )
    }


    if(gamePage == GamePage.WAITING){

        halamanWaiting(
            onTimeout = {

                when(trial){
                    1 -> trial1 = -1
                    2 -> trial2 = -1
                    3 -> trial3 = -1
                }

                gamePage = GamePage.FAILED
            },

            onGame = {
                startTime = SystemClock.elapsedRealtime()
                gamePage = GamePage.START
            }
        )
    }


    if(gamePage == GamePage.START){

        halamanProgress(
            onClick = {

                reactionTime = (
                        SystemClock.elapsedRealtime() - startTime
                        ).toInt()

                when(trial){
                    1 -> trial1 = reactionTime
                    2 -> trial2 = reactionTime
                    3 -> trial3 = reactionTime
                }

                gamePage = GamePage.RESULT
            }
        )
    }


    if(gamePage == GamePage.RESULT){

        halamanResult(
            trial = trial,
            reactionTime = reactionTime,
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,

            onClick = {
                if(trial < 3){
                    trial++
                    gamePage = GamePage.WAITING
                }else{
                    gamePage = GamePage.FINAL
                }
            }
        )
    }


    if(gamePage == GamePage.FAILED){

        halamanFailed(
            trial = trial,
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,

            onClick = {
                if(trial < 3){
                    trial++
                    gamePage = GamePage.WAITING
                }else{
                    gamePage = GamePage.FINAL
                }
            }
        )
    }


    if(gamePage == GamePage.FINAL){

        halamanFinal(
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,
            average = average,
            validCount = valid.size,

            onClick = {
                trial = 1
                trial1 = -2
                trial2 = -2
                trial3 = -2
                reactionTime = 0
                gamePage = GamePage.BEGIN
            }
        )
    }
}



@Composable
fun halamanAwal(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF68C7D5))
            .clickable { onClick() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Reaction",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.flash_on_icon),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "Test",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
            text = "Click to Start",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



@Composable
fun halamanWaiting(
    onTimeout: () -> Unit,
    onGame: () -> Unit,
    modifier: Modifier = Modifier
){

    LaunchedEffect(Unit){

        val randomDelay = Random.nextLong(500, 4501)
        delay(randomDelay)

        onGame()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .clickable { onTimeout() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "GET READY!",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.baseline_report_problem_24),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "Wait for Green Light...",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
            text = "Dont Click Yet!!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



@Composable
fun halamanProgress(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .clickable { onClick() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "GO!",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.baseline_directions_run_24),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "CLICK NOW!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
            text = "TAP AS FAST AS YOU CAN!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



@Composable
fun halamanResult(
    trial: Int,
    reactionTime: Int,
    trial1: Int,
    trial2: Int,
    trial3: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .clickable { onClick() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Trial $trial Complete!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, CircleShape),

            contentAlignment = Alignment.Center
        ){

            Image(
                painter = painterResource(id = R.drawable.outline_check_24),
                contentDescription = "Trial Complete",
                modifier = Modifier.size(75.dp),
                colorFilter = ColorFilter.tint(Color(0xFF4CAF50))
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Time: ${reactionTime}ms",
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if(trial < 3){
                "Continue to Trial ${trial + 1}"
            }else{
                "Click to See Final Result"
            },
            fontSize = 15.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(35.dp))

        halamanTrialBox(
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,
            final = false,
            average = 0
        )
    }
}



@Composable
fun halamanFailed(
    trial: Int,
    trial1: Int,
    trial2: Int,
    trial3: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF44336))
            .clickable { onClick() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "FAIL!",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(25.dp))

        Image(
            painter = painterResource(id = R.drawable.baseline_thumb_down_24),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "YOU CLICK TOO EARLY!!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "TRY TO READ THE RULE BRO",
            fontSize = 15.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = if(trial < 3){
                "Continue to Trial ${trial + 1}"
            }else{
                "Click to See Final Result"
            },
            fontSize = 15.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        halamanTrialBox(
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,
            final = false,
            average = 0
        )
    }
}



@Composable
fun halamanTrialBox(
    trial1: Int,
    trial2: Int,
    trial3: Int,
    final: Boolean,
    average: Int
){

    Column(
        modifier = Modifier
            .width(if(final) 190.dp else 170.dp)
            .shadow(6.dp, RoundedCornerShape(10.dp))
            .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))
            .padding(12.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Trial Results",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "1",
                    fontSize = 13.sp,
                    color = if(trial1 == -2) Color.Gray else Color.Green
                )

                Text(
                    text = if(trial1 == -2){
                        "-"
                    }else if(trial1 == -1){
                        "FAIL"
                    }else{
                        "${trial1}ms"
                    },
                    fontSize = 10.sp,
                    maxLines = 1,
                    softWrap = false,
                    color = Color.DarkGray
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "2",
                    fontSize = 13.sp,
                    color = if(trial2 == -2) Color.Gray else Color.Green
                )

                Text(
                    text = if(trial2 == -2){
                        "-"
                    }else if(trial2 == -1){
                        "FAIL"
                    }else{
                        "${trial2}ms"
                    },
                    fontSize = 10.sp,
                    maxLines = 1,
                    softWrap = false,
                    color = Color.DarkGray
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "3",
                    fontSize = 13.sp,
                    color = if(trial3 == -2) Color.Gray else Color.Green
                )

                Text(
                    text = if(trial3 == -2){
                        "-"
                    }else if(trial3 == -1){
                        "FAIL"
                    }else{
                        "${trial3}ms"
                    },
                    fontSize = 10.sp,
                    maxLines = 1,
                    softWrap = false,
                    color = Color.DarkGray
                )
            }
        }

        if(final){

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Average Score",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Text(
                text = if(average >= 0){
                    "${average}ms"
                }else{
                    "N/A"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )
        }
    }
}



@Composable
fun halamanFinal(
    trial1: Int,
    trial2: Int,
    trial3: Int,
    average: Int,
    validCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    val category = when{
        validCount == 0 -> "NO VALID TRIALS"
        average < 180 -> "DANG YOU ARE SO FAST BRO!"
        average < 280 -> "YOUR REFLEX IS GOOD"
        average < 450 -> "MEH LIKE OTHER PERSON"
        else -> "YOU LIKE A SNAIL BRO"
    }

    val gambar = when{
        validCount == 0 -> R.drawable.huuuuu
        average < 180 -> R.drawable.hormat
        average < 280 -> R.drawable.good
        average < 450 -> R.drawable.standard
        else -> R.drawable.huuuuu
    }

    val warna = when{
        validCount == 0 -> Color(0xFFFF5722)
        average < 180 -> Color(0xFF00D978)
        average < 280 -> Color(0xFF2196F3)
        average < 450 -> Color(0xFFFF9800)
        else -> Color(0xFFFF5722)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(warna)
            .clickable { onClick() },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = category,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 29.sp,
            modifier = Modifier.padding(horizontal = 35.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Image(
            painter = painterResource(id = gambar),
            contentDescription = null,
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = if(validCount > 0){
                "Average: ${average}ms"
            }else{
                "Average: N/A"
            },
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Click to Start New Test",
            fontSize = 15.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        halamanTrialBox(
            trial1 = trial1,
            trial2 = trial2,
            trial3 = trial3,
            final = true,
            average = if(validCount > 0) average else -1
        )
    }
}
