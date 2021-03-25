package wbhhc.luoshu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalcController {



    @GetMapping("/calc/index")
    public String index(Model model){
        LuoShu luoShu =new LuoShu();
        model.addAttribute("msg","");
        model.addAttribute("luoshu", luoShu);
        model.addAttribute("typesubmit","0");
        return "calc/index";
    }

    @PostMapping("/calc/index")
    public String submit(LuoShu luoShu, String typesubmit,
                         Model model){
        String msg=null;
        try{
            if(!luoShu.autoFill())
                throw new RuntimeException("输入的数字有误！");
            if("1".equals(typesubmit))
                luoShu.rotate90();
            if("2".equals(typesubmit))
                luoShu.inverse90();
        }catch (Exception ex){
            msg=ex.getMessage();
        }


        model.addAttribute("luoshu", luoShu);
        model.addAttribute("msg",msg);
        model.addAttribute("typesubmit",typesubmit);
        return "calc/index";
    }
}
