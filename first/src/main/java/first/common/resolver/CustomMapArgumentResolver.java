package first.common.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import first.common.common.CommandMap;

/* 없어도 개발은 할 수 있지만, 개발을 더욱 편하게 할수 있는 역할

HandlerMethodArgumentResolver 
		인터페이스다
 		* 스프링 사용 시, 컨트롤러(Controller)에 들어오는 파라미터(Parameter)를 수정하거나 공통적으로 추가를 해주어야 하는 경우가 있다. 

 
 		* 로그인을 한 사용자의 사용자 아이디나 닉네임등을 추가하는것을 생각해보자. 
 			보통 그런 정보는 세션(Session)에 담아놓고 사용하는데, DB에 그러한 정보를 입력할 때에는 
			결국 세션에서 값을 꺼내와서 파라미터로 추가를 해야한다.


 *1. CommandMap 클래스 생성
	 * DTO
	 * HandlerMethodArgumentResolver는 컨트롤러의 파라미터가 Map 형식이면 동작하지 않는다. 

 
 *2. HandlerMethodArgumentResolver 작성
	  * 따라서 <mvc:annotation-driven/>을 선언하려면 Map을 그대로 사용할 수 없고, 선언하지 않으면 문제는 없다. 그렇지만 앞으로 포스팅할 내용중에는 <mvc:annotation-driven/>을 선언해야 하는 경우가 있기때문에, 여기서는 Map을 대신할 CommandMap을 작성한다.
	
		* 여기서 중요한점은 절대로 Map을 상속받으면 안된다.
		* ArgumentResolver를 거치지 않게 되니 주의
	
	 * 구성
	 	* 	아까 정의했던 CommandMap 객체를 생성
	 	* 	request에 담겨있는 모든 키(key)와 값(value)을 commandMap에 저장
	 	* 	request에 있는 값을 iterator를 이용하여 하나씩 가져오는 로직	

 *3. CustomMapArgumentResolver 등록

	* CustomMapArgumentResolver는 root context 영역에 등록이 되어야 한다. 따라서 action-servlet.xml에 등록해야 한다.
	* <mvc:"argument-resolvers> 태그를 이용하여 우리가 만든 CustomMapArgumentResolver의 빈(bean)을 수동으로 등록


 *4. Controller의 수정 및 테스트
	* log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
	* localhost:8080/first/sample/testMapArgumentResolver.do?aaa=value1&bbb=value2


*/
public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return CommandMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		CommandMap commandMap = new CommandMap();
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration<?> enumeration = request.getParameterNames();
		
		String key = null;
		String[] values = null;
		while(enumeration.hasMoreElements()){
			key = (String) enumeration.nextElement();
			values = request.getParameterValues(key);
			if(values != null){
				commandMap.put(key, (values.length > 1) ? values:values[0] );
			}
		}
		return commandMap;
	}
};


