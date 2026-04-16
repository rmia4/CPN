package com.example.proj.domain.mapPin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
                          BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//        }
        mapPinService.addMapPin(mapPinSaveRequestDto);

        return "";
    }

    @PostMapping("/delete")
    public String deleteMapPin(@RequestParam("mapPinId") Long mapPinId) {

        mapPinService.deletePinById(mapPinId);
        return "";
    }

    @GetMapping("/list")
    public String listMapPins(Model model) {
        model.addAttribute("mapPinList",mapPinService.findAllMapPin());

        return "";
    }

}
