package first.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;

@Repository("sampleDAO") /* bean을 수동으로 등록 */
public class SampleDAO extends AbstractDAO{

	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

};