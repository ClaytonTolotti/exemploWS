package ws.aula.com.aulaws.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ws.aula.com.aulaws.beans.Usuario;

/**
 * Created by claytontolotti@gmail.com, danielmatheuskuhn@gmail.com on 22/05/2017.
 */

public class UsuarioDeserialerizar implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement usuarios = json.getAsJsonObject();
        return (new Gson().fromJson(usuarios, Usuario.class));
    }
}