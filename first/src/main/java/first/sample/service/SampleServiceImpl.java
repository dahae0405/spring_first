package first.sample.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import first.sample.dao.SampleDAO;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO; /* 데이터 접근을 위한 DAO (data access object) 객체 선언*/
	
	/*
	 * 	selectBoardList의 결과값으로 sampleDAO 클래스의 selectBoardList 메서드를 호출하고, 그 결과값을 바로 반환(return) 하였다.
	 */
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

};