
function adminNameCheck() {
    var name=$("#adminName").val();
    if(name===null||name==="") {
        $("#errmsg").text("用户名不允许为空！")
        return false

    }
    else {
        $("#errmsg").text(" ")
        return true
    }
}
function adminPwdCheck() {
    var pwd=$("#adminPwd").val();
    if(pwd===null||pwd==="") {
        $("#errmsg").text("密码不允许为空！")
        return false
    }
    else {
        $("#errmsg").text(" ")
        return true
    }
}
function checkVeriCode() {
    var stat=false
    var info=2333333
    var  code=$("#subVericode").val();
    console.log(code)
    $.ajax({
        url:"/admin/vericodeCheck?subVeriCode="+code,
        type:"get",
        data:code,
        async:false,
        success:function(data,event){
            console.log(data)
            stat=data.meta.success
            info=data.meta.message
            if(stat===true){
                $("#veriCodeErr").text(" ")

            }
            else
            {
                $("#veriCodeErr").text(info)
                console.log(info)


            }
        }
    });
    if(stat) return true;
    else return false
}

function submitForm(){
    var  checkAdminNameStat=adminNameCheck()
    var  checkAdminPwdStat=adminPwdCheck()
    var  checkVeriStat=checkVeriCode()
    var name=$("#adminName").val();
    var pwd=$("#adminPwd")
    if((checkAdminNameStat)&&(checkAdminPwdStat)&&(checkVeriStat)){
        console.log("ppppp")
        $.ajax({
            url:"/admin/login2",
            type:"post",
            data:{
                'adminName':name,
                'adminPwd':pwd,
            },
            dataType:"json",
            async:false,
            success:function(data,event){
                console.log(data)
                stat=data.meta.success
                info=data.meta.message
                if(stat===true){
                    location.href("/admin/adminCenter")

                }
                else
                {
                    $("#errmsg").text(info)
                    console.log(info)


                }
            }
        });
    }
        //$("#adminLoginForm").submit();
    else if (!checkAdminNameStat)
        alert("您输入的管理员名不能为空！")
    else if(!checkAdminPwdStat)
        alert("您输入的密码不能为空！")
    else if(!checkVeriStat)
        alert("您输入的验证码有误！")
}
