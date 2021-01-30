$(function(){
             
             
                var nowing = 0 
                var $imglis = $(".banner .list0 li")
                var $fadeCover = $(".fadeCover")
                var fadeIntime = 400
                var fadeOutime = 700
                var $circle = $(".circle li")

                    // 定义函数
                    function rightbtn(){
                        // 先信号量的改变，
                    
                    nowing++
                    if(nowing>$imglis.length-1){
                        nowing=0
                       }
                       // 再调用函数
                    dong();
                    }
                    // 定时器业务
                    var timer = setInterval(rightbtn,2000)
                    $(".banner").onmouseenter(function(){
                        clearInterval(timer)
                    
                    })
                    $(".banner").onmouseleave(function(){
                        clearInterval(timer)
                        timer = setInterval(rightbtn,2000)
                    })
                    // 点击小圆点业务
                    $circle.click(function(){
                        nowing = $(this).index()
                        dong()
                    })
                 function dong(){
                     // 先让fadecover淡入，让全局变白 
                  $fadeCover.fadeIn(fadeIntime,function(){
                    // ,function(){
                    // 回调函数中，就表示fadecover淡入后做的事情。
                    // 换图是干蹦的，不是淡入淡出的换,进行暗箱操作，兄弟去cur，自己加cur
                    $imglis.eq(nowing).addClass('cur').siblings().removeClass('cur')
                    // 淡出让黑布小事
                    $(this).fadeOut(fadeOutime)
                  })
                  $circle.eq(nowing).addClass("cur").siblings().removeClass("cur")
                

                 }
             

})