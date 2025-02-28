package com.gestionusuarios.prueba.dao;

import com.gestionusuarios.prueba.models.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        List<Usuario> result =  entityManager.createQuery(query).getResultList();
        return result;
//        return  entityManager.createQuery(query).getResultList()
    }

    @Override
    public void deleteUsuario(int id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public Usuario getUsuarioById(int id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void postUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario putUsuario(int id, Usuario usuario) {
        Usuario usuarioExistente = entityManager.find(Usuario.class, id);
        BeanUtils.copyProperties(usuario, usuarioExistente, "id");
        return entityManager.merge(usuarioExistente);
    }
}
