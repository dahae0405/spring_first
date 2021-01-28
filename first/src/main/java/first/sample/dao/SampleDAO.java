package first.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;

@Repository("sampleDAO") /* bean을 수동으로 등록 */
public class SampleDAO extends AbstractDAO {

	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectList("sample.selectBoardList", map);
	}

	public void insertBoard(Map<String, Object> map) throws Exception {
		insert("sample.insertBoard", map);
	}

	
	public void updateHitCnt(Map<String, Object> map) throws Exception{
		update("sample.updateHitCnt", map);
	}
	
	/*
	 * 주의) 리턴값 List가 아니라 정보이므로 MAp임
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
	}

};