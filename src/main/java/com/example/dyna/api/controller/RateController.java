package com.example.dyna.api.controller;

import com.example.dyna.api.service.IRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/api/rates")
public class RateController implements IRateController {
    private IRateService service;

    @Override
    @GetMapping("/average/{code}/{date}")
    public ResponseEntity<Object> findAvgRateByDate(@PathVariable String code, @PathVariable LocalDate date) {
        try {
            var result = service.findAvgRateByDate(code, date);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/average/min-max/{code}/{quotations}")
    public ResponseEntity<Object> findMinMaxAvgLastRates(@PathVariable String code, @PathVariable int quotations) {
        try {
            var result = service.findMinMaxAvgLastRates(code, quotations);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/difference/max/{code}/{quotations}")
    public ResponseEntity<Object> findMaxRateDiff(@PathVariable String code, @PathVariable int quotations) {
        try {
            var result = service.findMaxRateDiff(code, quotations);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }  catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
