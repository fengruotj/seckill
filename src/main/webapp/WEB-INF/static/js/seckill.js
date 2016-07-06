/**
 * Created by dello on 2016/7/6.
 */
//存放主要交互逻辑JS代码
//JavaScript模块化
var seckill={
    //封装秒杀相关ajax的地址
    URL:{

    },
    validatePhone:function(phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        }
        else return false;
    }
    ,
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

        }
    },

};
