package simsimbooks.couponserver.common.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
	/**
	 * 락을 걸려는 엔티티 대상
	 */
	LockTarget target();

	/**
	 * 락을 걸려는 대상의 식별자
	 */
	String key();

	/**
	 *  락을 획득을 위해 기다리는 시간 (default : 5)
	 */
	long waitTime() default 5L;

	/**
	 * 락을 가지고 있을 수 있는 최대 시간 (default : 3)
	 */
	long leaseTime() default 3L;

	/**
	 * 락의 시간 단위 (default : 초)
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
