package com.travel.fj.utils;

import com.travel.fj.domain.Attraction;
import com.travel.fj.domain.Guide;
import com.travel.fj.domain.Work;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class PicFileUtil {


    public static final String PATH="G:/Code/AttractionPic";
    //public static final String PATH="/root/AttractionPic";

    private static   String getNewName(MultipartFile file){
        String originalFilename=file.getOriginalFilename();
        int idx= originalFilename.lastIndexOf(".");
        String ext=originalFilename.substring(idx);
        UUID uuid = UUID.randomUUID();
        return uuid.toString()+ext;
    }

    public static String encodePicToBase64 (String[] fileList ) {

        StringBuffer encodePic=new StringBuffer();
        byte[] pic =null;
        for(String fileName:fileList) {
            String newPath = PATH +'/'+ fileName;
            File f = new File(newPath);
            try {
                FileInputStream fis = new FileInputStream(f);
                 pic = new byte[fis.available()];
                fis.read(pic);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            BASE64Encoder encoder = new BASE64Encoder();
            String singlEndodePic = encoder.encode(pic);
            encodePic.append(singlEndodePic).append("###");
        }
        encodePic.delete(encodePic.length()-3,encodePic.length());
        return encodePic.toString();
    }

    public static byte[] getFirstPic(String[] fList){
        String picPath=PATH+'/'+fList[0];
        byte[] pic=null;
        File f=new File(picPath);
        try {
            FileInputStream fis = new FileInputStream(f);
             pic = new byte[fis.available()];
            fis.read(pic);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return pic;
    }

    public static void saveAttracPic(MultipartFile[] picData, Attraction relatedAttrac ){
        StringBuffer sb=new StringBuffer();
        for(MultipartFile f:picData) {
            String newName=getNewName(f);
            sb.append(newName);
            sb.append("#");
            File dir = new File(PATH, newName);
            try {
                f.transferTo(dir);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        int idx=sb.lastIndexOf("#");
        sb.deleteCharAt(idx);
        relatedAttrac.setAttracPicFileList(sb.toString());
    }

    public static void saveWorkPic(MultipartFile[] picData, Work relatedWork ){
        StringBuffer sb=new StringBuffer();
        for(MultipartFile f:picData) {
            String newName=getNewName(f);
            sb.append(newName);
            sb.append("#");
            File dir = new File(PATH, newName);
            try {
                f.transferTo(dir);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        int idx=sb.lastIndexOf("#");
        sb.deleteCharAt(idx);
        relatedWork.setWorkPicList(sb.toString());
    }

    public static void saveGuidePic(MultipartFile[] picData, Guide relatedGuide ){
        StringBuffer sb=new StringBuffer();
        for(MultipartFile f:picData) {
            String newName=getNewName(f);
            sb.append(newName);
            sb.append("#");
            File dir = new File(PATH, newName);
            try {
                f.transferTo(dir);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        int idx=sb.lastIndexOf("#");
        sb.deleteCharAt(idx);
        relatedGuide.setGuidePicList(sb.toString());
    }
}
