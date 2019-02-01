var PrionerNo='',ID='',path='';
var issOnlineUrl = "http://127.0.0.1:22001/ZKBIOOnline";
function updateJob() {
    var selected = $("#jobTable").bootstrapTable("getSelections");
    console.log(selected)
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要处理的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能处理一个任务！');
        return;
    }
    var sels = selected[0];
    PrionerNo=sels.prisonerNo;
    ID=sels.id;
    if(sels.statue=='0'){
        $.ajax({
            type:"get",
            url:ctx + "task/selectSingle",
            dataType:"json", //预期服务器返回数据的类型
            data:{id:sels.id},
            success:function(r){
                console.log(r.msg[0].actiivityUrl)
                if(r.msg[0].taskType=='学习教育'){
                    $('#baodao').hide();
                    $('#xuexi').show();
                    $('#qingjia').hide();

                }else if(r.msg[0].taskType=='报道'){
                    $('#baodao').show();
                    $('#xuexi').hide();
                    $('#qingjia').hide();
                    $('#img1').attr("src","data:image/png;base64,"+r.msg[0].actiivityUrl+"")
                    $('#img2').attr("src","data:image/png;base64,"+r.msg[0].talkUrl+"")

                }else if(r.msg[0].taskType=='请假'){
                    $('#baodao').hide();
                    $('#xuexi').hide();
                    $('#qingjia').show();
                    $('#img3').attr("src","data:image/png;base64,"+r.msg[0].freedayUrl+"")

                }
                var $form = $('#job-add');
                $form.modal();

            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });

    }else{
        $.ajax({
            type:"get",
            url:ctx + "task/selectSingle",
            dataType:"json", //预期服务器返回数据的类型
            data:{id:sels.id},
            success:function(r){
                // console.log(r.msg[0].actiivityUrl)
                // $('.img1').attr("src","data:image/png;base64,"+r.msg[0].actiivityUrl+"")
                // $('.img2').attr("src","data:image/png;base64,"+r.msg[0].talkUrl+"")
                // var $form = $('#job-add1');
                // $form.modal();
                console.log(r.msg[0].actiivityUrl)
                if(r.msg[0].taskType=='学习教育'){
                    $('.baodao').hide();
                    $('.xuexi').show();
                    $('.qingjia').hide();

                }else if(r.msg[0].taskType=='报道'){
                    $('.baodao').show();
                    $('.xuexi').hide();
                    $('.qingjia').hide();
                    $('.img1').attr("src","data:image/png;base64,"+r.msg[0].actiivityUrl+"")
                    $('.img2').attr("src","data:image/png;base64,"+r.msg[0].talkUrl+"")

                }else if(r.msg[0].taskType=='请假'){
                    $('.baodao').hide();
                    $('.xuexi').hide();
                    $('.qingjia').show();
                    $('.img3').attr("src","data:image/png;base64,"+r.msg[0].freedayUrl+"")

                }
                var $form = $('#job-add1');
                $form.modal();
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });
        // $('.img1').attr("src",sels.fingerUrl)
        // $('.img2').attr("src",sels.talkUrl)
        // var $form = $('#job-add1');
        // $form.modal();
    }



}
function search() {
    $MB.refreshTable('jobTable');
}
function refresh() {
   // $(".job-table-form")[0].reset();
    search();
}

$(function () {

    $.ajax({
        type:"get",
        url:ctx + "user/checklist?deptId=6",
        dataType:"json", //预期服务器返回数据的类型
        success:function(r){
            var html='';
            var res=r.msg
            for(var i=0;i<res.length;i++){
                html+="<option value='"+res[i].userId+"'>"+res[i].username+"</option>"
            }
            console.log(html)
            $("select[name='leader']").append(html)
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    });


    var $jobTableForm = $(".job-table-form");
    var settings = {
        url: ctx + "task/select",//prisoner/select   job/list
        pageSize: 10,
        queryParams: function (params) {
            console.log(params)
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $("input[name=beanName]").val(),
                leader: $("select[name='leader']").val()
            };d
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'prisonerNo',
                title: '人员编号'
            }, {
                field: 'name',
                title: '姓名'
            }, {
                field: 'taskType',
                title: '任务类型'

            },{
                field: 'leader',
                title: '负责人'
            }, {
                field: 'card',
                title: '身份证'
            },{
                field: 'year',
                title: '年'
            }, {
                field: 'month',
                title: '月'
            }, {
                field: 'telephone',
                title: '电话'
            }
        ]
    };

    $MB.initTable('jobTable', settings);
    // initSysCronClazzList();
});
var validator;
var $jobAddForm = $("#job-add-form");

$(function () {
   // validateRule();


    $("#job-add .btn-save").click(function () {
        $.ajax({
            type:"post",
            url:ctx + "task/finish",
            dataType:"json", //预期服务器返回数据的类型
            data:{id:ID},
            success:function(r){
                closeModal();
                refresh();
                $MB.n_success(r.msg);
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);

            }
        });

    });

    $("#job-add .btn-close").click(function () {
        closeModal();
    });
    $("#job-add1 .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $MB.closeAndRestModal("job-add");
    $MB.closeAndRestModal("job-add1");
  //  validator.resetForm();
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
        url:issOnlineUrl+"/fingerprint/getImage",
        dataType:"json", //预期服务器返回数据的类型
        data:{},
        success:function(r){

            if(r.ret=='0'){//录入完毕

                var imgStr=r.data.jpg_base64;

                $.ajax({
                        type:"post",
                        url:ctx + "picture/zwupload",
                        dataType:"json", //预期服务器返回数据的类
                        data:{prisonerNo:PrionerNo,imgStr:imgStr},
                        success:function(g){
                            if(g.code=="0"){

                               // $("input[name=fingerUrl]").val(g.msg.path)
                                path=g.msg;
                                console.log(path);
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
                    }}
                   );


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
            console.log(path);
            window.setTimeout(function(){
                $('.spmsg').html('录入完成')
            },500);
            $.ajax({
                type:"post",
                url:ctx + "task/update",
                dataType:"json", //预期服务器返回数据的类型
                data:{id:ID,fingerUrl:path},
                success:function(r){
                    window.setTimeout(function(){
                        $('.spmsg').html('已更新数据')
                    },500);

                },
                error:function(jqXHR){
                    alert("发生错误："+ jqXHR.status);
                    $('#zhiwMsg').hide();
                    $('#zhiwerror').show();
                }
            });
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
