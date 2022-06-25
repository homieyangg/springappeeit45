package com.eeit45team2.springappeeit45.controller;

import com.eeit45team2.springappeeit45.model.Category;
import com.eeit45team2.springappeeit45.model.Hobby;
import com.eeit45team2.springappeeit45.model.Member;
import com.eeit45team2.springappeeit45.service.CategoryService;
import com.eeit45team2.springappeeit45.service.HobbyService;
import com.eeit45team2.springappeeit45.service.MemberService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {

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
            @RequestParam(value = "score", required = false, defaultValue = "-1") Integer score,
            Model model) {
        String message = visitor != null ? visitor + " 您好," : " 訪客您好,";
        model.addAttribute("helloMessage", message);
        model.addAttribute("score", score);
        return "greeting";
    }

    @GetMapping("/insertMemberFrom")
    public String getMemberForm(Model model) {
        return "/insertMember";
    }

    @PostMapping("/insertMemberFrom")
    public String saveMember(Member member, RedirectAttributes ra) {
        ra.addFlashAttribute("INSERT_SUCCESS", "新增成功");
        Category c = categoryService.getCategory(101);
        member.setCategory(c);
        Hobby h = hobbyService.getHobby(1);
        member.setHobby(h);
        //時間戳記
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        member.setRegisterTime(ts);
        //----------圖片----------
        SerialBlob blob = null;
        try {
            blob = null;
            MultipartFile productImage = member.getProductImage();
            InputStream is = productImage.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[8192];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, 0);
            }
            blob = new SerialBlob(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //-----------------------
        member.setImage(blob);
        System.out.println(member);
        memberService.save(member);
        return "redirect:/";
    }

    @ModelAttribute("memberBean")
    public Member ma01() {
        System.out.println("----------------01");
        Member member = new Member();
        member.setName("MickeyA");
//      model.addAttribute("memberBean",member);
        return member;
    }

    @ModelAttribute
    public void ma02(Model model) {
        List<Category> cate = categoryService.getAllCategories();
        List<Hobby> hobb = hobbyService.getAllHobbies();
        model.addAttribute(cate);
        model.addAttribute(hobb);

        Map<String, String> map = new HashMap<>();
        map.put("F","女");
        map.put("M","男");
        model.addAttribute("genderMap",map);
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

