var validator;
var $jobAddForm = $("#job-add-form");
var issOnlineUrl = "http://127.0.0.1:22001/ZKBIOOnline";
(function($){
    $.fn.serializeObject=function(){
        var o={};
        var a=this.serializeArray();
        $.each(a, function() {
            if(o[this.name]){
                if(!o[this.name].push){
                    o[this.name]=[o[this.name]];
                }
                o[this.name].push(this.value||'');
            }else{
                o[this.name]=this.value||'';
            }
        });
        return o;
    };
})(jQuery);
$(function () {
    validateRule();
    $("input[name='date1']").daterangepicker(
        {
            singleDatePicker: true,//设置为单个的datepicker，而不是有区间的datepicker 默认false
            showDropdowns: true,//当设置值为true的时候，允许年份和月份通过下拉框的形式选择 默认false
            autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
            timePicker24Hour : true,//设置小时为24小时制 默认false
            timePicker : true,//可选中时分 默认false
            locale: {
                format: "YYYY-MM-DD HH:mm:ss",
                applyLabel: '确定',
                cancelLabel: '取消',
                separator: " - ",
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            }

        }
    ).on('cancel.daterangepicker', function(ev, picker) {
        $("#date1").val("请选择日期");
        $("#servingStartTime").val("");
    }).on('apply.daterangepicker', function(ev, picker) {
        $("#servingStartTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
        $("#date1").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
    });
    $("input[name='date2']").daterangepicker(
        {
            singleDatePicker: true,//设置为单个的datepicker，而不是有区间的datepicker 默认false
            showDropdowns: true,//当设置值为true的时候，允许年份和月份通过下拉框的形式选择 默认false
            autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
            timePicker24Hour : true,//设置小时为24小时制 默认false
            timePicker : true,//可选中时分 默认false
            locale: {
                format: "YYYY-MM-DD HH:mm:ss",
                applyLabel: '确定',
                cancelLabel: '取消',
                separator: " - ",
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            }

        }
    ).on('cancel.daterangepicker', function(ev, picker) {
        $("#date2").val("请选择日期");
        $("#servingEndTime").val("");
    }).on('apply.daterangepicker', function(ev, picker) {
        $("#servingEndTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
        $("#date2").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
    });


    $("#job-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $jobAddForm.validate();
        var flag = validator.form();
        var jsSerialize=$jobAddForm.serializeObject();
        console.log(jsSerialize)
        if (flag) {
            if (name === "save") {
                // $.post(ctx + "job/add", $jobAddForm.serialize(), function (r) {
                //     if (r.code === 0) {
                //         closeModal();
                //         refresh();
                //         $MB.n_success(r.msg);
                //     } else $MB.n_danger(r.msg);
                // });
                $.ajax({
                    type:"post",
                    url:ctx + "prisoner/add",
                    dataType:"json", //预期服务器返回数据的类型
                    data:jsSerialize,
                    success:function(r){
                       // window.location.reload()
                                closeModal();
                                refresh();
                                $MB.n_success(r.msg);
                },
                error:function(jqXHR){
                    alert("发生错误："+ jqXHR.status);
                }
            });

            }
            if (name === "update") {
                // $.post(ctx + "job/update", $jobAddForm.serialize(), function (r) {
                //     if (r.code === 0) {
                //         closeModal();
                //         refresh();
                //         $MB.n_success(r.msg);
                //     } else $MB.n_danger(r.msg);
                // });
                $.ajax({
                    type:"post",
                    url:ctx + "prisoner/update",
                    dataType:"json", //预期服务器返回数据的类型
                    data:jsSerialize,
                    success:function(r){
                        // window.location.reload()
                        $("input[name=id]").remove();
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    },
                    error:function(jqXHR){
                        alert("发生错误："+ jqXHR.status);
                    }
                });
            }
        }
    });

    $("#job-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#job-add-button").attr("name", "save");
    $MB.closeAndRestModal("job-add");
    validator.resetForm();
    $("#job-add-modal-title").html('新增任务');
}
var timing='';
function zwsb() {
    var skip=$('#skip').val();
    if(skip=='0'){//启动指纹识别
       // $('#hidezw1').show();
        $('.hidezw2').show();
        $('#zhiwMsg').hide()
        $.ajax({
            type:"get",
            url: "http://127.0.0.1:22001/zkbioonline/info",
            dataType:"json", //预期服务器返回数据的类型
            data:{},
            success:function(r){
        window.setTimeout(function(){
            $('#skip').val('1');
            $('#zhiwMsg').show();
            $('.hidezw2').hide();
            $('#zhiwMsg').val('开始录入')
        },500);
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            $('#zhiwMsg').hide();
            $('#zhiwerror').show();
            }
        });

        // /*成功后--调试接口时可删除下面此部分*/
        // window.setTimeout(function(){
        //     $('#skip').val('1');
        //     $('#zhiwMsg').show();
        //     $('.hidezw2').hide();
        //     $('#zhiwMsg').val('开始录入')
        // },1000);

    }else if(skip=='1'){//开始识别
        $('#zhiwMsg').hide()
        $('.hidezw1').show();
        $('.hidezw2').show();
        $('.spmsg').html('正在录入中请稍候...')

        $.ajax({
            type:"get",
            url:issOnlineUrl+"/fingerprint/beginCapture?type=1&FakeFunOn=0",
            dataType:"json", //预期服务器返回数据的类型
            data:{},
            success:function(r){

          timing = window.setInterval(timingFunc,1000);
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            $('#zhiwMsg').hide();
            $('#zhiwerror').show();
            }
        });

        /*成功后--调试接口时可删除下面此部分*/
         // timing = window.setInterval(timingFunc,1000);
    }
}
function timingFunc() {
    console.log('请按三次设备，请稍候...')
    $.ajax({
        type:"get",
        url:issOnlineUrl+"/fingerprint/getTemplate",
        dataType:"json", //预期服务器返回数据的类型
        data:{},
        success:function(r) {
            if (r.ret=="0") {

            $("input[name=fingerprints]").val(r.data.template)
            window.clearInterval(timing);
            $('.hidezw1').hide();
            $('.hidezw2').show();
            $('.spmsg').html('已完成录入，正在断开设备...')
            closeShebei()
        }

        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
             window.clearInterval(timing);
        $('#zhiwMsg').hide();
        $('#zhiwerror').show();
        }
    });

    // /*成功后--调试接口时可删除下面此部分*/
    // window.clearInterval(timing);
    // $('.hidezw1').hide();
    // $('.hidezw2').show();
    // $('.spmsg').html('已完成录入，正在断开设备...')
    //      closeShebei()
}
function closeShebei() {
    $.ajax({
        type:"get",
        url:issOnlineUrl+"/fingerprint/cancelCapture",
        dataType:"json", //预期服务器返回数据的类型
        data:{},
        success:function(r){
            window.setTimeout(function(){
                $('.spmsg').html('录入完成')
            },500);
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        $('#zhiwMsg').hide();
        $('#zhiwerror').show();
        }
    });

    // /*成功后--调试接口时可删除下面此部分*/
    // window.setTimeout(function(){
    //     $('.spmsg').html('录入完成')
    // },1000);


}
//重新录入指纹**************发送接口错误时还原指纹录入重新使用
function chongzhiwen(){
    $('.hidezw1').hide();
    $('.hidezw2').hide();
    $('.spmsg').html('正在唤起设备中请稍候...')
    $('#zhiwMsg').show();
    $('#zhiwMsg').val('启动指纹识别')
    $('#skip').val('0');
}
function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $jobAddForm.validate({
        rules: {
            beanName: {
                required: true,
                maxlength: 100
            },
            methodName: {
                required: true,
                maxlength: 100
            },
            cronExpression: {
                required: true,
                maxlength: 100,
                remote: {
                    url: "job/checkCron",
                    type: "get",
                    dataType: "json",
                    data: {
                        cron: function () {
                            return $("input[name='cronExpression']").val().trim();
                        }
                    }
                }
            }
        },
        messages: {
            beanName: {
                required: icon + "请输入Bean名称",
                maxlength: icon + "长度不能超过100个字符"
            },
            methodName: {
                required: icon + "请输入方法名称",
                maxlength: icon + "长度不能超过100个字符"
            },
            cronExpression: {
                required: icon + "请输入cron表达式",
                maxlength: icon + "长度不能超过100个字符",
                remote: icon + "cron表达式不合法"
            }
        }
    });
}