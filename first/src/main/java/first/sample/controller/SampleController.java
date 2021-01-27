package first.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.sample.service.SampleService;
import first.sample.service.SampleServiceImpl;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="sampleService")
	private SampleServiceImpl sampleService;
	
	@RequestMapping(value="/sample/openSampleBoardList.do")
    public ModelAndView openSampleBoardList(Map<String,Object> commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/boardList");
    	System.out.println("111");
    	List<Map<String,Object>> list = sampleService.selectBoardList(commandMap);
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	@RequestMapping(value="/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("");
		System.out.println("2222");
		System.out.println("commandMap = "+commandMap);
		System.out.println("commandMap > "+commandMap.isEmpty());
		if(commandMap.isEmpty() == false){
			System.out.println("3333");
			Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String,Object> entry = null;
			while(iterator.hasNext()){
				entry = iterator.next();
				System.out.println("entry = "+entry);
				
				log.info("key : "+entry.getKey()+", value : "+entry.getValue());
			}
		}
		return mv;
	};
	
};





