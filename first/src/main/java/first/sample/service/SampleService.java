package first.sample.service;

import java.util.List;
import java.util.Map;

public interface SampleService {
	
	/*
	public List<Map<String, Object>> selectBoardList(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;
	

}
