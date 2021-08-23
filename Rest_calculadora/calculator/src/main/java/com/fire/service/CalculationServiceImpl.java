package com.fire.service;

import com.fire.model.MathOperation;
import com.fire.model.MathOperationResult;
import com.fire.utils.Constants;
import com.fire.utils.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculationServiceImpl {

    private static final Logger  logger = LoggerFactory.getLogger(CalculationServiceImpl.class);


    @RabbitListener(queues = Constants.OPERATION_QUEUE)
    public void processMathOperation(MathOperation mathOperation) {

        logger.info("Fetching the mathOperation "+ mathOperation.getUuid() +" from the queue operation queue");

        Optional<MathOperationResult> mathOperationResult =
                this.operate(mathOperation);


        logger.info("Sending the mathOperation "+ mathOperation.getUuid() +" to result queue");


        if(mathOperationResult.isPresent()){
            logger.info("Calculation succeeded with result: "+ mathOperationResult.get().getResult() );
            MDC.put("result",mathOperationResult.get().getResult().toString());

            mathOperationResult.get();
        };

    }

    private Optional<MathOperationResult> operate(MathOperation mathOperation){


        if(mathOperation.getOperator()== Operator.ADDITION){
            logger.info("Performing ADDITION operation of: "+mathOperation.getA() +" + "+mathOperation.getB());

            return Optional.of(new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() + mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.SUBTRACTION){

            logger.info("Performing SUBTRACTION operation of: "+mathOperation.getA() +" - "+mathOperation.getB());

            return Optional.of(new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() - mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.MULTIPLICATION){

            logger.info("Performing MULTIPLICATION operation of: "+mathOperation.getA() +" * "+mathOperation.getB());

            return Optional.of( new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() * mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.DIVISION){
            if(mathOperation.getB()==0){
                logger.error("Operating not acceptable, first operating can not be zero");
                throw new IllegalArgumentException("Operating not acceptable, first operating can not be zero");
            }

            logger.info("Performing DIVISION operation of: "+mathOperation.getA() +" / "+mathOperation.getB());


            return Optional.of( new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() / mathOperation.getB()+"")
            ));
        }
        return Optional.empty();
    }


}