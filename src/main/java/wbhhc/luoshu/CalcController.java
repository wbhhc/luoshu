package wbhhc.luoshu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalcController {



    @GetMapping("/calc/index")
    public String index(Model model){
        LuoShu luoShu=new LuoShu();
        model.addAttribute("msg","");
        model.addAttribute("luoshu",luoShu);
        model.addAttribute("typesubmit","0");
        return "calc/index";
    }

    @PostMapping("/calc/index")
    public String submit(LuoShu luoShu,String typesubmit,
                         Model model){


        Integer[][] arr=new Integer[3][3];
        String msg=null;
        try{
            Calc calc=new Calc(luoShu.getRow1col1(),luoShu.getRow1col2(),luoShu.getRow1col3(),
                    luoShu.getRow2col1(),luoShu.getRow2col2(),luoShu.getRow2col3(),
                    luoShu.getRow3col1(),luoShu.getRow3col2(),luoShu.getRow3col3());
            if(!calc.autoFill())
                throw new RuntimeException("输入的数字有误！");
            if("1".equals(typesubmit)){
                calc.rotate90();
            }
            arr=calc.getResult();

            luoShu.setRow1col1(arr[0][0]);
            luoShu.setRow1col2(arr[0][1]);
            luoShu.setRow1col3(arr[0][2]);
            luoShu.setRow2col1(arr[1][0]);
            luoShu.setRow2col2(arr[1][1]);
            luoShu.setRow2col3(arr[1][2]);
            luoShu.setRow3col1(arr[2][0]);
            luoShu.setRow3col2(arr[2][1]);
            luoShu.setRow3col3(arr[2][2]);

        }catch (Exception ex){
            msg=ex.getMessage();
        }


        model.addAttribute("luoshu",luoShu);
        model.addAttribute("msg",msg);
        model.addAttribute("typesubmit",typesubmit);
        return "calc/index";
    }
}
