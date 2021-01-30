function randomAttracReflush(session) {
    $.ajax({
            url: "/attrac/randomAttracAjax",
            type: "get",
            data: null,
            //async:false,
            success: function (data) {
                $("#randomAttrac").html(" ")
                //console.log(data)
                for (var i = 0; i < data.length; i++) {
                    var str="";
                     str+= "<li class='tututu'>"+
                    "<img src='/attrac/getAttracSinglePic/"+data[i].attracId+"'   />"+
                        "<a href='/attrac/attracDetail/"+data[i].attracId+"'>"+
                        "<span>"+data[i].attracName+"</span></a>";
                    if(session) {
                        str+="<button ><a href='/work/createWork/"+data[i].attracId+"' >为此景点书写游记</a> </button></li>"
                    }
                    else;
                    $("#randomAttrac").append(str);

                }
            }
        }
    );
}


function randomWorkReflush() {
    $.ajax({
            url: "/work/randomWorkAjax",
            type: "get",
            data: null,
            //async:false,
            success: function (data) {
                $("#randomWork").html(" ")
                //console.log(data)
                for (var i = 0; i < data.length; i++) {

                    $("#randomWork").append
                    ( "<li >"+
                        "<a href='/work/workDetail/"+data[i].workId+"'>"+
                        "<img src='/work/getWorkSinglePic/"+data[i].workId+"'  />"+
                        "<div class='youhuitu-in'>"+
                        "<div class='youhuitu-inl'>游记</div>"+
                        "<div class='youhuitu-inr' >"+data[i].workTitle+"</div>"+
                        "<div class='xiangxi'>"+
                        "<span>作者："+data[i].workAuthor.userName+" | 发表时间："+data[i].workCreateTime+"</span>&nbsp;"+
                        "<span>描述景点：："+data[i].workAttrac.attracName+" | 类型："+data[i].workType.noteTypeName+"</span>&nbsp;"+
                        "</div>"+
                        "</div>"+
                        "</a>"+
                       "</li>"
                    )
                }
            }
        }
    );
}

function randomGuideReflush() {
    $.ajax({
            url: "/guide/randomGuideAjax",
            type: "get",
            data: null,
            //async:false,
            success: function (data) {
                $("#randomGuide").html(" ")
                //console.log(data)
                for (var i = 0; i < data.length; i++) {

                    $("#randomGuide").append
                    ( "<li >"+
                        "<a href='/guide/guideDetail/"+data[i].guideId+"'>"+
                        "<img src='/guide/getGuideSinglePic/"+data[i].guideId+"'  />"+
                        "<div class='youhuitu-in'>"+
                        "<div class='youhuitu-inl'>指南</div>"+
                        "<div class='youhuitu-inr' >"+data[i].guideTitle+"</div>"+
                        "<div class='xiangxi'>"+
                        "<span>发表时间："+data[i].guideCreateTime+"</span>&nbsp;"+
                        "<span>描述景点：："+data[i].guideAttrac.attracName+" | 类型："+data[i].guideType.guideTypeName+"</span>&nbsp;"+
                        "</div>"+
                        "</div>"+
                        "</a>"+
                        "</li>"
                    )
                }
            }
        }
    );
}