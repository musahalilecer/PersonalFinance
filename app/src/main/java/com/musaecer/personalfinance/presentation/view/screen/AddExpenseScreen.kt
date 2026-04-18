import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musaecer.personalfinance.presentation.viewmodel.CategoryViewModel
import com.musaecer.personalfinance.presentation.viewmodel.ExpenseViewModel
import com.musaecer.personalfinance.presentation.viewmodel.PaymentMethodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    paymentMethodViewModel: PaymentMethodViewModel = hiltViewModel(),
    navController: NavController
) {
    // Input State'leri
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Index yerine gerçek veritabanı ID'sini tutuyoruz
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedMethodId by remember { mutableStateOf<Int?>(null) }

    // ViewModel'lerden gelen State'ler
    val insertStatus by viewModel.insertStatus.collectAsState()
    val categoryState by categoryViewModel.state

    val paymentState by paymentMethodViewModel.paymentMethodsState
    val insertPaymentState by paymentMethodViewModel.paymentInsertStatus.collectAsState()

    /*
    // Kayıt başarılı olduğunda otomatik geri dönmek için dinleyici
    LaunchedEffect(insertStatus.isInsertSuccess) {
        if (insertStatus.isInsertSuccess!!) {
            navController.popBackStack()
            viewModel.resetInsertStatus() // Tekrar tetiklenmemesi için durumu sıfırla
        }
    }

     */

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Tutar Giriş Alanı ---
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Tutar", style = MaterialTheme.typography.labelLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "₺",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                BasicTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    textStyle = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
            }
        }

        // --- Açıklama Girişi ---
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Açıklama") },
            placeholder = { Text("Örn: Market alışverişi") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Ödeme Yöntemi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            // Ödeme yöntemi seçimi için benzer bir LazyRow eklenebilir
            if(paymentState.paymentMethods.isEmpty()) {
                Text("Ödeme yöntemleri yükleniyor veya boş...", style = MaterialTheme.typography.bodySmall)
            }
            LazyRow {
                items(paymentState.paymentMethods) { paymentMethod ->
                    FilterChip(
                        selected = selectedMethodId == paymentMethod.id, // Ödeme yöntemi seçimi eklenebilir
                        onClick = { selectedMethodId = paymentMethod.id },
                        label = { Text(paymentMethod.name) },
                        leadingIcon = if (selectedCategoryId == paymentMethod.id) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                        } else null
                    )
                }
            }
        }

        // --- Dinamik Kategori Seçimi (Veritabanından Gelen) ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Kategori",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            // Eğer kategoriler yükleniyorsa bir gösterge eklenebilir
            if (categoryState.categories.isEmpty()) {
                Text("Kategoriler yükleniyor veya boş...", style = MaterialTheme.typography.bodySmall)
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // itemsIndexed yerine doğrudan liste elemanlarını kullanıyoruz
                items(categoryState.categories) { categoryItem ->
                    FilterChip(
                        selected = selectedCategoryId == categoryItem.id,
                        onClick = { selectedCategoryId = categoryItem.id },
                        label = { Text(categoryItem.category) },
                        leadingIcon = if (selectedCategoryId == categoryItem.id) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                        } else null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- Kaydet Butonu ---
        Button(
            onClick = {
                val amountDouble = amount.toDoubleOrNull() ?: 0.0
                // ID null değilse kaydet
                selectedCategoryId?.let { catId ->
                    viewModel.insertExpense(
                        amount = amountDouble,
                        description = description,
                        date = "1 Mart 2026", // Tarih seçici eklenebilir
                        categoryId = catId,
                        paymentMethodId = 1 // Varsayılan ödeme yöntemi
                    )
                }
            },
            // Kontrol: Boş alan olmamalı ve bir kategori seçilmiş olmalı
            enabled = amount.isNotBlank() &&
                    description.isNotBlank() &&
                    selectedCategoryId != null &&
                    !insertStatus.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            if (insertStatus.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("İşlemi Kaydet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        // Hata Mesajı Gösterimi
        if (insertStatus.error.isNotEmpty()) {
            Text(
                text = insertStatus.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}