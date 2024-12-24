package com.example.ucp2.ui.widget


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.R

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.primary)) // Mengatur warna background sesuai primary
            .padding(25.dp)
    ) {
        Column( // Ganti Row dengan Column agar konten bisa ditata secara vertikal
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Menyelaraskan konten secara horizontal
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBackButton) {
                    TextButton(
                        onClick = onBack,
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Kembali",
                            fontSize = 16.sp, // Membesarkan ukuran font tombol kembali
                            fontWeight = FontWeight.Bold, // Membuat tombol kembali lebih tebal
                            color = Color.White // Mengatur warna teks tombol kembali
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp)) // Tambahkan jarak di sini

            Text(
                text = judul,
                fontSize = 22.sp, // Mengurangi ukuran font judul
                fontWeight = FontWeight.Bold,
                color = Color.White, // Mengatur warna teks judul
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
