package tw.edu.pu.csim.tcyang.hello2025
import UserScoreModel
import UserScoreRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserScoreViewModel : ViewModel() {
    private val userScoreRepository = UserScoreRepository()

    var message by mutableStateOf("訊息")
        private set
    var user by mutableStateOf("")
        private set // 讓狀態只能在 ViewModel 內部被修改

    fun updateUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.updateUser(userScore)
        }
    }
    fun deleteUser(userScore: UserScoreModel) {
        viewModelScope.launch {
            message = userScoreRepository.deleteUser(userScore)
        }
    }
    fun getUserScoreByName(name: String) {
        viewModelScope.launch {
            message = userScoreRepository.getUserScoreByName(name)
        }
    }fun onUserChange(newUser: String) {
        user = newUser
    }
}
