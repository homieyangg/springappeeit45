package com.eeit45team2.springappeeit45.controller;

import com.eeit45team2.springappeeit45.model.Member;
import com.eeit45team2.springappeeit45.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

@Controller
public class QueryMemberController {

    MemberService memberService;
    ServletContext ctx;

    @Autowired
    public QueryMemberController(MemberService memberService, ServletContext ctx) {
        this.memberService = memberService;
        this.ctx = ctx;
    }



    @GetMapping("/queryMembers")
    public String queryMembers(Model model){
        List<Member> members = memberService.getAllMembers();
        model.addAttribute(members);
        return "members";
    }

    @GetMapping("/crm/crm/picture/{id}")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Integer id) {
        ResponseEntity<byte[]> re = null;
        HttpHeaders headers = new HttpHeaders();

        Member bean = memberService.get(id);
        String filename = bean.getFileName();   // kitty.gif
        Blob blob = bean.getImage();
        String mimeType = ctx.getMimeType(filename);
        MediaType mediaType = MediaType.valueOf(mimeType);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = blob.getBinaryStream();
            byte[] b = new byte[81920];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            headers.setContentType(mediaType);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            re = new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }
}
