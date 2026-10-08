
package com.example.vp_exercise_week3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Bonus2(){

    val background = Color(0xFF101527)
    val cardColor = Color(0xFF222B3B)
    val blue = Color(0xFF55B5EF)
    val gray = Color(0xFF99A4B8)

    var spot by remember { mutableStateOf("") }
    var pengalaman by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var kepuasan by remember { mutableStateOf("Luar Biasa") }
    var status by remember { mutableStateOf("Draft Tersimpan") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 35.dp,
                    bottom = 100.dp
                )
        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF263C73),
                                Color(0xFF327EC5)
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(15.dp),

                contentAlignment = Alignment.CenterStart
            ){

                Column{

                    Text(
                        text = "LOG PERJALANAN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB8CEE9)
                    )

                    Text(
                        text = "Tromsø, Norway ❄",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Ekspedisi Aurora Borealis",
                        fontSize = 13.sp,
                        color = Color(0xFFD1E1F2)
                    )
                }
            }


            Spacer(modifier = Modifier.height(15.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ){

                Text(
                    text = "SPOT WISATA FAVORIT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = gray
                )

                OutlinedTextField(
                    value = spot,
                    onValueChange = {
                        spot = it
                        status = "Draft Tersimpan"
                    },
                    placeholder = {
                        Text(
                            text = "Fjellheisen Cable Car",
                            color = Color.LightGray
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),

                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = cardColor,
                        unfocusedContainerColor = cardColor,
                        focusedIndicatorColor = blue,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            }


            Spacer(modifier = Modifier.height(12.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ){

                Text(
                    text = "APA YANG PALING KAMU NIKMATI?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = gray
                )

                OutlinedTextField(
                    value = pengalaman,
                    onValueChange = {
                        pengalaman = it
                        status = "Draft Tersimpan"
                    },
                    placeholder = {
                        Text(
                            text = "Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth(),

                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = cardColor,
                        unfocusedContainerColor = cardColor,
                        focusedIndicatorColor = blue,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            }


            Spacer(modifier = Modifier.height(12.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ){

                Text(
                    text = "CATATAN TAMBAHAN / PERLENGKAPAN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = gray
                )

                OutlinedTextField(
                    value = catatan,
                    onValueChange = {
                        catatan = it
                        status = "Draft Tersimpan"
                    },
                    placeholder = {
                        Text(
                            text = "Ketik perlengkapan ekstra di sini...",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),

                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = cardColor,
                        unfocusedContainerColor = cardColor,
                        focusedIndicatorColor = blue,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "TINGKAT KEPUASAN:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = gray
            )

            Spacer(modifier = Modifier.height(10.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){

                for(pilihan in listOf("Biasa", "Seru", "Luar Biasa")){

                    Box(
                        modifier = Modifier
                            .background(
                                if(kepuasan == pilihan){
                                    blue
                                }else{
                                    background
                                },
                                RoundedCornerShape(30.dp)
                            )
                            .border(
                                1.dp,
                                if(kepuasan == pilihan){
                                    blue
                                }else{
                                    Color.DarkGray
                                },
                                RoundedCornerShape(30.dp)
                            )
                            .clickable {
                                kepuasan = pilihan
                                status = "Draft Tersimpan"
                            }
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ),

                        contentAlignment = Alignment.Center
                    ){

                        Text(
                            text = if(pilihan == "Luar Biasa"){
                                "Luar Biasa ★"
                            }else{
                                pilihan
                            },
                            fontSize = 12.sp,

                            fontWeight = if(kepuasan == pilihan){
                                FontWeight.Bold
                            }else{
                                FontWeight.Normal
                            },

                            color = if(kepuasan == pilihan){
                                background
                            }else{
                                gray
                            }
                        )
                    }
                }
            }

        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 25.dp
                ),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = "Status: ",
                    fontSize = 13.sp,
                    color = gray
                )

                Text(
                    text = status,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = blue
                )
            }


            FloatingActionButton(
                onClick = {

                    if(spot.isBlank()){

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Isi nama spot wisata terlebih dahulu!"
                            )
                        }

                    }else{

                        status = "Berhasil Disimpan"

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Travel Journal $spot berhasil disimpan!"
                            )
                        }

                    }
                },
                containerColor = blue,
                contentColor = background,
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.size(60.dp)
            ){

                Text(
                    text = "+",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 100.dp
                )
        )

    }
}
