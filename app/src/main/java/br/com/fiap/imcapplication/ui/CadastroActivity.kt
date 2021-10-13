package br.com.fiap.imcapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import br.com.fiap.imcapplication.R
import br.com.fiap.imcapplication.RetrofitClient
import br.com.fiap.imcapplication.model.Usuario
import br.com.fiap.imcapplication.repository.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    lateinit var etNome: EditText
    lateinit var etEmail: EditText
    lateinit var etSenha: EditText
    lateinit var etProfissao: EditText
    lateinit var etAltura: EditText
    lateinit var etDataNascimento: EditText
    lateinit var etCodigo: EditText
    lateinit var buttonAvancar: Button
    lateinit var buttonVoltar: Button
    lateinit var buttonSalvar: Button
    lateinit var rbMasculino: RadioButton
    lateinit var rbFeminino: RadioButton

    var codigo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        supportActionBar!!.hide()

        etNome = findViewById(R.id.etNome)
        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        etProfissao = findViewById(R.id.etProfissao)
        etAltura = findViewById(R.id.etAltura)
        etDataNascimento = findViewById(R.id.etNascimento)
        etCodigo = findViewById(R.id.etCodigo)
        buttonAvancar = findViewById(R.id.buttonAvancar)
        buttonVoltar = findViewById(R.id.buttonVoltar)
        buttonSalvar = findViewById(R.id.buttonSalvar)
        rbFeminino = findViewById(R.id.rbFeminino)
        rbMasculino = findViewById(R.id.rbMasculino)

        buttonSalvar.setOnClickListener {
            criarUsuario()
        }

        buttonAvancar.setOnClickListener {
            codigo++
            exibirUsuario(codigo)
        }

        buttonVoltar.setOnClickListener {
            codigo--
            exibirUsuario(codigo)
        }

    }

    private fun criarUsuario() {
        var usuario = Usuario()

        usuario.email = etEmail.text.toString()
        usuario.password = etSenha.text.toString()
        usuario.nome = etNome.text.toString()
        usuario.profissao = etProfissao.text.toString()
        usuario.altura = etAltura.text.toString().toDouble()
        usuario.dataNascimento = etDataNascimento.text.toString()
        usuario.roles = "ROLE_USER"

        if (rbFeminino.isChecked) {
            usuario.sexo = 'F'
        } else {
            usuario.sexo = 'M'
        }

        val remote = RetrofitClient.createService(UsuarioService::class.java)
        val call: Call<Usuario> = remote.criarUsuario(usuario)

        call.enqueue(object : Callback<Usuario> {

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                val usuario = response.body()
                etNome.setText(usuario!!.nome)
                etEmail.setText(usuario.email)
                etSenha.setText(usuario.password)
                etProfissao.setText(usuario.profissao)
                etAltura.setText(usuario.altura.toString())
                etDataNascimento.setText(usuario.dataNascimento)

                // Criar/ler SharedPreferences
                val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)
                val editor = dados.edit()

                editor.putInt("id", usuario.id)
                editor.putString("nome", usuario.nome)
                editor.putString("email", usuario.email)
                editor.putString("password", usuario.password)
                editor.putString("profissao", usuario.profissao)
                editor.putFloat("altura", usuario.altura.toFloat())
                editor.putString("dataNascimento", usuario.dataNascimento)
                editor.apply()

            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

                val s = t.message
                Log.i("xpto", "--> ${s.toString()}")

            }

        })

    }

    private fun exibirUsuarios(codigo: Int) {

        // Autenticação basic
        var auth = "susanna@terra.com.br:123"
        var authByteArray = auth.toByteArray()
        var authBase64 = Base64.encodeToString(authByteArray, Base64.NO_WRAP)

        val remote = RetrofitClient.createService(UsuarioService::class.java)
        val call: Call<List<Usuario>> = remote.list("Basic $authBase64".trim())

        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {

                if (codigo >= 0 && codigo < response.body()!!.size) {
                    val s = response.body()!!.get(codigo)
                    etNome.setText(s.nome)
                    etEmail.setText(s.email)
                    etSenha.setText(s.password)
                    etProfissao.setText(s.profissao)
                    etAltura.setText(s.altura.toString())
                    etDataNascimento.setText(s.dataNascimento)
                }

            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                val s = t.message
                Log.i("xpto", "--> ${s.toString()}")
            }

        })
    }

    private fun exibirUsuario(codigo: Int) {

        // Autenticação basic
        var auth = "susanna@terra.com.br:123"
        var authByteArray = auth.toByteArray()
        var authBase64 = Base64.encodeToString(authByteArray, Base64.NO_WRAP)

        val remote = RetrofitClient.createService(UsuarioService::class.java)
        val call: Call<Usuario> = remote.getUsuarioById("Basic $authBase64".trim(), "1")

        call.enqueue(object : Callback<Usuario> {

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                val s = response.body()
                etNome.setText(s!!.nome)
                etEmail.setText(s.email)
                etSenha.setText(s.password)
                etProfissao.setText(s.profissao)
                etAltura.setText(s.altura.toString())
                etDataNascimento.setText(s.dataNascimento)

            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

                val s = t.message
                Log.i("xpto", "--> ${s.toString()}")

            }

        })
    }
}