package tw.edu.pu.csim.tcyang.hello2025

import UserScoreModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.tcyang.hello2025.ui.theme.Hello2025Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hello2025Theme{
                UserScoreScreen()
            }
        }
    }
}
@Composable
fun UserScoreScreen( userScoreViewModel: UserScoreViewModel = viewModel()
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var user by remember { mutableStateOf("") }
        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("姓名") },
            placeholder = { Text("請輸入您的姓名") }
        )
        Text("您輸入的姓名是：$user")
        Spacer(modifier = Modifier.size(10.dp))

        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("子青", 21)
            userScoreViewModel.updateUser(userScore)
        }) {
            Text("新增/異動資料")
        }

        Text(userScoreViewModel.message)
    }
    Button(onClick = {
        val userScore = UserScoreModel("子青", 39)
        userScoreViewModel.deleteUser(userScore)
    }) {
        Text("刪除資料")
    }
    Button(onClick = {
        userScoreViewModel.getUserScoreByName("子青")
    }) {
        Text("查詢分數")
    }

}
