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
    if(sels.statue=='0'){
        $.ajax({
            type:"get",
            url:ctx + "task/selectSingle",
            dataType:"json", //预期服务器返回数据的类型
            data:{id:sels.id},
            success:function(r){
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

    // $('#addID').append("<input type='hidden' name='id'/>")
    // $("#job-add-modal-title").html('修改任务');
    $form.find("input[name='name']").val(sels.name);
    $form.find("input[name='servingStartTime']").val(sels.servingStartTime);
    $form.find("input[name='date1']").val(sels.servingStartTime);
    $form.find("input[name='date2']").val(sels.servingEndTime);

    $form.find("input[name='prisonerNo']").val(sels.prisonerNo);
    $form.find("input[name='servingEndTime']").val(sels.servingEndTime);
    $form.find("input[name='corrective']").val(sels.corrective);
    $form.find("input[name='sex']").val(sels.sex);
    $form.find("input[name='card']").val(sels.card);
    $form.find("input[name='telephone']").val(sels.telephone);
    $form.find("input[name='address']").val(sels.address);
    $form.find("input[name='id']").val(sels.id);
    // $("#job-add-button").attr("name", "update");


}
$(function () {
    // $.ajax({
    //     type:"get",
    //     url:ctx + "user/list?deptId=6",
    //     dataType:"json", //预期服务器返回数据的类型
    //     success:function(r){
    //         var html='';
    //         var res=r.rows
    //         for(var i=0;i<res.length;i++){
    //             console.log(r.rows[i])
    //             html+="<option value='"+r.rows[i].userId+"'>"+r.rows[i].username+"</option>"
    //         }
    //         $("select[name='leader']").append(html)
    //     },
    //     error:function(jqXHR){
    //         alert("发生错误："+ jqXHR.status);
    //     }
    // });

    var $jobTableForm = $(".job-table-form");
    var settings = {
        url: ctx + "task/select",//prisoner/select   job/list
        pageSize: 10,
        queryParams: function (params) {
            console.log(params)
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $jobTableForm.find("#sys-cron-clazz-list-bean").find(".autocomplete-input").val(),
                 // card: $jobTableForm.find("#sys-cron-clazz-list-method").find(".autocomplete-input").val(),
                 leader: $jobTableForm.find("select[name='leader']").val()
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