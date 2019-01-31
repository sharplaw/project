package cc.mrbird.common.base64;

import cc.mrbird.common.Pic.PicBuffer;
import cc.mrbird.common.Pic.UploadImg;
import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.ResponseBo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class PictureController  extends BaseController {


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Log("上传图片")
    @RequestMapping("picture/upload")
    @ResponseBody
    public ResponseBo upload(String imgStr, String prisonerNo, String seet) {
        String a[]=  imgStr.split(",");
        String imgStrs= a[1];
        Date date = new Date();
        int month = date.getMonth();
        int months = month + 1;
        String mon = String.valueOf(months);
        String path = "E://" + prisonerNo + "//" + mon + "//" + prisonerNo + "-" + mon + "-" + seet + ".png";
        String filepath="E://" + prisonerNo + "//" + mon;
        boolean flag= Base.CreateImage(imgStrs, path,filepath);
        if(flag=true){
            return ResponseBo.ok(path);
        }else{
            return ResponseBo.error();
        }

    }
    @Log("上传指纹图片")
    @RequestMapping("picture/zwupload")
    @ResponseBody
    public ResponseBo zwupload(String imgStr, String prisonerNo) {
        boolean flag=false;
        Date date = new Date();
        int month = date.getMonth();
        int months = month + 1;
        String mon = String.valueOf(months);
        Base.CreateImage(imgStr,"E://"+prisonerNo+"//"+mon+"//fingerprint.png","E://"+prisonerNo+"//"+mon);
        PicBuffer.changeRGB("E://"+prisonerNo+"//"+mon+"//fingerprint.png");
        UploadImg UI = new UploadImg("E://"+prisonerNo+"//"+mon+"//fingerprint.png", "E://"+prisonerNo+"//"+mon, "fingerprint","png",150,200);
        String path="E://"+prisonerNo+"//"+mon+"//fingerprint.png";
        try {
             flag=   UI.createThumbnail();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(flag=true){
            return ResponseBo.ok(path);
        }else{
            return ResponseBo.error();
        }

    }

}
