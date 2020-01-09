package be.sdlg.apps.edcmobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.svc.StudyDesignSvc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Transactional
@RequestMapping("/study")
public class StudyController {
	@Autowired
	private StudyDesignSvc studyDesignSvc;
	private static final org.slf4j.Logger log =
		    org.slf4j.LoggerFactory.getLogger(StudyController.class);
	 @GetMapping
	  public String showDesignForm(@RequestParam(value="id", required=false) Long id,Model model) {
		 Study study;
		 if (id==null) {
			 study = new Study();
		 } else {
			 study = studyDesignSvc.findStudy(id);
		 }
		 model.addAttribute("study", study);
		 return "study";
	 }
	 @PostMapping
	 public String processDesign(Study study) {
	   
	   log.info("Processing design: " + study);

	   return "redirect:/studyList";
	 }
}
