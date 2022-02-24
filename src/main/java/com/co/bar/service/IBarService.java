package com.co.bar.service;

import com.co.bar.dto.BarDto;
import org.springframework.http.ResponseEntity;

public interface IBarService {

    ResponseEntity<BarDto> getBar(int iteraciones, int idPilaDataBase);

}
