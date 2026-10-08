
package com.example.vp_exercise_week3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.ceil

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2() {

    var coin by remember { mutableIntStateOf(0) }
    var klik by remember { mutableIntStateOf(0) }
    var value by remember { mutableIntStateOf(1) }
    var hargaUpgrade by remember { mutableIntStateOf(10) }

    val nextValue = ceil(value * 1.5).toInt()

    LaunchedEffect(klik) {
        if (klik > 0) {
            delay(150)
            klik = 0
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.soal2_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Your Coins",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = coin.toString(),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )

            Text(
                text = "$value Coin Per Tap",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "TAP THE CAT!!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            Image(
                painter = painterResource(
                    id = if (klik == 0) {
                        R.drawable.soal2_kucing_1
                    } else {
                        R.drawable.soal2_kucing_2
                    }
                ),
                contentDescription = "Tap the cat",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        coin += value
                        klik++
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .background(
                        Color.White,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Give Me Your Coin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Next upgrade: $nextValue coins per tap",
                    fontSize = 15.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        coin -= hargaUpgrade
                        value = nextValue
                        hargaUpgrade *= 2
                    },
                    enabled = coin >= hargaUpgrade,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        disabledContainerColor = Color.Gray
                    )
                ) {

                    Text(
                        text = if (coin >= hargaUpgrade) {
                            "Upgrade for $hargaUpgrade coins"
                        } else {
                            "Find ${hargaUpgrade - coin} more coins"
                        },
                        color = Color.White
                    )
                }
            }
        }
    }
}
