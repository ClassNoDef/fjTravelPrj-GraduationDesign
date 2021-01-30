function init() {
    var gqData={
        "queryGuideTitle":"",
        // queryWorkAuthorId;
        "queryGuideType":"",
        // queryWorkAttracId;
        "queryGuideStatus":"",
        "currentPageNum":1
    }

    $.ajax({
        url: "/guideMgr/loadAllGuidesAjax",
        type: "post",
        async: true,
        data: gqData,
        dataType: 'json',
        success: function (data) {
            $("#gList").html(" ")
            for(var i=0;i<data.pageContent.length;i++){

                var stat="";
                if(data.pageContent[i].guideStatus==0){
                    stat="未审核"
                }
                else if(data.pageContent[i].guideStatus==1){
                    stat="已上架"
                }
                else if(data.pageContent[i].guideStatus==2){
                    stat="已屏蔽"
                }
                else{
                    stat="系统错误"
                }

                var isblocked=""
                if(data.pageContent[i].guideAttrac.isBlocked==1){
                    isblocked="(景点已屏蔽)"
                }

                var str="";

                str+=   "<tr>" +
                    "<td>"+data.pageContent[i].guideId+"</td>"+
                    "<td>"+data.pageContent[i].guideTitle+"</td>"+
                    "<td>"+data.pageContent[i].guideAuthor.adminName+"</td>"+
                    "<td>"+data.pageContent[i].guideCreateTime+"</td>"+
                    "<td>"+data.pageContent[i].guideType.guideTypeName+"</td>"+
                    "<td>"+data.pageContent[i].guideAttrac.attracName+isblocked+"</td>"+
                    "<td>"+stat+"</td>";
                if(data.pageContent[i].guideAttrac.isBlocked==0) {
                    str+= "<td><a class='am-btn am-btn-primary' href='/guideMgr/changeGuideStatus/" + data.pageContent[i].guideId + "'>审核</a></td>"+

                        "<td><a class='am-btn am-btn-primary' href='/guideMgr/updateGuide/" + data.pageContent[i].guideId + "'>更新内容</a></td>" ;
                }
                else;
                str+="</tr>"
                $("#gList").append(str);

            }

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doGQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }

            if(data.totalPageNum!==0) {
                pstr +=
                    "到<input type=\"number\" class=\"text-center\"  id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doGQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t"
                ;
            }
            $("#gpage").html(pstr)
        }
    })

}

function doGQuery(pageNo) {
    var gqData={
        "queryGuideTitle":$("#gqname").val(),
        // queryWorkAuthorId;
        "queryGuideType":$("#gqtype").val(),
        // queryWorkAttracId;
        "queryGuideStatus":$("#gqstatus").val(),
        "currentPageNum":pageNo
    }

    /*isNaN用于防止空输入直接提交*/
    if((pageNo<=0||isNaN(pageNo))){

        alert("输入的页码有误！")
        return ;
    }

    $.ajax({
        url: "/guideMgr/loadAllGuidesAjax",
        type: "post",
        async: true,
        data: gqData,
        dataType: 'json',
        success: function (data) {

            console.log(data)

            /*右侧条件解决在查询无结果时alert而不显示空表格的问题*/
            if((pageNo>data.totalPageNum)&&(data.totalPageNum!=0)){

                alert("输入的页码有误！")
                return ;
            }
            $("#gList").html(" ")
            for(var i=0;i<data.pageContent.length;i++){
                var stat="";
                if(data.pageContent[i].guideStatus==0){
                    stat="未审核"
                }
                else if(data.pageContent[i].guideStatus==1){
                    stat="已上架"
                }
                else if(data.pageContent[i].guideStatus==2){
                    stat="已屏蔽"
                }
                else{
                    stat="系统错误"
                }
                var isblocked=""
                if(data.pageContent[i].guideAttrac.isBlocked==1){
                    isblocked="(景点已屏蔽)"
                }

               var str="";

                 str+=   "<tr>" +
                    "<td>"+data.pageContent[i].guideId+"</td>"+
                    "<td>"+data.pageContent[i].guideTitle+"</td>"+
                    "<td>"+data.pageContent[i].guideAuthor.adminName+"</td>"+
                    "<td>"+data.pageContent[i].guideCreateTime+"</td>"+
                    "<td>"+data.pageContent[i].guideType.guideTypeName+"</td>"+
                    "<td>"+data.pageContent[i].guideAttrac.attracName+isblocked+"</td>"+
                    "<td>"+stat+"</td>";
                    if(data.pageContent[i].guideAttrac.isBlocked==0) {
                       str+= "<td><a class='am-btn am-btn-primary' href='/guideMgr/changeGuideStatus/" + data.pageContent[i].guideId + "'>审核</a></td>"+

                        "<td><a class='am-btn am-btn-primary' href='/guideMgr/updateGuide/" + data.pageContent[i].guideId + "'>更新内容</a></td>" ;
                    }
                    else;
                    str+="</tr>"
                $("#gList").append(str);

            }

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doGQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doGQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }

            if(data.totalPageNum!==0) {
                pstr +=
                    "到<input type=\"number\" class=\"text-center\"  id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doGQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t"
                ;
            }
            $("#gpage").html(pstr)
        }
    })
}