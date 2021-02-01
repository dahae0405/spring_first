package first.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.common.util.FileUtils;
import first.sample.service.SampleServiceImpl;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name = "sampleService")
	private SampleServiceImpl sampleService;

	// 게시판 목록
	@RequestMapping(value = "/sample/openBoardList.do")
	public ModelAndView openSampleBoardList(Map<String, Object> commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardList");
		log.info("Controller집입 >> openBoardList");
		List<Map<String, Object>> list = sampleService.selectBoardList(commandMap);
		log.info("DB데이터get 작업완료 list = " + list);
		mv.addObject("list", list);

		return mv;
	}

	// - commandMap 생성 및 test
	@RequestMapping(value = "/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");
		System.out.println("2222");
		System.out.println("commandMap = " + commandMap);
		System.out.println("commandMap > " + commandMap.isEmpty());
		if (commandMap.isEmpty() == false) {
			System.out.println("3333");
			Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				System.out.println("entry = " + entry);

				log.info("key : " + entry.getKey() + ", value : " + entry.getValue());
			}
		}
		return mv;
	};

	// 상세 - 작성하기
	@RequestMapping(value = "/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardWrite");
		return mv;
	}

	// 입력작업
	@RequestMapping(value = "/sample/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{

		log.info("Controller - insertBoard");
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		log.info("model생성 작업완료");
		
		sampleService.insertBoard(commandMap.getMap(), request);
		return mv;
	}

	// 상세 조회
	@RequestMapping(value = "/sample/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardDetail");	
		
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());	// 상세정보 조회
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));

		return mv;
	}

	// 상세 - 수정페이지 조회
	@RequestMapping(value = "/sample/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardUpdate");
		
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}

	// 상세 - update
	@RequestMapping(value="/sample/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{ // HttpServletRequest를 추가
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		
		sampleService.updateBoard(commandMap.getMap(), request);
		
		mv.addObject("IDX", commandMap.get("IDX"));
		return mv;
	}

	
	// 상세 - delete
	@RequestMapping(value="/sample/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		
		sampleService.deleteBoard(commandMap.getMap());
		
		return mv;
	}
	



};
