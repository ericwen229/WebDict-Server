package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class QueryController {

    @RequestMapping(value = "/q", method = RequestMethod.GET)
    public Query query(@RequestParam(value = "word") String word,
                       @RequestParam(value = "baidu", defaultValue = "false") String baidu,
                       @RequestParam(value = "youdao", defaultValue = "false") String youdao,
                       @RequestParam(value = "jinshan", defaultValue = "false") String jinshan) {

        Query q = new Query(word);
        if (!baidu.equalsIgnoreCase("false")) {
            q.queryBaidu();
        }
        if (!youdao.equalsIgnoreCase("false")) {
            q.queryYoudao();
        }
        if (!jinshan.equalsIgnoreCase("false")) {
            q.queryJinshan();
        }
        return q;
    }

}
