function initPage() {
    var uqData={
        "queryUserName":"",
        "queryUserSex":"",
        "queryUserStatus":"",
        "currentPageNum":1
    }

    $.ajax({
        url: "/admin/loadAllUserAjax",
        type: "post",
        async: true,
        data: uqData,
        dataType: 'json',
        success: function (data) {


            $("#uList").html(" ")
            var ustr="";
            for(var i=0;i<data.pageContent.length;i++){
                var stat="";
                var operStr="";
                if(data.pageContent[i].userStatus==0){
                    stat="正常";
                    operStr="<td><button  " +
                        "onclick='freezeUser("+data.pageContent[i].userId +","+1+")'>冻结</button></td>"
                }
                else if(data.pageContent[i].userStatus==1){
                    stat="冻结";
                    operStr="<td><button  " +
                        "onclick='unFreezeUser("+data.pageContent[i].userId +","+1+")'>解冻</button></td>"
                }
                else if(data.pageContent[i].userStatus==2){
                    stat="未开通";
                    operStr="<td><button  " +
                        "onclick='activeUser("+data.pageContent[i].userId +","+1+")'>开通</button></td>"
                }
                else{
                    stat="系统错误"
                }

                var sex=""
                if(data.pageContent[i].userSex=="M"){
                    sex="男"
                }
                else if(data.pageContent[i].userSex=="F"){
                    sex="女"
                }
                else{
                    sex="系统错误"
                }



                ustr+="<tr>" +
                    "<td>"+data.pageContent[i].userId+"</td>"+
                    "<td>"+data.pageContent[i].userName+"</td>"+
                    "<td>"+sex+"</td>"+
                    "<td>"+data.pageContent[i].userBirthday+"</td>"+
                    "<td>"+data.pageContent[i].userPhone+"</td>"+
                    "<td>"+data.pageContent[i].userEmail+"</td>"+
                    "<td>"+data.pageContent[i].userNote+"</td>"+
                    "<td>"+data.pageContent[i].noteCount+"</td>"+
                    "<td>"+stat+"</td>";
                ustr+=operStr;
                ustr+= "</tr>";

            }
            $("#uList").html(ustr)

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doUQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }

            if(data.totalPageNum!==0) {
                pstr += " 到<input type=\"number\" class=\"text-center\" id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doUQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t";
            }

            $("#upage").html(pstr)



        }
    })
}

function doUQuery(pageNo) {
    var uqData={
        "queryUserName":$("#uqname").val(),
        "queryUserSex":$("#uqsex").val(),
        "queryUserStatus":$("#uqstatus").val(),
        "currentPageNum":pageNo
    }

    /*isNaN用于防止空输入直接提交*/
    if((pageNo<=0||isNaN(pageNo))){

        alert("输入的页码有误！")
        return ;
    }

    $.ajax({
        url: "/admin/loadAllUserAjax",
        type: "post",
        async: true,
        data: uqData,
        dataType: 'json',
        success: function (data) {
            if((pageNo>data.totalPageNum)&&(data.totalPageNum!=0)){

                alert("输入的页码有误！")
                return ;
            }
            $("#uList").html(" ")
            console.log(data)
            var ustr="";
            for(var i=0;i<data.pageContent.length;i++){
                var stat="";
                var operStr="";
                if(data.pageContent[i].userStatus==0){
                    stat="正常";
                    operStr="<td><button  " +
                        "onclick='freezeUser("+data.pageContent[i].userId +","+pageNo+")'>冻结</button></td>"
                }
                else if(data.pageContent[i].userStatus==1){
                    stat="冻结";
                    operStr="<td><button  " +
                        "onclick='unFreezeUser("+data.pageContent[i].userId +","+pageNo+")'>解冻</button></td>"
                }
                else if(data.pageContent[i].userStatus==2){
                    stat="未开通";
                    operStr="<td><button  " +
                        "onclick='activeUser("+data.pageContent[i].userId +","+pageNo+")'>开通</button></td>"
                }
                else{
                    stat="系统错误"
                }

                var sex=""
                if(data.pageContent[i].userSex=="M"){
                    sex="男"
                }
                else if(data.pageContent[i].userSex=="F"){
                    sex="女"
                }
                else{
                    sex="系统错误"
                }



                ustr+="<tr>" +
                    "<td>"+data.pageContent[i].userId+"</td>"+
                    "<td>"+data.pageContent[i].userName+"</td>"+
                    "<td>"+sex+"</td>"+
                    "<td>"+data.pageContent[i].userBirthday+"</td>"+
                    "<td>"+data.pageContent[i].userPhone+"</td>"+
                    "<td>"+data.pageContent[i].userEmail+"</td>"+
                    "<td>"+data.pageContent[i].userNote+"</td>"+
                    "<td>"+data.pageContent[i].noteCount+"</td>"+
                    "<td>"+stat+"</td>";
                ustr+=operStr;
                ustr+= "</tr>";

            }
            $("#uList").html(ustr)

            var pstr="共"+data.totalRecNum+"条, 当前显示"+(data.startIndex+1)+"-"+data.endIndex+"条, 第"+data.pageNo+"/"+data.totalPageNum+"页";
            if(data.pageNo>1){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doUQuery(1)'>首页</button>&nbsp;"
            }
            if(data.prePage==true){
                pstr+=" <button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.pageNo-1)+")'>上一页</button>&nbsp;"
            }
            if(data.nextPage==true){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.pageNo+1)+")'>下一页</button>&nbsp;"
            }
            if((data.pageNo!=data.totalPageNum)&&(data.totalRecNum!==0)){
                pstr+="<button class='btn btn-sm btn-outline-info' onclick='doUQuery("+(data.totalPageNum)+")'>末页</button>&nbsp;"
            }
            if(data.totalPageNum!==0) {
                pstr += " 到<input type=\"number\" class=\"text-center\" id=\"pageNo\" size=4 style=\"text-align:right;\"/> 页\n" +
                    "\t           <button class=\"btn btn-sm btn-success\" onclick=\"doUQuery(parseInt($('#pageNo').val()));\"> 跳 转 </button>\t";
            }

            $("#upage").html(pstr)



        }
    })
}