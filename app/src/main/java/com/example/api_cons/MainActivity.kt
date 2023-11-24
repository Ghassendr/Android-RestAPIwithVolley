package com.example.api_cons
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val URL_DATA = "https://jsonplaceholder.typicode.com/users"
    private var listuser: ArrayList<User> = ArrayList()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(listuser)
        recyclerView.adapter = userAdapter

        loadlist()
    }

    private fun loadlist() {
        val stringRequest = StringRequest(Request.Method.GET, URL_DATA,
            Response.Listener<String> { response ->
                try {
                    val array = JSONArray(response)
                    for (i in 0 until array.length()) {
                        val `object` = array.getJSONObject(i)
                        val name = `object`.getString("name")
                        val username = `object`.getString("username")
                        val email = `object`.getString("email")
                        val user = User(name, username, email)
                        listuser.add(user)
                    }
                    userAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@MainActivity,
                    error.message + " Error Loading Users",
                    Toast.LENGTH_LONG
                ).show()
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }
}
