package com.co.bar.controller;

import com.co.bar.dto.BarDto;
import com.co.bar.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bar")
public class BarController {

    @Autowired
    BarService barService;

    @GetMapping()
    public String health() {
        return "Health";
    }

    /**
     * Una vez completado el
     * número necesario de iteraciones Q, almacene los valores restantes de Ai, al final de Respuesta.
     *
     * @param iteraciones    = Número de itraciones de entrada
     * @param idPilaDataBase = id de entrada a consultar en DB
     * @return retorna
     */
    @GetMapping(value = "/tender")
    public ResponseEntity<BarDto> getBartender(@RequestParam int iteraciones, @RequestParam int idPilaDataBase) {
        return barService.getBar(iteraciones, idPilaDataBase);
    }
}