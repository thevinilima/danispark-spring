package com.daniscorp.danispark.repository;

import com.daniscorp.danispark.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Usuario findByNumero(int numero);
    
}
