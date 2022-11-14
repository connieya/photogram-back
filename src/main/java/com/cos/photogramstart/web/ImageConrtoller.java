package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageConrtoller {

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }
}
