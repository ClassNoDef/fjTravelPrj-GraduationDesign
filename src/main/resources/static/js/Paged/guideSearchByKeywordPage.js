function initR(title) {
    var qData={

        "queryGuideTitle":title,
        "currentPageNum":1

    };

    $.ajax({
            url : "/guide/searchGuide",
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
                        "<img src='/work/getWorkSinglePic/"+data.pageContent[i].guideId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<button class='ydc-actions-trigger'></button>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/guide/guideDetail/"+data.pageContent[i].guideId+"'>"+data.pageContent[i].guideTitle +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 景点："+data.pageContent[i].guideAttrac.attracName+"</span>"+
                        "<span> 浏览量："+data.pageContent[i].guideClickCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }
                $("#gspage").html(pstr)
                $("#result").html(str);
                $("#mainTitle").text("搜索结果")
                $("Title").text("指南搜索结果")
            }
        }
    );
}

function doSGQuery(title,pageNo) {
    var qData={

        "queryGuideTitle":title,
        "currentPageNum":pageNo

    };

    $.ajax({
            url : "/guide/searchGuide",
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
                        "<img src='/work/getWorkSinglePic/"+data.pageContent[i].guideId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<button class='ydc-actions-trigger'></button>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/guide/guideDetail/"+data.pageContent[i].guideId+"'>"+data.pageContent[i].guideTitle +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 景点："+data.pageContent[i].guideAttrac.attracName+"</span>"+
                        "<span> 浏览量："+data.pageContent[i].guideClickCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSGuery(\""+title+"\","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSGQuery(\""+title+"\","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }
                $("#gspage").html(pstr)
                $("#result").html(str);
                $("#mainTitle").text("搜索结果")
                $("Title").text("指南搜索结果")
            }
        }
    );
}