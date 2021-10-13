package br.com.fiap.imcapplication.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import br.com.fiap.imcapplication.R
import br.com.fiap.imcapplication.RetrofitClient
import br.com.fiap.imcapplication.model.Usuario
import br.com.fiap.imcapplication.repository.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var buttonLogin: Button
    lateinit var buttonNovoUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNovoUsuario = findViewById(R.id.buttonNovoUsuario)

        buttonLogin.setOnClickListener {
            logar()
        }

        buttonNovoUsuario.setOnClickListener {
            val i = Intent(this, CadastroActivity::class.java)
            startActivity(i)
        }

        supportActionBar!!.hide()
    }

    private fun logar() {

        // ---Carregar dados de autenticação do SharedPreferences
        val dados = this.getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val email = dados.getString("email", "")
        val password = dados.getString("password", "")
        val id = dados.getInt("id", 0)

        // Autenticação basic
        var auth = "$email:$password"
        var authByteArray = auth.toByteArray()
        var authBase64 = Base64.encodeToString(authByteArray, Base64.NO_WRAP)

        val remote = RetrofitClient.createService(UsuarioService::class.java)
        val call: Call<Usuario> = remote.getUsuarioById("Basic $authBase64".trim(), id.toString())

        call.enqueue(object : Callback<Usuario> {

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                Log.i("xpto", response.message())
                val usuario: Usuario? = response.body()

                Log.i("xpto", usuario.toString())

                // Criar SharedPreferences
                val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)
                val editor = dados.edit()

                editor.putInt("id", usuario!!.id)
                editor.putString("nome", usuario.nome)
                editor.putString("email", usuario.email)
                editor.putString("password", usuario.password)
                editor.putString("profissao", usuario.profissao)
                editor.putFloat("altura", usuario.altura.toFloat())
                editor.putString("dataNascimento", usuario.dataNascimento)
                editor.apply()

                val i = Intent(applicationContext, DashboardActivity::class.java)
                startActivity(i)
                finish()

            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

                val s = t.message
                Log.i("xpto", "--> ${s.toString()}")

            }

        })

    }
}