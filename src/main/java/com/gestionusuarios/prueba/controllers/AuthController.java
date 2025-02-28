package com.gestionusuarios.prueba.controllers;

import com.gestionusuarios.prueba.dao.UsuarioDao;
import com.gestionusuarios.prueba.models.Usuario;
import com.gestionusuarios.prueba.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login (@RequestBody Usuario usuario) {
        Usuario usuarioLogged = usuarioDao.getUsuarioLogged(usuario);
        if(usuarioLogged != null) {
            String token = jwtUtil.create(String.valueOf(usuarioLogged.getId()) , usuarioLogged.getEmail());
            return token;
        } else  {
            return "error";
        }
    }

}

