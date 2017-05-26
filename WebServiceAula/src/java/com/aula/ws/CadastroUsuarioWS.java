package com.aula.ws;

import com.aula.dao.UsuarioDAO;
import com.aula.model.Usuario;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 * @author claytontolotti@gmail.com, danielmatheuskuhn@gmail.com
 */
@Path("usuario")
public class CadastroUsuarioWS {

    @Context
    private UriInfo context;

    public CadastroUsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listar")
    public String getUsuarios(String email){
        
        Gson gson = new Gson();
        List<Usuario> usuario = new ArrayList<Usuario>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();        
        usuario = usuarioDAO.getUsuarios();
        return gson.toJson(usuario);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("inserir")
    public String inserirUsuario(String content){
        
        Gson gson = new Gson();
        Usuario c = (Usuario) gson.fromJson(content, Usuario.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO();        
        return usuarioDAO.inserirUsuario(c);
    }

    /**
     * PUT method for updating or creating an instance of CadastroUsuarioWS
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}