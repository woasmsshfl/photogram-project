package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // IoC컨테이너에 띄울수 있는 최상위 상속자.
@Aspect // AOP 처리할 수 있는 hendler로 만들어주는 어노테이션
public class ValidationAdvice {

	// 관점지향프로그래밍 : AOP(Aspect-oriented programming)
	// 기능을 만들 때, 1. 핵심은 데이터를 잘 받는것이다.
	// 2. DB에서 데이터를 확인하고 기능이 실현된다.
	// 이 떄, 유효성검사, 보안처리를 해야하는데 내용이 너무 많다.
	// 핵심기능이 아닌 공통기능(전처리, 후처리)을 한번에 필터링한다.

	// @After("execution(* 경로지정)") // 특정 함수 실행한 뒤에 실행됨
	// @Before("execution(* 경로지정)") // 특정 함수 실행하기 직전에 실행됨
	// @Around("execution(* 경로지정)") // 특정 함수 실행 전후로 관여함
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	// web.api 폴더 내부에 Controller로 끝나는 모든 파일들에 모든 메서드에 관여한다.
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// ProceedingJoinPoint : 지정 경로의 내부 함수가 실행됐을 때, 그 내부 함수의 모든 정보에
		// 접근할 수 있게 해주는 변수(매개변수 포함)

		System.out.println("====================================");
		System.out.println("web api controller");
		System.out.println("====================================");

		Object[] args = proceedingJoinPoint.getArgs(); // 내부 매개변수를 확인해보기
		for (Object arg : args) {
			if (arg instanceof BindingResult) { // 매개변수에 BindingResult가 있으면

				// System.out.println("유효성 검사하는 메서드입니다.");
				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					// System.out.println("유효성검사 실행");
					Map<String, String> errorMap = new HashMap<>();

					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
				}

			}
		}
		// proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
		// profile 함수보다 먼저 실행

		// proceed() : 지정경로의 내부 함수로 다시 돌아가서 실행되게 해주는 함수.
		return proceedingJoinPoint.proceed(); 
	}

	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("====================================");
		System.out.println("web controller");
		System.out.println("====================================");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();

					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationException("유효성 검사 실패함", errorMap);
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
}
