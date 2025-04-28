package tw.edu.pu.csim.hsiien.recipepage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import tw.edu.pu.csim.hsiien.recipepage.ui.theme.RecipepageTheme
import java.io.InputStreamReader

data class RecipeItem(
    val title: String,
    val link: String,
    val image: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipepageTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecipePageWithData()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePageWithData() {
    val context = LocalContext.current
    val allRecipes = remember { loadRecipesFromAssets(context) }
    var searchText by remember { mutableStateOf("") }

    val filteredRecipes = allRecipes.filter {
        it.title.contains(searchText, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD7E0E5))
                .padding(vertical = 6.dp, horizontal = 24.dp)
        ) {
            Text("Refrigerator", color = Color.Black, fontSize = 24.sp, modifier = Modifier.weight(1f))
            AsyncImage(
                model = "https://img.icons8.com/ios-filled/50/shopping-cart.png",
                contentDescription = "Cart Icon",
                modifier = Modifier.size(31.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFD9D9D9))
                .height(42.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp),
                tint = Color.Gray
            )

            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("搜尋食譜", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth(),
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredRecipes) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://icook.tw${recipe.link}"))
                            context.startActivity(intent)
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(recipe.image)
                                .crossfade(true)
                                .error(R.drawable.ic_launcher_background)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .build(),
                            contentDescription = recipe.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        )
                        Text(
                            text = recipe.title,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(8.dp),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F0F5))
                .padding(vertical = 10.dp)
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
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

fun loadRecipesFromAssets(context: Context): List<RecipeItem> {
    return try {
        val inputStream = context.assets.open("recipes_all.json")
        val json = InputStreamReader(inputStream).readText()
        Gson().fromJson(json, Array<RecipeItem>::class.java).toList()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}