package tw.edu.pu.csim.hsiien.recipepage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import tw.edu.pu.csim.hsiien.recipepage.ui.theme.RecipepageTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipepageTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecipePage()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RecipePage() {
    val textFieldValue = remember { mutableStateOf("") }

    // ➕ 新增更多食譜項目讓畫面可滑動
    val recipes = listOf(
        "番茄炒蛋" to "https://i.imgur.com/zMZxU8v.jpg",
        "義大利麵" to "https://i.imgur.com/8QO4YDa.jpg",
        "燉牛肉" to "https://i.imgur.com/UqEBkIh.jpg",
        "炒青菜" to "https://i.imgur.com/FJ3Y7GL.jpg",
        "三杯雞" to "https://i.imgur.com/sXTCvVu.jpg",
        "滷肉飯" to "https://i.imgur.com/ONb5XpC.jpg",
        "控肉" to "https://i.imgur.com/VYOd1Ei.jpg",
        "炒蛋" to "https://i.imgur.com/8QO4YDa.jpg"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // 頂部欄
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD7E0E5))
                .padding(vertical = 6.dp, horizontal = 24.dp)
        ) {
            Text(
                "Refrigerator",
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = "https://img.icons8.com/ios-filled/50/shopping-cart.png",
                contentDescription = "Cart Icon",
                modifier = Modifier.size(31.dp)
            )
        }

        // 搜尋欄（調整高度、icon ）
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFD9D9D9))
                .height(42.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e346ee13-bedc-4716-997c-3021b1c60805",
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(24.dp) // ✅ icon 放大
                    .padding(end = 8.dp)
            )

            TextField(
                value = textFieldValue.value,
                onValueChange = { textFieldValue.value = it },
                placeholder = { Text("搜尋食譜", color = Color.Gray, fontSize = 14.sp) }, // ✅ 確保文字顯示
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                textStyle = TextStyle(fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
        }




        // 食譜列表
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(recipes) { recipe ->
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEAEAEA))
                ) {
                    AsyncImage(
                        model = recipe.second,
                        contentDescription = recipe.first,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp) // ⬅️ 稍微縮短圖片
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(recipe.first, fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(" 503", fontSize = 12.sp)
                        }
                    }
                }
            }
        }


        // 底部導航（補回來）
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F0F5))
                .padding(vertical = 10.dp) // ⬅️ 增加 padding，空間感更舒適
        ) {
            val icons = listOf(
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/05ddb832-37fe-47c3-8220-028ff10b3a3b",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/491f290c-7773-45bc-8cb9-26245e94863c",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/af697626-7bdb-47a8-aa3c-f54f66e0eb6a",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d087a83c-ddf3-4ec4-95b4-c114aa377ef5"
            )
            icons.forEach { iconUrl ->
                AsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp) // ⬅️ 微調大小更剛好
                )
            }
        }
    }
}

//目前可以跑 但畫面不夠協調
