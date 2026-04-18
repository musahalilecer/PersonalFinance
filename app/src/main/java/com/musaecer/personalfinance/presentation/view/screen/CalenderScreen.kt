package com.musaecer.personalfinance.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.musaecer.personalfinance.presentation.state.CalenderState
import com.musaecer.personalfinance.presentation.state.Date
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalenderScreen() {
    val currentMonth = remember { YearMonth.now() }
    val state = remember(currentMonth) { PerspectiveCalendar(currentMonth) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD)) // Çok hafif kırık beyaz
            .padding(20.dp)
    ) {
        // Üst Başlık ve Navigasyon Alanı
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = state.yearMonth.year.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Text(
                    text = state.yearMonth.month.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault()),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2D3436)
                )
            }

            // Buraya ileri/geri butonları eklenebilir
        }

        // Gün İsimleri Paneli
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF1F2F6))
                .padding(vertical = 8.dp)
        ) {
            listOf("Paz", "Pzt", "Sal", "Çar", "Per", "Cum", "Cmt").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF636E72)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Günler Izgarası
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            userScrollEnabled = false // Takvim sabit kalsın
        ) {
            items(state.dates) { calendarDate ->
                StyledCalendarDayItem(calendarDate)
            }
        }
    }
}

@Composable
fun StyledCalendarDayItem(calendarDate: Date) {
    val isSelectable = calendarDate.date != null

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    calendarDate.isToday -> Color(0xFF0984E3).copy(alpha = 0.1f)
                    else -> Color.Transparent
                }
            )
            .clickable(enabled = isSelectable) { /* Seçim aksiyonu */ },
        contentAlignment = Alignment.Center
    ) {
        if (calendarDate.date != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = calendarDate.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (calendarDate.isToday) FontWeight.Bold else FontWeight.Medium,
                    color = if (calendarDate.isToday) Color(0xFF0984E3) else Color(0xFF2D3436)
                )

                // Bugünün altına küçük bir nokta koyalım
                if (calendarDate.isToday) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0984E3))
                    )
                }
            }
        }
    }
}

fun getDaysInMonth(yearMonth: YearMonth): List<LocalDate?> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Pazar=0 yapmak için

    val days = mutableListOf<LocalDate?>()

    // Ayın ilk gününden önceki boşluklar (önceki ayın son günleri yerine boş bırakıyoruz)
    for (i in 0 until firstDayOfWeek) {
        days.add(null)
    }

    // Ayın gerçek günleri
    for (i in 1..daysInMonth) {
        days.add(yearMonth.atDay(i))
    }

    return days
}

fun PerspectiveCalendar(yearMonth: YearMonth): CalenderState {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Pazar başlangıçlı ise

    val calendarDates = mutableListOf<Date>()
    val today = LocalDate.now()

    // Önceki aydan sarkan boşluklar
    for (i in 0 until firstDayOfWeek) {
        calendarDates.add(Date(null, false))
    }

    // Mevcut ayın günleri
    for (i in 1..daysInMonth) {
        val date = yearMonth.atDay(i)
        calendarDates.add(
            Date(
                date = date,
                isCurrentMonth = true,
                isToday = date == today
            )
        )
    }

    return CalenderState(yearMonth, calendarDates)
}