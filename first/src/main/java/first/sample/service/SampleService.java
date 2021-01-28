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
	// 목록 조회
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;
	
	// 입력
	void insertBoard(Map<String, Object> map) throws Exception;
	
	// 상세 조회
	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;

	
}
