package simsimbooks.couponserver.common.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LockAspect {
	private final SpelExpressionParser parser = new SpelExpressionParser();
	private final RedissonClient redissonClient;

	@Around("@annotation(simsimbooks.couponserver.common.lock.DistributedLock)")
	public Object retryOptimisticLock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature sig = (MethodSignature)joinPoint.getSignature();
		DistributedLock annotation = sig.getMethod().getAnnotation(DistributedLock.class);
		LockTarget target = annotation.target();
		// SpEL을 사용해 id 값을 꺼냄.
		String key = getDynamicValue(sig.getParameterNames(), joinPoint.getArgs(), annotation.key());

		RLock rLock = redissonClient.getLock("LOCK:" + target + ":" + key);
		try {
			boolean available = rLock.tryLock(annotation.waitTime(), annotation.leaseTime(), annotation.timeUnit());
			if (!available) {
				throw new BusinessException(ErrorCode.SERVICE_BUSY);
			}
			return joinPoint.proceed();
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try{rLock.unlock();}
			catch (IllegalMonitorStateException e) {log.info("Redisson Lock already unlock");}
		}
	}

	/**
	 * SpEL(Spring Expression Language)은 객체 그래프를 조회하고 조작하는데 사용되는 표현식 언어.
	 */
	private String getDynamicValue(String[] parameterNames, Object[] args, String key) {
		// 메서드에 선언된 파라미터 이름 목록을 문자열 배열로 가져오기
		// 예를 들어 메서드 선언이 issue(Request request, Long id) 라면 names = ["request","id"] 가 된다.
		// String[] parameterNames <- sig.getParameterNames();

		// 메서드를 호출할 때 전달된 실제 인자 값을 객체 배열로 가져온다.
		// 예를 들어 issue("hello", 15) 라면  args = ["hello", 15] 가 된다.
		// Object[] args <- joinPoint.getArgs();

		// SpEL 평가 시 사용할 컨텍스트를 생성한다.
		// 이 컨테스트에 names와 args가 바인딩 된다.
		EvaluationContext ctx = new StandardEvaluationContext();

		for (int i = 0; i < parameterNames.length; i++) {
			ctx.setVariable(parameterNames[i], args[i]);
		}

		// 애노테이션에 지정된 SpEL 표현식을 평가해 실제 id 문자열을 가져온다.
		return parser.parseExpression(key).getValue(ctx, String.class);
	}
}