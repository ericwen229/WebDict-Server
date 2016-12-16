package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.Like;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public Like like(@RequestParam(value = "word") String word,
                     @RequestParam(value = "source") String source,
                     @RequestParam(value = "dislike", defaultValue = "false") String dislike) {
        return new Like(true);
    }

}
