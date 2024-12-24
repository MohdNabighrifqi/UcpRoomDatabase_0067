package com.example.ucp2.ui.view


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.R

@Composable
fun HomeTokoView(
    onBarangClick: () -> Unit,
    onSuplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit,
    modifier: Modifier.Companion,
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 48.dp))
                    .background(color = colorResource(R.color.primary))
                    .padding(bottom = 32.dp)
                    .padding(top = 20.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 24.dp)
                ) {
                    Column {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home Icon",
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(Modifier.padding(3.dp))
                        Text(
                            text = "Selamat Datang",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "Di Toko Kita",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )

                    }
                }
                Box(
                    Modifier.align(Alignment.CenterEnd)
                        .padding(24.dp)
                        .padding(top = 12.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.people),
                        contentDescription = "Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
            }
        },
        content = { paddingValues ->
            BodyHomeTokoView(
                onBarangClick = { onBarangClick() },
                onSuplierClick = { onSuplierClick() },
                onAddBrgClick = { onAddBrgClick() },
                onAddSplClick = { onAddSplClick() },
                paddingValues = paddingValues,
            )
        }
    )
}



@Composable
fun BodyHomeTokoView(
    onBarangClick: () -> Unit = { },
    onSuplierClick: () -> Unit = { },
    onAddSplClick: () -> Unit = { },
    paddingValues: PaddingValues,
    onAddBrgClick: () -> Unit,

    ) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            GradientCard(
                title = "Tambah Barang",
                icon = Icons.Filled.Add,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.primary),
                        colorResource(R.color.primary_variant)
                    )
                ),
                onClick = onAddBrgClick
            )
        }
        item {
            GradientCard(
                title = "List Product",
                icon = Icons.Filled.List,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.primary_list_product),
                        colorResource(R.color.primary_list_product_variant)
                    )
                ),
                onClick = onBarangClick
            )
        }
        item {
            GradientCard(
                title = "Tambah Supplier",
                icon = Icons.Filled.Add,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.primary_add_supplier),
                        colorResource(R.color.primary_add_supplier_variant)
                    )
                ),
                onClick = onAddSplClick
            )
        }
        item {
            GradientCard(
                title = "List Supplier",
                icon = Icons.Filled.List,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.primary_list_supplier),
                        colorResource(R.color.primary_list_supplier_variant)
                    )
                ),
                onClick = onSuplierClick
            )
        }
    }
}



@Composable
fun GradientCard(
    title: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                clicked = !clicked
                onClick()
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .height(150.dp),
        shape = MaterialTheme.shapes.large, // Larger rounded corners
        elevation = CardDefaults.elevatedCardElevation(10.dp) // Increased elevation for shadow
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(50.dp), // Larger icon for more impact
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 15.sp, // Adjusted font size for smaller text
                        fontWeight = FontWeight.Normal // Normal weight for smaller text
                    ),
                    textAlign = TextAlign.Center, // Center-align the text
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}
