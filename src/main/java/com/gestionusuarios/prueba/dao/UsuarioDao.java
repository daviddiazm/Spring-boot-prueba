package com.gestionusuarios.prueba.dao;

import com.gestionusuarios.prueba.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getUsuarios();

    void deleteUsuario(int id);

    Usuario getUsuarioById(int id);

    void postUsuario(Usuario usuario);

    Usuario putUsuario(int id, Usuario usuario);
}
