package com.daniscorp.danispark;

import static org.assertj.core.api.Assertions.assertThat;

import com.daniscorp.danispark.models.Usuario;
import com.daniscorp.danispark.repository.UsuarioRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
public class UsuarioTest {

    @Autowired
    private UsuarioRepository ur;

    private static int numero;

    @Test
    @Order(1)
    @Rollback(false)
    public void testCreateUser() {
        Usuario savedUsuario = ur.save(new Usuario("Teste", "12345678902", "031966886688"));

        numero = savedUsuario.getNumero();

        assertThat(savedUsuario.getNumero()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testListUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) ur.findAll();

        assertThat(usuarios).size().isGreaterThan(0);
    }

    @Test
    @Order(3)
    @Rollback(false)
    public void testUpdateUsuario() {
        Usuario usuario = ur.findByNumero(numero);
        usuario.setNome("Teste Update");

        ur.save(usuario);

        Usuario updatedUsuario = ur.findByNumero(numero);

        assertThat(updatedUsuario.getNome()).isEqualTo("Teste Update");
    }

    @Test
    @Order(4)
    @Rollback(false)
    public void testDeleteUsuario() {
        Usuario usuario = ur.findByNumero(numero);

        ur.delete(usuario);

        Usuario deletedUsuario = ur.findByNumero(numero);

        assertThat(deletedUsuario).isNull();
    }
}
