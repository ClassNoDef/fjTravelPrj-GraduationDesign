function initResult() {
    var wqData={
        "queryWorkTitle":"",
        "queryWorkType":"",
        "queryWorkStatus":"",
        "currentPageNum":1
    }

    $.ajax({
        url: "/workMgr/loadAllWorksAjax",
        type: "post",
        async: true,
        data: wqData,
        dataType: 'json',
        success: function (data) {
            $("#wList").html(" ")
            var wstr="";
            for(var i=0;i<data.pageContent.length;i++){
                var stat="";
                if(data.pageContent[i].workStatus==0){
                    stat="未审核"
                }
                else if(data.pageContent[i].workStatus==1){
                    stat="已审核"
                }
                else if(data.pageContent[i].workStatus==2){
                    stat="已屏蔽"
                }
                else if(data.pageContent[i].workStatus==3){
                    stat="用户已删除"
                }
                else{
                    stat="系统错误"
                }
                wstr+="<tr>" +
                    "<td>"+data.pageContent[i].workId+"</td>"+
                    "<td>"+data.pageContent[i].workTitle+"</td>"+
                    "<td>"+data.pageContent[i].workAuthor.userName+"</td>"+
                    "<td>"+data.pageContent[i].workCreateTime+"</td>"+
                    "<td>"+data.pageContent[i].workType.noteTypeName+"</td>"+
                    "<td>"+data.pageContent[i].workAttrac.attracName+"</td>"+
                    "<td>"+stat+"</td>"+
                    "<td><a class='am-btn am-btn-primary' href='/workMgr/workCheck/"+data.pageContent[i].workId+"'>审核</a></td>"+
                    "</tr>"

            }
                $("#wList").html(wstr)

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doWQuery(1)'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }

                if(data.totalPageNum!==0) {
                pstr +=
                    "到<input type=\"number\" class=\"text-center\"  id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doWQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t"
                    ;
                }
                $("#wpage").html(pstr)



        }
    })
}

function doWQuery(pageNo) {
    var wquery={
        "queryWorkTitle":$("#wqname").val(),
        // queryWorkAuthorId;
        "queryWorkType":$("#wqtype").val(),
        // queryWorkAttracId;
        "queryWorkStatus":$("#wqstatus").val(),
        "currentPageNum":pageNo

    }

    /*isNaN用于防止空输入直接提交*/
    if((pageNo<=0||isNaN(pageNo))){

        alert("输入的页码有误！")
        return ;
    }

    $.ajax({
        url: "/workMgr/loadAllWorksAjax",
        type: "post",
        async: true,
        data: wquery,
        dataType: 'json',
        success: function (data) {

            /*右侧条件解决在查询无结果时alert而不显示空表格的问题*/
            if((pageNo>data.totalPageNum)&&(data.totalPageNum!=0)){

                alert("输入的页码有误！")
                return ;
            }
            $("#wList").html(" ")
            $("#page").html(" ")
            console.log(data)

            var wstr="";
            for(var i=0;i<data.pageContent.length;i++){

                var stat="";
                if(data.pageContent[i].workStatus==0){
                    stat="未审核"
                }
                else if(data.pageContent[i].workStatus==1){
                    stat="已审核"
                }
                else if(data.pageContent[i].workStatus==2){
                    stat="已屏蔽"
                }
                else if(data.pageContent[i].workStatus==3){
                    stat="用户已删除"
                }
                else{
                    stat="系统错误"
                }
                wstr+="<tr>" +
                    "<td>"+data.pageContent[i].workId+"</td>"+
                    "<td>"+data.pageContent[i].workTitle+"</td>"+
                    "<td>"+data.pageContent[i].workAuthor.userName+"</td>"+
                    "<td>"+data.pageContent[i].workCreateTime+"</td>"+
                    "<td>"+data.pageContent[i].workType.noteTypeName+"</td>"+
                    "<td>"+data.pageContent[i].workAttrac.attracName+"</td>"+
                    "<td>"+stat+"</td>"+
                    "<td><a class='am-btn am-btn-primary' href='/workMgr/workCheck/"+data.pageContent[i].workId+"'>审核</a></td>"+
                    "</tr>"
            }
                $("#wList").html(wstr)

                var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
                if(data.pageNo>1){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doWQuery(1)'>首页</button>&nbsp;"
                }
                if(data.prePage==true){
                    pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
                }
                if(data.nextPage==true){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
                }
                if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                    pstr+="<button class='btn btn-sm btn-outline-info' onclick='doWQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
                }

            /*有结果才显示页面输入框*/
            if(data.totalPageNum!==0) {
                pstr += " 到<input type=\"number\" class=\"text-center\" id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doWQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t"
            }
                $("#wpage").html(pstr)
        }
    });
}