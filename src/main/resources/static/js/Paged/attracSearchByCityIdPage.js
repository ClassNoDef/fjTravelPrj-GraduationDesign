function initR(cityId) {
    var qData={

        "queryAttracCity":cityId,
        "currentPageNum":1

    };

    $.ajax({
            url : "/attrac/searchAttrac",
            type : "post",
            async : true,

            data : qData,
            dataType : 'json',
            success : function(data) {
                console.log(data)
                $("#result").html(" ")
                var str="";
                for(var i=0;i<data.pageContent.length; i++){
                    str +=
                        "<div class='ydc-group-table-item'>"+
                        "<div class='ydc-group-table-item-img'>"+
                        "<img src='/attrac/getAttracSinglePic/"+data.pageContent[i].attracId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<a href='/attrac/attracDetail/"+data.pageContent[i].attracId+"'><button class='ydc-actions-trigger'>查看详情</button></a>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/attrac/attracDetail/"+data.pageContent[i].attracId+"'>"+data.pageContent[i].attracName +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 景点类型："+data.pageContent[i].attracType.attracTypeName+" |</span>"+
                        "<span> 浏览量："+data.pageContent[i].attracClickCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }

                $("#apage").html(pstr)
                //console.log(str);
                var tipsstr=" ";
                tipsstr+="<div class='ydc-group-table-item-text'>"+
                    "<span>"+
                    data.pageContent[0].attracCity.cityTips+
                    "</span>"+
                    "</div>"
                $("#bigCityName").text(data.pageContent[0].attracCity.cityName)
                $("#tips").html(tipsstr);
                $("#result").html(str);
                $("#mainTitle").text(data.pageContent[0].attracCity.cityName+"景点列表")
                $("#tipsTitle").text(data.pageContent[0].attracCity.cityName+"简介")
                $("Title").text(data.pageContent[0].attracCity.cityName+"--景点列表")
            }
        }
    );
}

function doAQueryC(cityId,pageNo) {
    var qData={

        "queryAttracCity":cityId,
        "currentPageNum":pageNo

    };

    $.ajax({
            url : "/attrac/searchAttrac",
            type : "post",
            async : true,

            data : qData,
            dataType : 'json',
            success : function(data) {
                console.log(data)
                $("#result").html(" ")
                var str="";
                for(var i=0;i<data.pageContent.length; i++){
                    str +=
                        "<div class='ydc-group-table-item'>"+
                        "<div class='ydc-group-table-item-img'>"+
                        "<img src='/attrac/getAttracSinglePic/"+data.pageContent[i].attracId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<a href='/attrac/attracDetail/"+data.pageContent[i].attracId+"'><button class='ydc-actions-trigger'>查看详情</button></a>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/attrac/attracDetail/"+data.pageContent[i].attracId+"'>"+data.pageContent[i].attracName +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 景点类型："+data.pageContent[i].attracType.attracTypeName+" |</span>"+
                        "<span> 浏览量："+data.pageContent[i].attracClickCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doAQueryC("+cityId+","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }

                $("#apage").html(pstr)
                //console.log(str);
                var tipsstr=" ";
                tipsstr+="<div class='ydc-group-table-item-text'>"+
                    "<span>"+
                    data.pageContent[0].attracCity.cityTips+
                    "</span>"+
                    "</div>"
                $("#bigCityName").text(data.pageContent[0].attracCity.cityName)
                $("#tips").html(tipsstr);
                $("#result").html(str);
                $("#mainTitle").text(data.pageContent[0].attracCity.cityName+"景点列表")
                $("#tipsTitle").text(data.pageContent[0].attracCity.cityName+"简介")
                $("Title").text(data.pageContent[0].attracCity.cityName+"--景点列表")
            }
        }
    );
}