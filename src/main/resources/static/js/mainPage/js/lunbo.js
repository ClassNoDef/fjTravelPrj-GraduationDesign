var tutu=document.getElementById("tutu")
            var btn1 = document.getElementById("btn1")
            var btn2 = document.getElementById("btn2")
            var tiaozhuan = document.getElementById("tiaozhuan")
            var text1 = document.getElementById("text1")
            var nowing = 5

            btn1.onclick=function(){//滞后验收，先让信号量减再验收，不合格在帮它修改
                nowing--
                if(nowing<1){//改信号量
                    nowing=9
                }
                huantu();//调用函数

            }
            btn2.onclick=function(){
                nowing++
                if(nowing>9){
                    nowing=1
                }
                huantu();
            }
            tiaozhuan.onclick =function(){
                linshi = parseInt(text1.value)
                if(linshi>=1 && linshi<=9){
                    nowing=linshi
                }
                huantu();

            }
            function huantu(){
                tutu.src = "images/"+nowing+".jpg"//自定义函数
            }
