package net.atos.testoffer.usersmanagementapi.monitor;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * Logging call to Rest controller public operations
 * 
 * @author elheni
 *
 */
@Aspect
@Component
public class RestControllerMonitor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerMonitor.class);
	
	@Pointcut("execution(public * *(..))")
	private void anyPublicOperation() {
		/* this is a flag method to be used bellow	 */
	}

	@Pointcut("within(net.atos.testoffer.usersmanagementapi.rest..*)")
	private void inRestPackage() {
		/* this is a flag method to be used bellow	 */
	}
	
	@Pointcut("anyPublicOperation() && inRestPackage()")
    public void logPointcut(){
		/* this is a flag method to be used bellow	 */
	}
	
	/**
	 * Using Spring AOP this method log entering and exiting from RestController Methods
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.currentTimeMillis();
 		try {
 			LOGGER.info("Entering {} with arguments {}",joinPoint.toShortString(),Arrays.toString(joinPoint.getArgs()) );
 			Object result = joinPoint.proceed();
 			long end = System.currentTimeMillis();
 			LOGGER.info("Exiting {}\t with result {}\t Execution time : {}",joinPoint.toShortString(), result, (end - start) + " ms");
 			return result; 
 		} catch (Throwable e) {
 			long end = System.currentTimeMillis();
 			LOGGER.error("Exiting {} with error\t Execution time : {} with exception {}", joinPoint.toShortString(), (end - start) + " ms", e);
 			throw e;
 		}

	}
	
}
