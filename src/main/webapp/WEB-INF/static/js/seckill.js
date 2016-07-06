/**
 * Created by dello on 2016/7/6.
 */
//存放主要交互逻辑JS代码
//JavaScript模块化
var seckill={
    //封装秒杀相关ajax的地址
    URL:{
        now:function(){
            return '/seckill/time/now';
        }
    },
    validatePhone:function(phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        }
        else return false;
    },
    handlerSeckill:function(){
        //处理秒杀逻辑
        alert("start");
    },
    countdown:function(seckillId,nowTime,startTime,endTime){
        var seckiillbox=$("#seckill-box");
        if(nowTime>=endTime){
            //秒杀结束
            seckiillbox.html("秒杀结束")
        }else if(nowTime<startTime){

            var killTime=new Date(startTime+1000);
            seckiillbox.countdown(killTime,function(event){
                var format=event.strftime("秒杀倒计时：%D天 %H时 %M分 %S秒");
                seckiillbox.html(format);
            }).on("finsih.countdown",function(){
                //时间完成后的回掉函数
                //获取秒杀地址控制显示逻辑，执行秒杀操作
                seckill.handlerSeckill();
            });
        }else{
            //秒杀开始
            seckill.handlerSeckill();
        }
    },
    //详情秒杀逻辑
    deatil:{
        //详情页初始化
        init: function (params) {
            //用户手机验证
            //计时交互
            //在Coookie中查找手机号

            var killPhone= $.cookie("killphone");
            var startTime=params.startTime;
            var endTime=params.endTime;
            var seckillId=params.seckillId;

            //验证手机号
            if(seckill.validatePhone(killPhone)){

            }else{
                //绑定手机号码
                var killPhoneModal=$("#killPhoneModal");
                killPhoneModal.modal({
                    show:true,  //显示弹出层
                    backdrop:"static",//禁止位置关闭
                    keyboard:false   //键盘事件关闭
                });
            }
            $("#killPhoneBtn").click(function(){
                var inputPhone=$("#killPhoneKey").val();
                if(seckill.validatePhone(inputPhone)){
                    $.cookie("killphone",inputPhone,{expires:7,path:'/seckill'});//expires 7天 path 路径
                    //刷新页面
                    window.location.reload();
                }else {
                    $("#killPhoneMessage").hide().html("<lable class='label label-danger'>手机号码错误</lable>").show(300);
                }
            });
            //已经登陆成功
            //计时交互
            $.get(seckill.URL.now(),{},function(result){
                if(result&&result.success==true){
                    var nowTime=result['data'];
                    var nowDate=new Date(nowTime).getTime();
                    //时间判断
                    seckill.countdown(seckillId,nowDate,startTime,endTime);
                }else{
                    console.error(result);
                }
            });
        }
    },

};
