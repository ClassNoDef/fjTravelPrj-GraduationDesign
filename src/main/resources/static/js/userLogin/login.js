
function emailLogin() {
    var emailLoginHtml="";

    $("#dymForm").html(" ")
    $("#elogin").css("display","none");
    $("#nlogin").css("display","");
    $("#plogin").css("display","");
    emailLoginHtml+=
        "<input type='hidden' name='loginType' value='e'>"+
        "<dd class='user_icon'>"+
        "<input type='email' placeholder='邮箱' class='login_txtbx' id='userEmail' name='userEmail' onblur='userEmailCheck()'  />"+
       "</dd>"+
        "<p id='errmsgU'></p>"+
        "<dd class='pwd_icon'>"+
        "<input type='password' placeholder='密码' class='login_txtbx' id='userPwd' name='userPwd' onblur='userPwdCheck()' />"+
        "</dd>"+
        "<p id='errmsgP'></p>"+
        "<dd class='val_icon'>"+
        "<div class='checkcode'>"+
        "<input type='text' id='veriCode'  class='login_txtbx' placeholder='验证码(不区分大小写)' onblur='checkVeriCode()' >"+
        "</div>"+
        "<img  src='/user/vericode' onclick='changeVeriCode(this)' width='112px'>"+
        "</dd>"+
        "<p id='veriCodeErr'></p>"
        ;
    $("#dymForm").html(emailLoginHtml)
}

function nameLogin() {
    var nameLoginHtml="";

    $("#dymForm").html(" ")
    $("#nlogin").css("display","none");
    $("#elogin").css("display","");
    $("#plogin").css("display","");
    nameLoginHtml+=

        "<input type='hidden' name='loginType' value='n'>"+
        "<dd class='user_icon'>"+
        "<input type='text' placeholder='用户名' class='login_txtbx' id='userName' name='userName' onblur='userNameCheck()'  />"+
        "</dd>"+
        "<p id='errmsgU'></p>"+
        "<dd class='pwd_icon'>"+
        "<input type='password' placeholder='密码' class='login_txtbx' id='userPwd' name='userPwd' onblur='userPwdCheck()' />"+
        "</dd>"+
        "<p id='errmsgP'></p>"+
        "<dd class='val_icon'>"+
        "<div class='checkcode'>"+
        "<input type='text' id='veriCode'  class='login_txtbx' placeholder='验证码(不区分大小写)' onblur='checkVeriCode()'  >"+
        "</div>"+
        "<img  src='/user/vericode' onclick='changeVeriCode(this)' width='112px'>"+
        "</dd>"+
        "<p id='veriCodeErr'></p>"

    $("#dymForm").html(nameLoginHtml)
}

function phoneLogin() {
    var phoneLoginHtml="";
    $("#plogin").css("display","none");
    $("#nlogin").css("display","");
    $("#elogin").css("display","");
    $("#dymForm").html(" ")
    phoneLoginHtml+=
        "<input type='hidden' name='loginType' value='p'>"+
        "<dd class='user_icon'>"+
        "<input type='number' placeholder='手机号' class='login_txtbx' id='userPhone' name='userPhone' onblur='userPhoneCheck()'  />"+
        "</dd>"+
        "<p id='errmsgU'></p>"+
        "<dd class='pwd_icon'>"+
        "<input type='password' placeholder='密码' class='login_txtbx' id='userPwd' name='userPwd' onblur='userPwdCheck()' />"+
        "</dd>"+
        "<p id='errmsgP'></p>"+
        "<dd class='val_icon'>"+
        "<div class='checkcode'>"+
        "<input type='text' id='veriCode'  class='login_txtbx' placeholder='验证码(不区分大小写)' onblur='checkVeriCode()' >"+
        "</div>"+
        "<img  src='/user/vericode' onclick='changeVeriCode(this)' width='112px'>"+
        "</dd>"+
        "<p id='veriCodeErr'></p>"

    $("#dymForm").html(phoneLoginHtml)
}

