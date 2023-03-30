package com.example.mayara.erudio;

import com.example.mayara.erudio.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Math.sqrt;
import static java.util.Arrays.stream;
import static org.apache.tomcat.util.http.parser.HttpParser.isNumeric;

@RestController
public class MathController {

    @RequestMapping(value = "/calc/{numberOne}/{numberTwo}/{option}/", method = RequestMethod.GET)
    public Double calc(@PathVariable("numberOne") String numberOne,
                       @PathVariable("numberTwo") String numberTwo, @PathVariable("option") String option)

            throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please, set a numeric value");
        }
        switch (option) {
            case "sum":
                return convertToDouble(numberOne) + convertToDouble(numberTwo);
            case "minus":
                return convertToDouble(numberOne) - convertToDouble(numberTwo);
            case "div":
                return convertToDouble(numberOne) / convertToDouble(numberTwo);
            case "avg":
                double valores[] = {convertToDouble(numberOne), convertToDouble(numberTwo)};
                return Arrays.stream(valores).average().getAsDouble();
            case "sqrt":
                return sqrt(convertToDouble(numberOne));

        }

        return null;
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null) return 0D;
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}