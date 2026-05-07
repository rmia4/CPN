package com.example.proj.domain.timeTable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String addTimeTable(@Valid TimeTableUpdateDto dto, BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails, Model model) {
        dto.setUserId(userDetails.getUsername());

        //TODO:예외처리
        if(bindingResult.hasErrors()){
        }
        timeTableService.updateTimeTable(dto);
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
