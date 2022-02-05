package com.daniscorp.danispark.controllers;

import com.daniscorp.danispark.models.Usuario;
import com.daniscorp.danispark.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ModelAndView listaUsuarios() {
        ModelAndView mv = new ModelAndView("usuarios/index");
        Iterable<Usuario> usuarios = ur.findAll();
        mv.addObject("usuarios", usuarios);

        return mv;
    }

    @RequestMapping(value = "/usuarios/cadastrar", method = RequestMethod.GET)
    public String form() {
        return "usuarios/cadastrarForm";
    }

    @RequestMapping(value = "/usuarios/cadastrar", method = RequestMethod.POST)
    public String form(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Preencha todos os campos");
            return "redirect:/usuarios/cadastrar";
        }

        ur.save(usuario);

        return "redirect:/usuarios";
    }

    @RequestMapping(value = "/usuarios/editar/{numero}", method = RequestMethod.GET)
    public ModelAndView editarUsuario(@PathVariable("numero") int numero) {
        Usuario usuario = ur.findByNumero(numero);
        ModelAndView mv = new ModelAndView("/usuarios/editarForm");
        mv.addObject("usuario", usuario);

        return mv;
    }

    @RequestMapping(value = "/usuarios/update/{numero}", method = RequestMethod.POST)
    public String updateUsuario(
            @PathVariable("numero") int numero,
            @Valid Usuario usuario,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Preencha todos os campos");
            return "redirect:/usuarios/editar/" + numero;
        }

        ur.save(usuario);

        return "redirect:/usuarios";
    }

    @RequestMapping(value = "/usuarios/deletar")
    public String deletarUsuario(int numero) {
        Usuario usuario = ur.findByNumero(numero);

        ur.delete(usuario);

        return "redirect:/usuarios";
    }
}
