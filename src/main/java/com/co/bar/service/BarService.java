package com.co.bar.service;

import brave.Tracer;
import com.co.bar.dto.BarDto;
import com.co.bar.entity.ArraysEntity;
import com.co.bar.exception.NotFoundException;
import com.co.bar.repository.BarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BarService {

    @Autowired
    BarRepository repository;
    Tracer tracer;

    public static String solve(int q, List<Integer> values) {
        List<Integer> primes = primeNumbers(q);

        List<Integer> actualValues = new ArrayList<>(values);
        List<Integer> solution = new ArrayList<>();
        List<Integer> tmpValues = new ArrayList<>();

        for (int iQ = 0; iQ < q; iQ++) {
            // clear A from last Qi
            tmpValues.clear();
            for (int j = actualValues.size() - 1; j >= 0; j--) {
                if (actualValues.get(j) % primes.get(iQ) == 0) {
                    //add to solution
                    solution.add(actualValues.get(j));
                } else {
                    //rebuild A
                    tmpValues.add(0, actualValues.get(j));
                }
            }
            actualValues.clear();
            actualValues = new ArrayList<>(tmpValues);

        }
        // Adding rest of numbers from B
        solution.addAll(tmpValues);

        // Converting solution to String comma separated
        return solution.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static List<Integer> primeNumbers(int n) {
        List<Integer> primeNumbers = new ArrayList<>();
        int i = 1;
        while (primeNumbers.size() < n) {
            if (isPrime(++i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    public static boolean isPrime(int number) {
        return !IntStream.rangeClosed(2, number / 2).anyMatch(i -> number % i == 0);
    }

    public ResponseEntity<BarDto> getBar(int iteraciones, int idPilaDataBase) throws NotFoundException {

        var barDto = new BarDto();
        Optional<ArraysEntity> bartender = repository.findById(idPilaDataBase);
        if (!bartender.isPresent()) {
            barDto.setResponseCode(400);
            barDto.setMessage("No se encontro el id de la pila indicado.");
            barDto.setTransactionId(tracer.currentSpan().context().traceIdString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(barDto);
//            throw new NotFoundException("The provided id does not exist.");
        }

        String input_array = bartender.get().getInput_array();
        String[] parts = input_array.split(",");

//        List<String> list = Arrays.stream(parts).collect(Collectors.toList());
//        list.stream().forEach(System.out::println);
        List<Integer> values = new ArrayList<>();

        for (String part : parts) {
            values.add(Integer.parseInt(part));
        }

        String solution = this.solve(iteraciones, values);
        barDto.setResponseCode(200);
        barDto.setData(solution);
        return ResponseEntity.status(HttpStatus.OK).body(barDto);


//        if (idPilaDataBase > 6 || idPilaDataBase < 1) {
//            barDto.setResponseCode(400);
//            barDto.setMessage("No se encontro el id de la pila indicado.");
//            barDto.setTransactionId(tracer.currentSpan().context().traceIdString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(barDto);
//        }
    }

}
