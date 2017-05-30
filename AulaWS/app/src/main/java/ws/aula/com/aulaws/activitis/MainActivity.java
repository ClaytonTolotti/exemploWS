package ws.aula.com.aulaws.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ws.aula.com.aulaws.R;
import ws.aula.com.aulaws.beans.Usuario;
import ws.aula.com.aulaws.util.Constants;
import ws.aula.com.aulaws.util.Retorno;
import ws.aula.com.aulaws.ws.UsuarioWS;

/**
 * Created by claytontolotti@gmail.com, danielmatheuskuhn@gmail.com on 22/05/2017.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnInserir;
    private Button btnListar;
    private EditText edtEmail;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInserir = (Button) findViewById(R.id.btn_cadastrar_usuario);
        btnListar = (Button) findViewById(R.id.btn_listar_usuario);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtName = (EditText) findViewById(R.id.edt_nome);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        edtName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edtPassword.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirUsuario();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });
    }

    public boolean inserirUsuario() {
        if (TextUtils.isEmpty(edtEmail.getText()) ||
                TextUtils.isEmpty(edtName.getText()) ||
                TextUtils.isEmpty(edtPhone.getText()) ||
                TextUtils.isEmpty(edtPassword.getText())) {
            Toast.makeText(this.getApplicationContext(), "Pro favor preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {

                Usuario usuario = new Usuario();
                usuario.setEmail(edtEmail.getText().toString());
                usuario.setName(edtName.getText().toString());
                usuario.setPhone(edtPhone.getText().toString());
                usuario.setPassword(edtPassword.getText().toString());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", usuario.getEmail());
                jsonObject.put("name", usuario.getName());
                jsonObject.put("phone", usuario.getPhone());
                jsonObject.put("password", usuario.getPassword());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.WEB_SERVICE)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsuarioWS usuarioWS = retrofit.create(UsuarioWS.class);
                Call<String> chamada = usuarioWS.inserirUsuario(jsonObject.toString());

                chamada.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String strRetorno = response.body();
                        Gson gson = new Gson();
                        Retorno retorno = gson.fromJson(strRetorno, Retorno.class);
                        Toast.makeText(getApplicationContext(), retorno.getTxtRetorno(), Toast.LENGTH_SHORT).show();
                        edtEmail.setText(null);
                        edtName.setText(null);
                        edtPhone.setText(null);
                        edtPassword.setText(null);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.getCause();
                    }
                });

                return true;
            } catch (Exception jsEx) {
                return false;
            }
        }
    }

    public void listarUsuarios() {
        startActivity(new Intent(this, ListActivity.class));
    }
}
