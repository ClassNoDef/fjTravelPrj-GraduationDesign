function initResult() {
    var pageNo=1+
    var qData={
        "queryAttracName":"",
        "queryAttracCity":"",
        "queryAttracAdd":"",
        "queryAttracType":"",
        "currentPageNum":1
    };
    $.ajax({
        url : "/attracMgr/loadAllAttractionsAdmin",
        type : "post",
        async : true,
        data :qData,
        dataType : 'json',
        success : function(data) {
            console.log(data)
            var prep=data.pageNo-1;
            var nextp=data.pageNo+1;
            $("#tableresult").html(" ")
            var str="";
            for(var i=0;i<data.pageContent.length; i++){
                var stat="";
                if(data.pageContent[i].isBlocked==0) {
                    stat = "正常";
                }
                else {
                    stat = "屏蔽";
                }

                str += "<tr>" +
                    "<td align='center'>" + data.pageContent[i].attracId + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracName + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracCity.cityName + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracNoteCount + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracGuideCount + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracType.attracTypeName + "</td>" +
                    "<td align='center'>" + stat + "</td>";
                if(data.pageContent[i].isBlocked==0) {
                    str+="<td align='center'><a class='am-btn am-btn-success' href='/guideMgr/createGuide/" + data.pageContent[i].attracId + "'> 创建指南</a></td>" +
                    "<td align='center'><a class='am-btn am-btn-success' href='/attracMgr/attractionDetail/" + data.pageContent[i].attracId + "'> 详细信息</a></td>" +
                    "<td align='center'><a class='am-btn am-btn-primary' href='/attracMgr/updateAttraction/" + data.pageContent[i].attracId + "'> 更新数据</a></td>";
                }
                if(data.pageContent[i].isBlocked==0)
                    str+="<td align='center'><button    onclick='deleteAttrac("+data.pageContent[i].attracId+","+pageNo+")'> 删除景点</button></td>";
                if(data.pageContent[i].isBlocked==1)
                    str+="<td align='center'><button    onclick='recoveryAttrac("+data.pageContent[i].attracId+","+pageNo+")'> 恢复景点</button></td>";

                    str+="</tr>";
            }
            $("#tableresult").html(str)

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }

            if(data.totalPageNum!==0) {
                pstr += " 到<input type=\"number\" class=\"text-center\" id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t";
            }
            $("#page").html(pstr)
        }
    });
}

/*当只点击btn时的组合与分页*/

function f() {
    
}

function doQuery(pageNo) {



    var query={
        "queryAttracName":$("#qname").val(),
        "queryAttracCity":$("#qcity").val(),
        "queryAttracAdd":$("#qadd").val(),
        "queryAttracType":$("#qtype").val(),
        "currentPageNum":pageNo
    };

    /*isNaN用于防止空输入直接提交*/
    if((pageNo<=0||isNaN(pageNo))){

        alert("输入的页码有误！")
        return ;
    }

    $.ajax({
        url : "/attracMgr/loadAllAttractionsAdmin",
        type : "post",
        async : true,
        data:query,
        dataType : 'json',
        success : function(data) {

            /*右侧条件解决在查询无结果时alert而不显示空表格的问题*/
            if((pageNo>data.totalPageNum)&&(data.totalPageNum!=0)){

                alert("输入的页码有误！")
                return ;
            }
            $("#tableresult").html(" ")
            $("#page").html(" ")
            var str="";
            for(var i=0;i<data.pageContent.length; i++){
                //console.log(data.pageSize)

                var stat="";
                if(data.pageContent[i].isBlocked==0) {
                    stat = "正常";
                }
                else {
                    stat = "屏蔽";
                }
                str += "<tr>" +
                    "<td align='center'>" + data.pageContent[i].attracId + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracName + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracCity.cityName + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracNoteCount + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracGuideCount + "</td>" +
                    "<td align='center'>" + data.pageContent[i].attracType.attracTypeName + "</td>" +
                    "<td align='center'>" + stat + "</td>";
                if(data.pageContent[i].isBlocked==0) {
                    str+="<td align='center'><a class='am-btn am-btn-success' href='/guideMgr/createGuide/" + data.pageContent[i].attracId + "'> 创建指南</a></td>" +
                        "<td align='center'><a class='am-btn am-btn-success' href='/attracMgr/attractionDetail/" + data.pageContent[i].attracId + "'> 详细信息</a></td>" +
                        "<td align='center'><a class='am-btn am-btn-primary' href='/attracMgr/updateAttraction/" + data.pageContent[i].attracId + "'> 更新数据</a></td>";
                }
                    if(data.pageContent[i].isBlocked==0)
                        str+="<td align='center'><button    onclick='deleteAttrac("+data.pageContent[i].attracId+","+pageNo+")'> 删除景点</button></td>";
                    if(data.pageContent[i].isBlocked==1)
                        str+="<td align='center'><button    onclick='recoveryAttrac("+data.pageContent[i].attracId+","+pageNo+")'> 恢复景点</button></td>";

                    str+="</tr>";
            }
            $("#tableresult").html(str)

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }

            /*有结果才显示页面输入框*/
            if(data.totalPageNum!==0) {
                pstr += " 到<input type=\"number\" class=\"text-center\" id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t"
            }
            $("#page").html(pstr)


        }
    });
}