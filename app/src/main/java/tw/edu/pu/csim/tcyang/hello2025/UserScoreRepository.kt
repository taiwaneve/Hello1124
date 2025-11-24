import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UserScoreRepository {
    val db = Firebase.firestore

    suspend fun updateUser(userScore: UserScoreModel): String {
        return try {
            db.collection("UserScore")
                .document(userScore.user)
                .set(userScore)
                .await()
            "新增/異動資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            "新增/異動資料失敗：${e.message}"
        }
    }

    suspend fun deleteUser(userScore: UserScoreModel): String {
        return try {
            db.collection("UserScore")
                .document(userScore.user)
                .delete()
                .await()
            "刪除資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            "刪除資料失敗：${e.message}"
        }
    }

    suspend fun getUserScoreByName(userName: String): String {
        return try {
            val querySnapshot = db.collection("UserScore")
                .whereEqualTo("user", userName)
                .get().await()
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first()
                val userScore = document.toObject<UserScoreModel>()
                "查詢成功！${userScore?.user} 的分數是 ${userScore?.score}"
            } else {
                "查詢失敗：找不到使用者 $userName 的資料。"
            }
        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }

    suspend fun orderByScore(): String {
        return try {
            var message = ""
            val querySnapshot = db.collection("UserScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(3)
                .get().await()

            querySnapshot.documents.forEach { document ->
                val userScore = document.toObject<UserScoreModel>()
                userScore?.let {
                    message += "使用者 ${it.user} 的分數為 ${it.score} \n"
                }
            }

            if (message.isEmpty()) {
                message = "抱歉，資料庫目前無相關資料"
            } else {
                message = "查詢成功！分數由大到小排序為：\n$message"
            }

            message
        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }
}