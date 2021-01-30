/*状态检查应该直接返回布尔值
* */

function userNameCheck() {
    var name=$("#userName").val();
    if(name===null||name==="") {
        $("#errmsg").text("用户名不允许为空！")
        return false
    }
    else {
        $("#errmsg").text(" ")
        return true
    }
}
function userPwdCheck() {
    var name=$("#userPwd").val();
    if(name===null||name==="") {
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
        url:"/user/vericodeCheck?subVeriCode="+code,
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
/*提交表单时应该再调用函数，而不是利用状态值，因为在回填时也许没有触发函数执行，
特别是使用onblur或onchange时*/
function submitForm(){

    var  checkUserNameStat=userNameCheck()
    var  checkUserPwdStat=userPwdCheck()
    var  checkVeriStat=checkVeriCode()
    if((checkUserNameStat)&&(checkUserPwdStat)&&(checkVeriStat))
        $("#userLoginForm").submit();
    else if (!checkUserNameStat)
        alert("您输入的用户名不能为空！")
    else if(!checkUserPwdStat)
        alert("您输入的密码不能为空！")
    else if(!checkVeriStat)
        alert("您输入的验证码有误！")
}
