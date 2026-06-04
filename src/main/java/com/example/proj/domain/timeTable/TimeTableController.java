package com.example.proj.domain.timeTable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class TimeTableController {
    private final TimeTableService timeTableService;


    //update
    @PostMapping("/add")
    public Object addTimeTable(@Valid TimeTableUpdateDto dto, BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails, Model model,
                               @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        boolean ajaxRequest = "XMLHttpRequest".equals(requestedWith);

        if (bindingResult.hasErrors()) {
            if (ajaxRequest) {
                return ResponseEntity.badRequest().body("시간표 입력값을 확인해주세요.");
            }

            return "pages/timeTable/timetable";
        }

        dto.setUserId(userDetails.getUsername());

        timeTableService.updateTimeTable(dto);
        if (ajaxRequest) {
            return ResponseEntity.ok().build();
        }

        return "redirect:/timetable";
    }

    @GetMapping("")
    public String listTimeTable(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<TimeTableModel> timeTable= timeTableService.getAllTimeTable(userDetails.getUsername());
        model.addAttribute("timeTable",timeTable);
        return "pages/timeTable/timetable";
    }

    @PostMapping("/delete")
    public String deleteTimeTable(@RequestParam(name = "id") Long id, Model model){
        timeTableService.deleteTimeTable(id);

        return "redirect:/timetable";
    }

}
