package com.co.bar.service;

import com.co.bar.dto.BarDto;
import com.co.bar.entity.ArraysEntity;
import com.co.bar.repository.BarRepository;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Log4j2
public class BarService {

    private static final Logger LOG = LoggerFactory.getLogger(BarService.class);

    private static final String DELIMITER = ",";
    @Autowired
    BarRepository repository;
    private int count = 0;

    /**
     * Verifica si un numero es primo
     *
     * @param n
     * @return
     */
    public static List<Integer> numeroPrimo(int n) {
        return IntStream.rangeClosed(2, n)
                .filter(BarService::esPrimo).boxed()
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un numero es primo
     *
     * @param number
     * @return
     */
    public static boolean esPrimo(int number) {
        return !IntStream.rangeClosed(2, number / 2).anyMatch(i -> number % i == 0);
    }

    public ResponseEntity<BarDto> getBar(int iteraciones, int idPilaDataBase) {
        var barDto = new BarDto();

        if (idPilaDataBase > 6 || idPilaDataBase < 1) {
            return getBarDtoResponse(barDto);
        }

        Optional<ArraysEntity> bartender = repository.findById(idPilaDataBase);
        List<Integer> arrayResponse = new ArrayList<>();
        if (!bartender.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Integer> listaNumeros = Arrays.stream(bartender.get().getInput_array().split(DELIMITER))
                    .sorted(Collections.reverseOrder())
                    .map(Integer::parseInt).collect(Collectors.toList());

            List<Integer> arrayA = new ArrayList<>();
            List<Integer> arrayB = new ArrayList<>();
            List<Integer> arrayP = numeroPrimo(100);

            for (int x = 0; x < iteraciones; x++) {
                for (Integer number : listaNumeros) {
                    if (number % arrayP.get(x) == 0) {
                        arrayB.add(number);
                        arrayResponse.add(number);
                    } else {
                        arrayA.add(number);
                    }
                }
                logArray(arrayA, arrayB);
//                barDto.setMessage(logArray);
                listaNumeros = new ArrayList<>(arrayA);
                if (x + 1 < iteraciones) {
                    arrayA.clear();
                    arrayB.clear();
                }
            }
            count = Integer.valueOf(0);
            arrayResponse.addAll(arrayA);
        }

        barDto.setResponseCode(200);


        String respuesta = arrayResponse.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
        barDto.setRespuesta(respuesta);
        return ResponseEntity.status(HttpStatus.OK).body(barDto);

    }

    private void logArray(List<Integer> arrayA, List<Integer> arrayB) {
        count++;
        String arrayAA = arrayA.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
        LOG.info("*******************");
        LOG.info("A{} = {}", count, arrayAA);
        String arrayBB = arrayB.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
        LOG.info("B{} = {}", count, arrayBB);
    }


    /**
     * @param barDto
     * @return
     */
    private ResponseEntity<BarDto> getBarDtoResponse(BarDto barDto) {
        barDto.setResponseCode(400);
        barDto.setMessage("No se encontro el id de la pila indicado.");
        barDto.setTransactionId(System.currentTimeMillis());
        barDto.setRespuesta(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(barDto);
    }

}
