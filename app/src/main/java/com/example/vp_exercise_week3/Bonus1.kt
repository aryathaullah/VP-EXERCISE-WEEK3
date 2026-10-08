
package com.example.vp_exercise_week3

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale


fun rupiah(value: Int): String {

    val formatter = NumberFormat.getNumberInstance(
        Locale.forLanguageTag("id-ID")
    )

    return "Rp ${formatter.format(value)}"
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Bonus1(){

    val orange = Color(0xFFE65D28)
    val brown = Color(0xFF66351F)
    val lightOrange = Color(0xFFFFD3A5)
    val background = Color(0xFFFFFAF7)

    var quantity by remember { mutableIntStateOf(3) }
    var isLarge by remember { mutableStateOf(false) }

    val hargaDasar = 25000

    val hargaTambahan = if(isLarge){
        6000
    }else{
        0
    }

    val hargaSatuan = hargaDasar + hargaTambahan

    val subtotal = hargaSatuan * quantity
    val pajak = subtotal * 10 / 100
    val total = subtotal + pajak

    val context = LocalContext.current


    Scaffold(
        containerColor = background,

        bottomBar = {

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "$quantity Caramel Latte berhasil ditambahkan ke keranjang!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),

                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = orange
                )
            ){
                Text(
                    text = "TAMBAH KE KERANJANG",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = "Kopi Kenangan Senja",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = brown
                )

                Text(
                    text = "☕",
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, lightOrange)
            ){

                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                color = Color(0xFF813A1C),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_coffee_24),
                            contentDescription = "Trial Complete",
                            modifier = Modifier.size(75.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Caramel Latte",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown
                    )

                    Text(
                        text = "Espresso shot, steamed milk & caramel syrup",
                        fontSize = 12.sp,
                        color = brown,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${rupiah(hargaSatuan)} / cup",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = orange
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PILIHAN UKURAN:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = brown
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .border(
                            width = if(!isLarge) 2.dp else 1.dp,
                            color = if(!isLarge) orange else lightOrange,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            isLarge = false
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Regular (+0)",
                        fontSize = 13.sp,
                        color = orange,
                        fontWeight = if(!isLarge){
                            FontWeight.Bold
                        }else{
                            FontWeight.Normal
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .border(
                            width = if(isLarge) 2.dp else 1.dp,
                            color = if(isLarge) orange else lightOrange,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            isLarge = true
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Large (+6k)",
                        fontSize = 13.sp,
                        color = orange,
                        fontWeight = if(isLarge){
                            FontWeight.Bold
                        }else{
                            FontWeight.Normal
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, lightOrange)
            ){

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = "Jumlah Pesanan:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .border(
                                1.dp,
                                Color.LightGray,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if(quantity > 1){
                                    quantity--
                                }
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "-",
                            fontSize = 22.sp,
                            color = brown
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                lightOrange,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                quantity++
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            color = orange,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF5EB)
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, lightOrange)
            ){

                Column(
                    modifier = Modifier.padding(12.dp)
                ){

                    PriceRow(
                        label = "Subtotal:",
                        price = rupiah(subtotal)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    PriceRow(
                        label = "Pajak Resto (10%):",
                        price = rupiah(pajak)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        color = lightOrange
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PriceRow(
                        label = "Total Tagihan:",
                        price = rupiah(total),
                        isTotal = true
                    )
                }
            }
        }
    }
}



@Composable
fun PriceRow(
    label: String,
    price: String,
    isTotal: Boolean = false
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(
            text = label,
            fontSize = if(isTotal) 14.sp else 12.sp,
            fontWeight = if(isTotal){
                FontWeight.Bold
            }else{
                FontWeight.Normal
            },
            color = Color(0xFF66351F)
        )

        Text(
            text = price,
            fontSize = if(isTotal) 14.sp else 12.sp,
            fontWeight = if(isTotal){
                FontWeight.Bold
            }else{
                FontWeight.Normal
            },
            color = if(isTotal){
                Color(0xFFE65D28)
            }else{
                Color(0xFF66351F)
            }
        )
    }
}
