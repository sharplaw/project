var $moban = $("#moban");
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

    $("#savemoban").click(function () {
      var id=$('input[name=id]').val()
          ,talkStartTime=$('input[name=talkStartTime]').val()
          ,talkEndTime=$('input[name=talkEndTime]').val()
          ,talker=$('input[name=talker]').val()
          ,talkerUnit=$('input[name=talkerUnit]').val()
          ,recorder=$('input[name=recorder]').val()
          ,recorderUnit=$('input[name=recorderUnit]').val()
          ,conment=$('textarea[name=conment]').val()
        $.ajax({
            type:"post",
            url:ctx + "talk/update",
            dataType:"json", //预期服务器返回数据的类型
            data:{
                id:id,talkStartTime:talkStartTime,talkEndTime:talkEndTime,talker:talker,talkerUnit:talkerUnit,recorder:recorder,recorderUnit:recorderUnit,conment:conment,
            },
            success:function(r){
                // window.location.reload()
                      id=$('input[name=date3]').val('')
                     id=$('input[name=date4]').val('')
                    id=$('input[name=id]').val('')
                    talkStartTime=$('input[name=talkStartTime]').val('')
                    talkEndTime=$('input[name=talkEndTime]').val('')
                    talker=$('input[name=talker]').val('')
                    talkerUnit=$('input[name=talkerUnit]').val('')
                    recorder=$('input[name=recorder]').val('')
                    recorderUnit=$('input[name=recorderUnit]').val('')
                    conment=$('textarea[name=conment]').val('')
                $('#moban').modal('hide')
                refresh();
                $MB.n_success(r.msg);
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        });
    });
    $("input[name='date3']").daterangepicker(
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
        $("#date3").val("请选择日期");
        $("#talkStartTime").val("");
    }).on('apply.daterangepicker', function(ev, picker) {
        $("#talkStartTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
        $("#date3").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
    });
    $("input[name='date4']").daterangepicker(
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
        $("#date4").val("请选择日期");
        $("#talkEndTime").val("");
    }).on('apply.daterangepicker', function(ev, picker) {
        $("#talkEndTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
        $("#date4").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
    });






});


function moban() {
    var selected = $("#jobTable").bootstrapTable("getSelections");
    console.log(selected)
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要谈话模板的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能选择一个任务！');
        return;
    }
    var sels = selected[0];
    var $form = $('#moban');
    $form.modal();
        // $("#job-add-modal-title").html('修改任务');
        // $form.find("input[name='name']").val(sels.name);
    // $form.find("input[name='servingStartTime']").val(sels.servingStartTime);
    // $form.find("input[name='prisonerNo']").val(sels.prisonerNo);
    // $form.find("input[name='servingEndTim']").val(sels.servingEndTim);
    // $form.find("input[name='corrective']").val(sels.corrective);
    // $form.find("input[name='sex']").val(sels.sex);
    // $form.find("input[name='card']").val(sels.card);
    // $form.find("input[name='telephone']").val(sels.telephone);
    // $form.find("input[name='address']").val(sels.address);
    $form.find("input[name='id']").val(sels.id);
        // $("#job-add-button").attr("name", "update");
    $.ajax({
        type:"get",
        url:ctx + "talk/select",
        dataType:"json", //预期服务器返回数据的类型
        data:{prisonerNo:sels.prisonerNo},
        success:function(r){
            // $form.find("input[name='id']").val(r.msg[0].id);
            $form.find("input[name='id']").val(r.msg[0].id);
            $form.find("input[name='date3']").val(r.msg[0].talkStartTime);
            $form.find("input[name='date4']").val(r.msg[0].talkEndTime);
            $form.find("input[name='talkStartTime']").val(r.msg[0].talkStartTime);
            $form.find("input[name='talkEndTime']").val(r.msg[0].talkEndTime);
            $form.find("input[name='talker']").val(r.msg[0].talker);
            $form.find("input[name='talkerUnit']").val(r.msg[0].talkerUnit);
            $form.find("input[name='recorder']").val(r.msg[0].recorder);
            $form.find("input[name='recorderUnit']").val(r.msg[0].recorderUnit);
            $form.find("textarea[name='conment']").val(r.msg[0].conment);

        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    });


}