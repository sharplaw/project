var PrionerNo='',ID='',savetype='1'

function addvideo() {
    var $form = $('#job-add');
    $form.modal();
    savetype='2'
}
function videoseting() {
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
    savetype='1'
    var sels = selected[0];
    //PrionerNo=sels.prisonerNo;
    ID=sels.id;
    var $form = $('#job-add');
    $form.modal();
    $form.find("input[name='title_edit']").val(sels.title);
    $form.find("input[name='describle_edit']").val(sels.describle);
    $form.find("input[name='videoUrl_edit']").val(sels.videoUrl);
    $form.find("select[name='type']").val(sels.type);

    $.ajax({
        type:"post",
        url:ctx + "video/selectSingle",
        dataType:"json", //预期服务器返回数据的类型
        data:{"id": ID},
        success:function(r){

        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    });

}
function delvideo() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定删除选中的任务？",
        confirmButtonText: "确定删除"
    }, function () {
        // $.post(ctx + 'job/delete', {"ids": ids}, function (r) {
        //     if (r.code === 0) {
        //         $MB.n_success(r.msg);
        //         refresh();
        //     } else {
        //         $MB.n_danger(r.msg);
        //     }
        // });
        $.ajax({
            type:"post",
            url:ctx + "video/delte",
            dataType:"json", //预期服务器返回数据的类型
            data:{"id": ids,flag:1},
            success:function(r){
                window.location.reload()
                closeModal();
                refresh();
                $MB.n_success(r.msg);
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });
    });

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
        url: ctx + "video/select",//prisoner/select   job/list
        pageSize: 10,
        queryParams: function (params) {
            console.log(params)
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                //id: $jobTableForm.find("#sys-cron-clazz-list-bean").find(".form-control").val(),
               // title: $jobTableForm.find("#sys-cron-clazz-list-method").find(".form-control").val(),
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'id',
                title: '编号'
            }, {
                field: 'title',
                title: '标题'
            }, {
                field: 'describle',
                title: '描述'

            },{
                field: 'createTime',
                title: '创建时间'
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


    // $("#job-add .btn-save").click(function () {
    //     $.ajax({
    //         type:"post",
    //         url:ctx + "task/finish",
    //         dataType:"json", //预期服务器返回数据的类型
    //         data:{id:ID},
    //         success:function(r){
    //             closeModal();
    //             refresh();
    //             $MB.n_success(r.msg);
    //         },
    //         error:function(jqXHR){
    //             alert("发生错误："+ jqXHR.status);
    //
    //         }
    //     });
    //
    // });

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
    //validator.resetForm();
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

$(function () {
    $("#input-repl-3a").fileinput({
        dropZoneTitle: "请上传小于150M的视频！",
        uploadUrl: "video/upload",
        enctype: 'multipart/form-data',
        language: "zh",
        autoReplace: true,
        showCaption: false,
        showUpload: true,
        overwriteInitial: true,
        showUploadedThumbs: true,
        //showPreview:false,                   //显示上传图片的大小信息
        maxFileCount: 1,
        minFileCount: 1,
        maxFileSize: 153600,//文件最大153600kb=150M
        initialPreviewShowDelete: false,
        showRemove: true,//是否显示删除按钮
        showClose: false,
        layoutTemplates: {
            actionUpload: '',
        },
        allowedFileExtensions: ["mp4", "avi", "dat", "3gp", "mov", "rmvb"],
        previewSettings: {
            image: {
                width: "100%",
                height: "100%"
            },
        }
    });

    $("#input-repl-3a").on("fileuploaded", function (event, data, previewId, index) {

        $('input[name=videoUrl]').val(data.response.msg.path)
        $('input[name=photoUrl]').val(data.response.msg.pic)

    })
})
function save(){
    if(savetype=='1'){
        var videoUrl = $('input[name=videoUrl]').val();
        var photoUrl = $('input[name=photoUrl]').val();
        var types = $('select[name=type]').val();

        var title_edit = $('input[name=title_edit]').val();
        var describle_edit = $('input[name=describle_edit]').val();

        $.ajax({
            type:"post",
            url:ctx + "video/update",
            dataType:"json", //预期服务器返回数据的类型
            data:{
                id:ID,
                title:title_edit,
                type:types,
                describle:describle_edit,
                videoUrl:videoUrl,
                photoUrl:photoUrl,
            },
            success:function(r){


            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });
    }else{
        var videoUrl = $('input[name=videoUrl]').val();
        var photoUrl = $('input[name=photoUrl]').val();
        var types = $('select[name=type]').val();

        var title_edit = $('input[name=title_edit]').val();
        var describle_edit = $('input[name=describle_edit]').val();

        $.ajax({
            type:"post",
            url:ctx + "video/add",
            dataType:"json", //预期服务器返回数据的类型
            data:{
                title:title_edit,
                type:types,
                describle:describle_edit,
                videoUrl:videoUrl,
                photoUrl:photoUrl,
            },
            success:function(r){


            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });
    }

}
