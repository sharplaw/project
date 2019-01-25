var PrionerNo='',ID=''
function updateJob() {
    var selected = $("#jobTable").bootstrapTable("getSelections");
    console.log(selected)
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
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
                $('#img1').attr("src","data:image/png;base64,"+r.msg[0].actiivityUrl+"")
                $('#img2').attr("src","data:image/png;base64,"+r.msg[0].talkUrl+"")
                var $form = $('#job-add');
                $form.modal();
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });

    }else{
        var $form = $('#job-add1');
        $form.modal();
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

    var $jobTableForm = $(".job-table-form");
    var settings = {
        url: ctx + "task/select",//prisoner/select   job/list
        pageSize: 10,
        queryParams: function (params) {
            console.log(params)
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $jobTableForm.find("#sys-cron-clazz-list-bean").find(".form-control").val(),
                talker: $jobTableForm.find("#sys-cron-clazz-list-method").find(".form-control").val(),
                 //leader: $jobTableForm.find("select[name='leader']").val()
            };
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
                field: 'talker',
                title: '谈话人'
            }, {
                field: 'talkerUnit',
                title: '谈话人信息'
            },{
                field: 'recorder',
                title: '记录人'
            }, {
                field: 'recorderUnit',
                title: '记录人信息'
            }, {
                field: 'talkStartTime',
                title: '开始时间'
            }, {
                field: 'talkEndTime',
                title: '结束时间'
            }, {
                field: 'servingStartTime',
                title: '矫正开始时间',
                // formatter: function (value, row, index) {
                //     if (value === '1') return '<span class="badge badge-danger">暂停</span>';
                //     if (value === '0') return '<span class="badge badge-success">正常</span>';
                // }
            }
            , {
                field: 'servingEndTime',
                title: '矫正结束时间',

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
    validator.resetForm();
}
var timing='';
function zwsb() {
    var skip=$('#skip').val();
    if(skip=='0'){//启动指纹识别
        // $('#hidezw1').show();
        $('.hidezw2').show();
        $('#zhiwMsg').hide()
        $.ajax({
            type:"post",
            url:ctx + "zk/start",
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
            type:"post",
            url:ctx + "zk/lu",
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
        type:"post",
        url:ctx + "zk/checkpic",
        dataType:"json", //预期服务器返回数据的类型
        data:{PrionerNo:PrionerNo},
        success:function(r){

            if(r.code=='0'){//录入完毕
                $("input[name=fingerUrl]").val(r.msg.path)
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
        type:"post",
        url:ctx + "zk/end",
        dataType:"json", //预期服务器返回数据的类型
        data:{},
        success:function(r){
            window.setTimeout(function(){
                $('.spmsg').html('录入完成')
            },500);
            $.ajax({
                type:"post",
                url:ctx + "task/update",
                dataType:"json", //预期服务器返回数据的类型
                data:{id:ID,fingerUrl: $("input[name=fingerUrl]").val(r.msg.path)},
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
