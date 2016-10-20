/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.scheduling;

import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.service.MembersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WomanVaccineSchedule {
	
	private static Logger logger = LoggerFactory.getLogger(MembersService.class.toString());
	private MembersScheduleService membersScheduleService;
	
	@Autowired
	public WomanVaccineSchedule(MembersScheduleService membersScheduleService) {
		this.membersScheduleService = membersScheduleService;	
	}
	
	public void immediateWomanVaccine(FormSubmission submission, Members members, Map<String, String> membersFields, String scheduleName, String immediateScheduleName, String refDate, String cond1, String cond2) {
		
		if(membersFields.containsKey(cond1))
		if(!membersFields.get(cond1).equalsIgnoreCase("") && membersFields.get(cond1) != null)
		if(membersFields.get(cond1).equalsIgnoreCase("1")){
			if(membersFields.containsKey(refDate)){
			if(!membersFields.get(refDate).equalsIgnoreCase("") && membersFields.get(refDate) != null)
			if(isValidDate(membersFields.get(refDate)))
				membersScheduleService.enrollimmediateMembersVisit(
					members.caseId(),submission.anmId(),membersFields.get(refDate),submission.instanceId(),scheduleName,immediateScheduleName);
			}
			else if(isValidDate(submission.getField(REFERENCE_DATE))){
				membersScheduleService.enrollimmediateMembersVisit(
					members.caseId(),submission.anmId(),submission.getField(REFERENCE_DATE),submission.instanceId(),scheduleName,immediateScheduleName);
			}
			}
		
		if(membersFields.containsKey(cond2))
			if(!membersFields.get(cond2).equalsIgnoreCase("") && membersFields.get(cond2) != null)
			if(membersFields.get(cond2).equalsIgnoreCase("3"))
				membersScheduleService.unEnrollAndCloseSchedule(
					members.caseId(),submission.anmId(),immediateScheduleName,LocalDate.parse(submission.getField(REFERENCE_DATE)));
		
	}
	
	public void WomanVaccine(FormSubmission submission, Members members, Map<String, String> membersFields, String scheduleName, String refDate, String cond) {
		
		if (membersFields.containsKey(cond))
		if (membersFields.get(cond) == null || membersFields.get(cond).equalsIgnoreCase(""))
		if (membersFields.containsKey(refDate)){
		if(isValidDate(membersFields.get(refDate)))
			membersScheduleService.enrollWomanTTVisit(members.caseId(),submission.anmId(),membersFields.get(refDate),scheduleName);
		}
		
		if (!membersFields.containsKey(cond))
		if (membersFields.containsKey(refDate)){
		if(isValidDate(membersFields.get(refDate)))
			membersScheduleService.enrollWomanTTVisit(members.caseId(),submission.anmId(),membersFields.get(refDate),scheduleName);
		}
		
		if (membersFields.containsKey(cond))
		if (membersFields.get(cond) != null && !membersFields.get(cond).equalsIgnoreCase("")){
			membersScheduleService.unEnrollAndCloseSchedule(
					members.caseId(),submission.anmId(),scheduleName,LocalDate.parse(submission.getField(REFERENCE_DATE)));
		}		
	}
	
	public void WomanFollowupVaccine(FormSubmission submission, Members members, String scheduleName, String refDate, String cond) {
		
		if (submission.getField(cond) == null || submission.getField(cond).equalsIgnoreCase("")){
		if (submission.getField(refDate) != null && !submission.getField(refDate).equalsIgnoreCase(""))
		if(isValidDate(submission.getField(refDate)))
			membersScheduleService.enrollWomanTTVisit(members.caseId(),submission.anmId(),submission.getField(refDate),scheduleName);
		}
		
		if (submission.getField(cond) != null && !submission.getField(cond).equalsIgnoreCase(""))
			membersScheduleService.unEnrollAndCloseSchedule(
					members.caseId(),submission.anmId(),scheduleName,LocalDate.parse(submission.getField(REFERENCE_DATE)));	
	}
	
	public boolean isValidDate(String dateString) {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        df.parse(dateString);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
	

}