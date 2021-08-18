package com.fire.rest.Controller;


import com.fire.model.MathOperation;
import com.fire.utils.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fire.rest.calculator.utils.Constants;

import java.util.UUID;


@RestController
@RequestMapping("/v1")
public class MathOperationController {

        private static final Logger logger = LoggerFactory.getLogger(MathOperationController.class);

        private RabbitTemplate rabbitTemplate;

        @Autowired
        MathOperationController(RabbitTemplate rabbitTemplate){
            this.rabbitTemplate =  rabbitTemplate;
        }

        @GetMapping("/sum")

        public ResponseEntity getSum(@RequestParam("a") int a, @RequestParam("b") int b  ){

            MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                    a,
                    b,
                    Operator.ADDITION);

            logger.info("created mathOperation for "+Operator.ADDITION +" with uuid:" +mathOperation.getUuid() );

            this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                    Constants.OPERATION_ROUTING_KEY,mathOperation);

            logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

            return  buildResponse(mathOperation.getUuid());

        }
        @GetMapping("/sub")
        public ResponseEntity getSub(@RequestParam("a") int a, @RequestParam("b") int b  ){

            MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                    a,
                    b,
                    Operator.SUBTRACTION);

            logger.info("created mathOperation for "+Operator.SUBTRACTION +" with uuid:" +mathOperation.getUuid() );

            this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                    Constants.OPERATION_ROUTING_KEY,mathOperation);

            logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

            return  buildResponse(mathOperation.getUuid());

        }
        @GetMapping("/mult")
        public ResponseEntity getMult(@RequestParam("a") int a, @RequestParam("b") int b  ){

            MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                    a,
                    b,
                    Operator.MULTIPLICATION);
            logger.info("created mathOperation for "+Operator.SUBTRACTION +" with uuid:" +mathOperation.getUuid() );


            this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                    Constants.OPERATION_ROUTING_KEY,mathOperation);
            logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );


            return  buildResponse(mathOperation.getUuid());

        }
        @GetMapping("/div")
        public ResponseEntity getDivisionOf(@RequestParam("a") int a, @RequestParam("b") int b  ){
            MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                    a,
                    b,
                    Operator.DIVISION);
            logger.info("created mathOperation for "+Operator.DIVISION +" with uuid:" +mathOperation.getUuid() );

            this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                    Constants.OPERATION_ROUTING_KEY,mathOperation);

            logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

            return  buildResponse(mathOperation.getUuid());
        }



        private HttpHeaders setDefaultHeaders(UUID  uuid){
            HttpHeaders headers = new HttpHeaders();
            headers.set("request-id",uuid.toString());

            return headers;
        }

        private  ResponseEntity buildResponse(UUID uuid){
            return  ResponseEntity
                    .ok()
                    .headers(setDefaultHeaders(uuid)).build();
        }

    }
