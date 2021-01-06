package aspects;

import beans.Client;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Aspect
public class LogAspect {
    Logger logger=Logger.getLogger(LogAspect.class.getName());
    public LogAspect() throws IOException {
        logger.addHandler(new FileHandler("log.xml"));
        logger.setUseParentHandlers(false);

    }
    long tmp1,tmp2,tmp3;
    boolean test=true;
    @Pointcut("execution(* beans.Client.verser(..))")
    public void pointcutVerser(){}

    @Pointcut("execution(* beans.Client.retirer(..))")
    public void pointcutRetirer(){}


    @Before("pointcutVerser()")
    public void beforeVerser(JoinPoint joinPoint){
        tmp1=System.currentTimeMillis();
        logger.info("---------------------");
        //logger.info("AVANT "+joinPoint.getSignature());
        Client client= (Client) joinPoint.getTarget();
        logger.info("CLIENT NAME : " + client.getNom());
        logger.info("SOLDE DU COMPTE AVANT VERSEMENET : " + client.getCp().getSolde());
    }


    @After("pointcutVerser()")
    public void afterVerser(JoinPoint joinPoint){
        tmp2=System.currentTimeMillis();
        logger.info("------------------- ");
        //logger.info("APRES "+joinPoint.getSignature());
        logger.info("TEMPS EXECUTION " + (tmp2-tmp1));
        Client client= (Client) joinPoint.getTarget();
        logger.info("CLIENT NAME : " + client.getNom());
        logger.info("SOLDE DU COMPTE APRES VERSEMENT :" + client.getCp().getSolde());
    }


    @Around("pointcutRetirer()")
    public void around(ProceedingJoinPoint proceedingJoinPoint,JoinPoint joinPoint) throws Throwable {
        tmp1=System.currentTimeMillis();
        Client client= (Client) joinPoint.getTarget();
        logger.info("BEFORE DEBIT -------------------------------------------");
        //logger.info("AVANT "+joinPoint.getSignature());
        logger.info("TEMPS EXECUTION : " + tmp3);
        logger.info("CLIENT NAME : " + client.getNom());
        logger.info("SOLDE DU COMPTE AVANT DEBIT : " + client.getCp().getSolde());

            if ((Double) joinPoint.getArgs()[0] < client.getCp().getSolde()) {
                proceedingJoinPoint.proceed();
                tmp2 = System.currentTimeMillis();
                logger.info("AFTER------------------------------------------------- ");
                //logger.info("APRES "+joinPoint.getSignature());
                logger.info("TEMPS EXECUTION " + (tmp2-tmp1));
                //Client client= (Client) joinPoint.getTarget();
                logger.info("CLIENT NAME : " + client.getNom());
                logger.info("SOLDE DU COMPTE APRES DEBIT :" + client.getCp().getSolde());

            }else {
                logger.info("SOLDE INSUFISANT !!! ");

            }
        }
    }

