package ws.aula.com.aulaws.activitis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.aula.com.aulaws.R;
import ws.aula.com.aulaws.beans.Usuario;
import ws.aula.com.aulaws.util.Constants;
import ws.aula.com.aulaws.util.UsuarioDeserialerizar;
import ws.aula.com.aulaws.ws.UsuarioWS;

/**
 * Created by claytontolotti@gmail.com, danielmatheuskuhn@gmail.com on 22/05/2017.
 */

public class ListActivity extends FragmentActivity {

    private ListView listUsuarios;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setMessage(this.getResources().getString(R.string.wait));
        progressDialog.setCancelable(true);
        progressDialog.show();

        listUsuarios = (ListView) findViewById(R.id.list_usuarios);

        Gson gson = new GsonBuilder().registerTypeAdapter(Usuario.class, new UsuarioDeserialerizar()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UsuarioWS listar = retrofit.create(UsuarioWS.class);
        Call<List<Usuario>> usuarios = listar.getUsuarios();

        usuarios.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> list = new ArrayList<Usuario>(response.body());
                ArrayAdapter<Usuario> arrayAdapter = new ArrayAdapter<Usuario>(
                        getApplicationContext(), R.layout.list_details_user, list
                );
                listUsuarios.setAdapter(arrayAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.i("REQUEST", t.getMessage());
            }
        });
    }
}
