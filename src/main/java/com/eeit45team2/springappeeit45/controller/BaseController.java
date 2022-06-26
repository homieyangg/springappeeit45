package com.eeit45team2.springappeeit45.controller;

import com.eeit45team2.springappeeit45.model.Category;
import com.eeit45team2.springappeeit45.model.Hobby;
import com.eeit45team2.springappeeit45.model.Member;
import com.eeit45team2.springappeeit45.service.CategoryService;
import com.eeit45team2.springappeeit45.service.HobbyService;
import com.eeit45team2.springappeeit45.service.MemberService;
import com.eeit45team2.springappeeit45.validate.MemberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {

    private static Logger Log = LoggerFactory.getLogger(BaseController.class);
    MemberService memberService;
    CategoryService categoryService;
    HobbyService hobbyService;

    //    @Autowired
    public BaseController(MemberService memberService, CategoryService categoryService, HobbyService hobbyService) {
        this.memberService = memberService;
        this.categoryService = categoryService;
        this.hobbyService = hobbyService;
    }


    @GetMapping({"/hello", "/"})
    public String home(
            @RequestParam(value = "name", required = false) String visitor,
            @RequestParam(value = "score", required = false) Integer score,
            Model model) {
        String message = visitor != null ? visitor + " 您好," : " 訪客您好,";
        model.addAttribute("helloMessage", message);
        model.addAttribute("score", score);
        return "greeting";
    }

    @GetMapping("/crm/mem/{id}")
    public String getMemberForUpdate(Model model, @PathVariable Integer id) {
        Member member = memberService.get(id);
        model.addAttribute(member);
        return "updateMember";
    }

    @PostMapping("/crm/mem/{id}")
    public String updateMember(Member member, Model model, @PathVariable Integer id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                System.out.println("有錯誤：" + error);
            }
            return "insertMember";
        }
        memberService.update(member);
        return "updateMember";
    }

    @RequestMapping("/insertMemberForm")
    public String getMemberForm(Model model) {
        return "insertMember";
    }

    @PostMapping("/insertMember")
    public String saveMember(@ModelAttribute("memberBean") Member member, RedirectAttributes ra, BindingResult bindingResult, Model model) {
        //資料檢查----------
        List<ObjectError> list0 = bindingResult.getAllErrors();
        for (ObjectError error : list0) {
            System.out.println("有錯誤：" + error);
        }
        System.out.println("-------------------------------------");
        MemberValidator validator = new MemberValidator();
        validator.validate(member, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                System.out.println("有錯誤：" + error);
            }
            return "insertMember";
        }
        //----------------
        Category c = categoryService.getCategory(101);
        member.setCategory(c);
        Hobby h = hobbyService.getHobby(1);
        member.setHobby(h);
        //時間戳記----------------
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        member.setRegisterTime(ts);
        //----------圖片----------
        SerialBlob blob = null;
        try {
            MultipartFile productImage = member.getProductImage();
            InputStream is = productImage.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[8192];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            blob = new SerialBlob(baos.toByteArray());
            member.setFileName(productImage.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        member.setImage(blob);
        //-----------------------
        try {
            memberService.save(member);
            ra.addFlashAttribute("INSERT_SUCCESS", "新增成功");
            return "redirect:/";
        } catch (Exception e) {
            Log.error(e.getMessage());
            String sqlMsg = e.getMessage();
            String errorMsg = "";
            if (sqlMsg.indexOf("ConstraintViolationException") >= 0) {
                errorMsg = "帳戶重複";
            } else {
                errorMsg = "系統異常,請聯絡開發人員";
            }
            model.addAttribute("INSERT_ERROR", "新增失敗" + errorMsg);
            return "insertMember";
        }
    }

    @ModelAttribute("memberBean")
    public Member ma01(@PathVariable(required = false) Integer id) {
        Member member = null;
        if (id == null){
            member = new Member();
        }else {
            member = memberService.get(id);
        }
        return member;
    }

    @ModelAttribute
    public void ma02(Model model) {
        List<Category> cate = categoryService.getAllCategories();
        List<Hobby> hobb = hobbyService.getAllHobbies();
        model.addAttribute(cate);
        model.addAttribute(hobb);

        Map<String, String> map = new HashMap<>();
        map.put("F", "女");
        map.put("M", "男");
        model.addAttribute("genderMap", map);
    }

    @ModelAttribute
    public void ma03() {
        System.out.println("----------------ma03");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        /*  此方法可讓日期不填，默認會填入Null值，值得注意的是import class是java.util.date    */
        // java.util.Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        dateFormat.setLenient(false);
        CustomDateEditor ce = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, ce);
        // java.sql.Date
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2.setLenient(false);
        CustomDateEditor ce2 = new CustomDateEditor(dateFormat2, true);
        binder.registerCustomEditor(java.sql.Date.class, ce2);
    }
}

