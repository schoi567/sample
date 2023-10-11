package eu.tutorials.bd1
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adminid = findViewById<EditText>(R.id.adminid)
        val password = findViewById<EditText>(R.id.password)
        val button2 = findViewById<Button>(R.id.button2)

        button2.setOnClickListener {
            val adminIdValue = adminid.text.toString()
            val passwordValue = password.text.toString()

            Log.e("MainActivity", "Admin ID: ${adminid.text} - Password: ${password.text}")
            sendAdminDataToServer(adminIdValue, passwordValue)
        }
    }
    fun sendAdminDataToServer(adminId: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()

        val requestBody = RequestBody.create(
            "application/x-www-form-urlencoded".toMediaType(),
            "adminid=$adminId&password=$password"
        )

        val request = Request.Builder()
            .url("http://10.1.10.197:8080/createadmin")  // Replace with your backend URL.
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                // Handle the error.
                Log.e("MainActivity", "Error: ${response.message}")
            } else {
                // Process the response if needed.
                Log.i("MainActivity", "Response: ${response.body?.string()}")
            }
        }

        }
    }


}
