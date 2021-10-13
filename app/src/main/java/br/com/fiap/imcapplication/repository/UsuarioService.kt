package br.com.fiap.imcapplication.repository

import br.com.fiap.imcapplication.model.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {

    // ---Listar todos os usuários
    @GET("usuarios")
    fun list(@Header("Authorization") authorization: String) : Call<List<Usuario>>

    // ---Recuperar um usuário pelo id
    @GET("usuarios/{id}")
    fun getUsuarioById(
            @Header("Authorization") authorization: String,
            @Path("id") id: String) : Call<Usuario>

    // ---Criar um novo usuário no back-end
    @POST("usuarios")
    fun criarUsuario(@Body usuario: Usuario) : Call<Usuario>

}