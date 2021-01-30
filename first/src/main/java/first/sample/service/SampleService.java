package first.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface SampleService {
	
	/*
	public List<Map<String, Object>> selectBoardList(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	// 목록 조회
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;
	
	// 상세 입력
	/*void insertBoard(Map<String, Object> map) throws Exception; 첨부파일 형식 데이터를 받기휘해 형식 변경 */
	void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	// 상세 조회
	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;
	
	// 상세 수정
	void updateBoard(Map<String, Object> map) throws Exception;
	
	// 상세 삭제
	void deleteBoard(Map<String, Object> map) throws Exception;
	
	
}
