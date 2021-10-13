package br.com.fiap.imcapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.fiap.imcapplication.R

class DashboardActivity : AppCompatActivity() {

    lateinit var tvNomeDashboard: TextView
    lateinit var tvProfissao: TextView
    lateinit var tvAlturaDashboard: TextView
    lateinit var tvPeso: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvAlturaDashboard = findViewById(R.id.tvAlturaDashboard)
        tvNomeDashboard = findViewById(R.id.tvNomeDashBoard)
        tvProfissao = findViewById(R.id.tvProfissao)
        tvPeso = findViewById(R.id.tvPeso)

        supportActionBar!!.hide()

        // Criar/ler SharedPreferences
        val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)
        tvNomeDashboard.text = dados.getString("nome", "")
        tvProfissao.text = dados.getString("profissao", "")
        tvAlturaDashboard.text = dados.getFloat("altura", 0.0f).toString()
        tvPeso.text = dados.getInt("peso", 0).toString()

    }
}