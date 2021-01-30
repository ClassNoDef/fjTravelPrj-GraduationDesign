package com.travel.fj.controller;

import com.travel.fj.domain.City;
import com.travel.fj.service.CityService;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("cityMgr")
public class CityContraller {

    @Autowired
    CityService cityService;

    @GetMapping("createCity")
    public String toCreateCity(Model model){

        model.addAttribute("newCity",new City());
        return "city_page/city_create_new_city";
    }

    @PostMapping("createCity")
    public String createCity(City city, Model model, MultipartFile citypic){

        try {
            city.setCityPic(citypic.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            cityService.createCity(city);
        }catch(Exception e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("newCity",new City());
            return "city_page/city_create_new_city";
        }
        return  "redirect:/cityMgr/loadCitys";
    }

    @RequestMapping("loadCitys")
    public String loadCitys(Model model){
        model.addAttribute("cityList",cityService.loadAllCity());
        return "city_page/city_city_mgr_page";
    }

    @PostMapping("loadCitysAjax")
    @ResponseBody
    public List<City> loadCityAjax(){
        List<City> result=cityService.loadAllCity();
        return result;
    }

    @GetMapping("updateCity/{cityId}")
    public String toUpdateCity(Model model, @PathVariable String cityId){

        City c=cityService.getCityById(Integer.parseInt(cityId));

        if(c!=null) {
            model.addAttribute("preUpdateCity", c);
            return "city_page/city_update_city_page";
        }
        else
            return "city_page/city_access_error_page";
    }

    @PostMapping("updateCity")
    public String updateCity(Model model,City city,MultipartFile citypic){
        if(!citypic.isEmpty()){
            try {
                city.setCityPic(citypic.getBytes());
            }
            catch (IOException e){
                    e.printStackTrace();
                }
        }
        else{
            byte[] oldPic=cityService.getCityPic(city.getCityId());
            if(oldPic!=null) {
                city.setCityPic(oldPic);
            }
        }
        try {
            cityService.updateCity(city);
        }catch(Exception e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("preUpdateCity", cityService.getCityById(city.getCityId()));
            return "city_page/city_update_city_page";
        }
        return  "redirect:/cityMgr/loadCitys";
    }

    @RequestMapping(value="deleteCity/{cityId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo deleteCity(@PathVariable  String cityId){

        try {
            cityService.deleteCity(Integer.parseInt(cityId));
        }catch (Exception e){
            return new ResultInfo(false,e.getMessage(),null);
        }
        return new ResultInfo(true,"删除成功",null);
    }

    @RequestMapping(value="getCityPicAjax/{cityId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo getCityPicAjax(@PathVariable String cityId){
        Integer id=Integer.parseInt(cityId);
        BASE64Encoder base64Encoder=new BASE64Encoder();
        byte[] pic=cityService.getCityPic(id);
        if(pic!=null) {
            String base64str = base64Encoder.encode(pic);
            return  new ResultInfo(true,"获取成功",base64str);
        }
        else
            return  new ResultInfo(false,"获取失败",null);

    }
    @RequestMapping(value="getCityPic/{cityName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getCityPic(@PathVariable String cityName){

        City c=cityService.getCityByName(cityName);
        if(c!=null) {
            Integer id = c.getCityId();
            byte[] pic = cityService.getCityPic(id);
            if (pic != null) {

                return pic;
            }
            else
                return null;
        }
        else
            return  null;

    }

}
