package com.gestionusuarios.prueba.controllers;

import com.gestionusuarios.prueba.dao.UsuarioDao;
import com.gestionusuarios.prueba.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "usuario")
    public List<Usuario> getUsuarios() {
        return usuarioDao.getUsuarios();
    }


    @RequestMapping(value = "usuario/{id}", method = RequestMethod.GET )
    public Usuario getUsuarioById(@PathVariable int id) {
        return usuarioDao.getUsuarioById(id);
    }


    @RequestMapping(value = "usuario", method = RequestMethod.POST)
    public void createUsuario(@RequestBody Usuario usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String contrasenia = usuario.getPassword();
        String hash = argon2.hash(1,1024,1,contrasenia);
        usuario.setPassword(hash);
        usuarioDao.postUsuario(usuario);
    }

    @RequestMapping(value = "usuario/{id}", method = RequestMethod.PUT)
    public Usuario updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        return usuarioDao.putUsuario(id, usuario);
    }

    @RequestMapping(value = "usuario/{id}", method = RequestMethod.DELETE)
    public void delteUsuario(@PathVariable int id) {
        usuarioDao.deleteUsuario(id);
    }

}
