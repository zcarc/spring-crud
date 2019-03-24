package com.wrkbr.email;

import lombok.extern.log4j.Log4j;

import java.util.stream.IntStream;

@Log4j
public class GenerateChar {


    public String generateChar() {

        StringBuffer buffer = new StringBuffer();

        IntStream.rangeClosed(1, 10).forEach(i -> {

            double d = Math.random();

            if (i % 2 == 1) {

                char c = (char) ((int) (d * 26 + 97));
                //log.info("i: " + i + ", 홀수: " + c);
                buffer.append(c);

            } else if (i % 2 == 0) {

                char c = (char) ((int) (d * 26) + 65);
                //log.info("i: " + i + ", 짝수: " + c);
                buffer.append(c);
            }

        });

        String result = buffer.toString();
        log.info("Generated result: " + result);

        return result;


    }

}
