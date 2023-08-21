package com.hashinology.hashigpt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionInput: EditText = findViewById(R.id.questionInput)
        val askButton: Button = findViewById(R.id.askButton)
        val answerOutput: TextView = findViewById(R.id.answerOutput)

        askButton.setOnClickListener {
            val question = questionInput.text.toString()

            // Send question to ChatGPT API
            val queue = Volley.newRequestQueue(this)
            val url = "https://api.chatgpt.com/v0/answers"

            val request = object : StringRequest(
                Method.POST, url,
                { response ->
                    // Parse response
                    val json = JSONObject(response)
                    val answer = json.getString("answer")

                    // Display answer
                    answerOutput.text = answer
                },
                { error ->
                    // Handle error
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return mutableMapOf("question" to question)
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer ${getApiKey()}"
                    return headers
                }
            }

            queue.add(request)

        }
    }

    private fun getApiKey(): String {
        // Retrieve API key securely
        return ""
    }

}