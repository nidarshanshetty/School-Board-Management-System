package com.school.sbm.service;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.ScheduleRequest;
import com.school.sbm.responsedto.ScheduleResponse;
import com.school.sbm.utility.ResponseStructure;

public interface IScheduleService {

	ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(int schoolId, ScheduleRequest scheduleRequest);

}
