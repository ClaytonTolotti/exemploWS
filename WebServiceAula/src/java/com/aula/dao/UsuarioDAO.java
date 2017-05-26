package com.aula.dao;

import com.aula.controller.Conection;
import com.aula.model.Retorno;
import com.aula.model.Usuario;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author claytontolotti@gmail.com, danielmatheuskuhn@gmail.com
 */
public class UsuarioDAO {
    
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Connection connection;
    private String sql;
    
    public List getUsuarios() {
        
        Usuario usuario;
        List<Usuario> list = new ArrayList<Usuario>();
        try {
            sql = "SELECT email, name, phone FROM ws.users";
            
            connection = Conection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                usuario = new Usuario(resultSet.getString("email"), 
                        resultSet.getString("name"),
                        resultSet.getString("phone")
                );
                list.add(usuario);
            }
            Conection.closeConnection();
            return list;
        } catch (SQLException sqlEx) {
            sqlEx.getMessage();
        }
        return null;
    }
    
    public String inserirUsuario(Usuario c){
        
        Retorno retorno = new Retorno();
        Gson gson = new Gson();
        
        try {
            sql = "INSERT INTO ws.users VALUES(?,?,?,?)";
            connection = Conection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, c.getEmail());
            preparedStatement.setString(2, c.getName());
            preparedStatement.setString(3, c.getPhone());
            preparedStatement.setString(4, c.getPassword());
            
            preparedStatement.execute();
            Conection.closeConnection();
            
            retorno.setRetorno(true);
            retorno.setTxtRetorno("Usuário cadastrado com sucesso!!!");
            
            return gson.toJson(retorno);
            
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            retorno.setRetorno(false);
            retorno.setTxtRetorno("Ocorreu um erro ao inserir os dados!!!");
            
            return gson.toJson(retorno);
        }
    }
}
