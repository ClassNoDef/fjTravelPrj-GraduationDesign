function initR(title) {
    var qData={

        "queryWorkTitle":title,
        "currentPageNum":1

    };

    $.ajax({
            url : "/work/searchWork",
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
                        "<img src='/work/getWorkSinglePic/"+data.pageContent[i].workId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<button class='ydc-actions-trigger'></button>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/work/workDetail/"+data.pageContent[i].workId+"'>"+data.pageContent[i].workTitle +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 作者："+data.pageContent[i].workAuthor.userName+"</span>"+
                        "<span> 浏览量："+data.pageContent[i].workLikeCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }
                $("#wspage").html(pstr)
                $("#result").html(str);
                $("#mainTitle").text("搜索结果")
                $("Title").text("游记搜索结果")
            }
        }
    );
}

function doSWQuery(title,pageNo) {
    var qData={

        "queryWorkTitle":title,
        "currentPageNum":pageNo

    };

    $.ajax({
            url : "/work/searchWork",
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
                        "<img src='/work/getWorkSinglePic/"+data.pageContent[i].workId+"' >"+
                        "</div>"+
                        "<div class='ydc-actions'>"+
                        "<div>"+
                        "<button class='ydc-actions-trigger'></button>"+
                        "</div>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-text'>"+
                        "<span>"+
                        "<a href='/work/workDetail/"+data.pageContent[i].workId+"'>"+data.pageContent[i].workTitle +"</a>"+
                        "</span>"+
                        "</div>"+
                        "<div class='ydc-group-table-item-info'>"+
                        "<span> 作者："+data.pageContent[i].workAuthor.userName+"</span>"+
                        "<span> 浏览量："+data.pageContent[i].workLikeCount+"</span>"+
                        "</div>"+
                        "</div>";
                }

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+1+")'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doSWQuery(\""+title+"\","+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }
                $("#wspage").html(pstr)
                $("#result").html(str);
                $("#mainTitle").text("搜索结果")
                $("Title").text("游记搜索结果")
            }
        }
    );
}