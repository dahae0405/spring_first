package first.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

@Service("sampleService") /* 서비스 anotation으로 선언 */
public class SampleServiceImpl implements SampleService {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "sampleDAO")
	private SampleDAO sampleDAO; /* 데이터 접근을 위한 DAO (data access object) 객체 선언 */

	/*
	 * selectBoardList의 결과값으로 sampleDAO 클래스의 selectBoardList 메서드를 호출하고, 그 결과값을 바로
	 * 반환(return) 하였다.
	 */
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	};

	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		/*
		 * // 파일정보 조회 ( 잘들어왔는지 test) MultipartHttpServletRequest
		 * multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		 * Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		 * MultipartFile multipartFile = null;
		 * 
		 * while(iterator.hasNext()){ multipartFile =
		 * multipartHttpServletRequest.getFile(iterator.next());
		 * if(multipartFile.isEmpty() == false){
		 * log.debug("------------- file start -------------");
		 * log.debug("name : "+multipartFile.getName());
		 * log.debug("filename : "+multipartFile.getOriginalFilename());
		 * log.debug("size : "+multipartFile.getSize());
		 * log.debug("-------------- file end --------------\n"); } }
		 */
		
		sampleDAO.insertBoard(map);
		
		List<Map<String,Object>> list = FileUtils.parseInsertFileInfo(map, request);
		
		for(int i=0, size=list.size(); i<size; i++){
			sampleDAO.insertFile(list.get(i));
		}

	}


	/*
	 * 그리고 게시판 상세 내용은 목록과는 다르게 단 하나의 행(reocrd)만 조회하기 때문에, Map의 형태로 결과값을 받았다.
	 * 
	 * 게시글 상세를 조회하기 위해서는 다음의 두가지의 동작이 필요하다. 1) 해당 게시글의 조회수를 1 증가시킨다. 2) 게시글의 상세내용을
	 * 조회한다. 즉, 이 두가지 동작은 하나의 트랜잭션으로 처리가 되어야 하는 부분이다.
	 * 
	 * 여기서는 게시글을 조회하기 위해서 위의 2가지 동작을 수행하는 역할을 하고있다. 따라서 DAO에서 2개 이상의 동작을 수행하면 안된다.
	 * DAO는 단순히 DB에 접속하여 데이터를 조회하는 역할만 수행하는 클래스다. 마지막으로 DAO에서는 위에서 설명한 두가지 동작에 대한
	 * 쿼리를 각각 호출하도록 하였다. updateHitCnt 라는 쿼리와 selectBoardDetail 이라는 쿼리를 각각 수행하는것을 볼 수
	 * 있다
	 * 
	 * 확인 여기서 잘 보면 쿼리는 2개가 실행되었지만 단 하나의 START, END를 볼 수 있다.
	 * 
	 * 
	 */
	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		// 게시판 조회수 갱신
		sampleDAO.updateHitCnt(map);
		
		// 게시판 정보 조회
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);
		
		// 피일 정보 조회
		List<Map<String,Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}

	/* 수정 */
	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception{
		// 게시판 정보 - 갱신
		sampleDAO.updateBoard(map);
		
		// 게시판 파일정보 - 초기화
		sampleDAO.deleteFileList(map); //첨부파일을 전부 삭제처리(DEL_GB = 'Y')를 하는 역할
		// 게시판 파일정보 - 분기별로 갱신 혹은 신규등록
		List<Map<String,Object>> list = FileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){ //IS_NEW라는 값이 "Y"인 경우는 신규 저장될 파일이라는 의미이고, "Y"가 아니면 기존에 저장되어 있던 파일이라는 의미이다.
				sampleDAO.insertFile(tempMap);
			}
			else{
				sampleDAO.updateFile(tempMap);
			}
		}
	};
	
	/* 삭제 */
	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}

};