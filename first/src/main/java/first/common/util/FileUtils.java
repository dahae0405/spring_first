package first.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/*	파일유틸
 * 		1. 파일 저장위치 설정
 *		2.  
 */
@Component("fileUtils")
public class FileUtils {
	private static final String filePath = "D:\\workSpace_Portfolio_personnal\\dev\\fileServer\\";	// 파일위치 설정 ( 이건 *외부 서버 디렉토리임 )
	
	// 첨부파일 - 등록
	public static List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		// 1) Request : 파일타입은 request로 받는다.
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request; 
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	// 2) 파일 정보들 샛팅
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();	// 다중 파일 전송할 계획
        Map<String, Object> listMap = null; 
        
        String boardIdx = (String)map.get("IDX");	// 신규 생성되는 게시글의 정보
        // 3. 유효성 체크 ( 파일 존재유무 )
        File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		// 4. 서버 디렉토리에 파일 업로드 및 파일 DTO(DB용) 생성
        		originalFileName = multipartFile.getOriginalFilename();	// 실제 파일명
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 확장자
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		file = new File(filePath + storedFileName); // 랜덤한 파일이름 생성
        		multipartFile.transferTo(file);				// 실제 서버 디렉토리에 저장
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("BOARD_IDX", boardIdx);
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        }
		return list;
	}
	
	
	// 첨부파일 - 수정
	public static List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null; 
		
		String boardIdx = (String)map.get("IDX");
		String requestName = null;
		String idx = null;
		
		
		while(iterator.hasNext()){
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;
				
				multipartFile.transferTo(new File(filePath + storedFileName));
				
				listMap = new HashMap<String,Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("BOARD_IDX", boardIdx);
				listMap.put("ORIGINAL_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
			}
			else{
				requestName = multipartFile.getName();
				idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
				if(map.containsKey(idx) == true && map.get(idx) != null){
					listMap = new HashMap<String,Object>();
					listMap.put("IS_NEW", "N");
					listMap.put("FILE_IDX", map.get(idx));
					list.add(listMap);
				}
			}
		}
		return list;
	}

}
