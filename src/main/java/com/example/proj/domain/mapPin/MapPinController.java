package com.example.proj.domain.mapPin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


//TODO: BindingResult 예외처리 추가
@Controller
@RequiredArgsConstructor
@RequestMapping("/mapPin")
public class MapPinController {
    private final MapPinService  mapPinService;

    @PostMapping("/add")
    public String addMapPin(@Valid MapPinSaveRequestDto mapPinSaveRequestDto,
                          @AuthenticationPrincipal UserDetails userDetails,
                          BindingResult bindingResult, Model model) {
        //TODO:bindingResult 예외처리
//        if (bindingResult.hasErrors()) {
//        }
        mapPinSaveRequestDto.setUserId(userDetails.getUsername());
        mapPinService.addMapPin(mapPinSaveRequestDto);

        return "";
    }

    @PostMapping("/delete")
    public String deleteMapPin(@RequestParam("mapPinId") Long mapPinId) {

        mapPinService.deletePinById(mapPinId);
        return "";
    }

    @GetMapping("/list")
    public String listMapPins(Model model,@RequestParam(name = "tag")String tag) {
        model.addAttribute("mapPinList",mapPinService.findAllMapPinByTagName(tag));

        return "";
    }

}
