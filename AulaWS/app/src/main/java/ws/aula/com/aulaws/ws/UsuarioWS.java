package ws.aula.com.aulaws.ws;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ws.aula.com.aulaws.beans.Usuario;

/**
 * Created by claytontolotti@gmail.com, danielmatheuskuhn@gmail.com on 22/05/2017.
 */

public interface UsuarioWS {

    @GET("usuario/listar")
    Call<List<Usuario>> getUsuarios();

    @Headers("Content-Type: application/json")
    @POST("usuario/inserir")
    Call<String> inserirUsuario(@Body String content);
}
