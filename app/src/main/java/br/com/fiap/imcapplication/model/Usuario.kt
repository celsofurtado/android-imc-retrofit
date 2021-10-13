package br.com.fiap.imcapplication.model

import java.time.LocalDate

class Usuario {

    var id: Int = 0
    var email: String = ""
    var password: String = ""
    var roles: String = ""
    var nome: String = ""
    var profissao: String = ""
    var altura: Double = 0.0
    var dataNascimento: String = ""
    var sexo: Char = 'U'
    var fotoUrl: String = ""

    override fun toString(): String {
        return "Usuario(id=$id, email='$email', password='$password', roles='$roles', nome='$nome', profissao='$profissao', altura=$altura, dataNascimento='$dataNascimento', sexo=$sexo, fotoUrl='$fotoUrl')"
    }


}