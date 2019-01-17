$(function () {
    var $jobTableForm = $(".job-table-form");
    var settings = {
        url: ctx + "prisoner/select",//prisoner/select   job/list
        pageSize: 10,
        queryParams: function (params) {
            console.log(params)
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $jobTableForm.find("#sys-cron-clazz-list-bean").find(".autocomplete-input").val(),
                card: $jobTableForm.find("#sys-cron-clazz-list-method").find(".autocomplete-input").val(),
                // status: $jobTableForm.find("select[name='status']").val()
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'id',
                title: '任务ID'
            }, {
                field: 'name',
                title: '姓名'
            }, {
                field: 'card',
                title: '身份证号码'
            }, {
                field: 'sex',
                title: '性别'
            }, {
                field: 'telephone',
                title: '联系电话'
            }, {
                field: 'leader',
                title: '负责人'
            }, {
                field: 'servingStartTime',
                title: '矫正时间',
                // formatter: function (value, row, index) {
                //     if (value === '1') return '<span class="badge badge-danger">暂停</span>';
                //     if (value === '0') return '<span class="badge badge-success">正常</span>';
                // }
            }
        ]
    };

    $MB.initTable('jobTable', settings);
    initSysCronClazzList();
});

function search() {
    $MB.refreshTable('jobTable');
}

function refresh() {
    $(".job-table-form")[0].reset();
    search();
}

function deleteJob() {
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
            url:ctx + "prisoner/del",
            dataType:"json", //预期服务器返回数据的类型
            data:{"id": ids},
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
    });
}

function runJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要立即执行的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定执行选中的任务？",
        confirmButtonText: "确定执行"
    }, function () {
        $.post(ctx + 'job/run', {"jobIds": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function pauseJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要暂停的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定暂停选中的任务？",
        confirmButtonText: "确定暂停"
    }, function () {
        $.post(ctx + 'job/pause', {"jobIds": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function resumeJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要恢复的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定恢复选中的任务？",
        confirmButtonText: "确定恢复"
    }, function () {
        $.post(ctx + 'job/resume', {"jobIds": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportJobExcel() {
    $.post(ctx + "job/excel", $(".job-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportJobCsv() {
    $.post(ctx + "job/csv", $(".job-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function initSysCronClazzList() {
    $.getJSON(ctx + "job/getSysCronClazz", function (r) {
        r = r.code == 0 ? r.msg : []
        $('#sys-cron-clazz-list-bean').autocomplete({
            hints: r,
            keyname: 'beanName',
            width: 70,
            // height: 32,
            valuename: 'beanName',
            showButton: false,
            onSubmit: function (text) {
                $('#sys-cron-clazz-list-bean').siblings("input[name='beanName']").val(text);

            }
        });
        $('#sys-cron-clazz-list-method').autocomplete({
            hints: r,
            keyname: 'beanName',
            width: 70,
            // height: 31,
            valuename: 'methodName',
            showButton: false,
            onSubmit: function (text) {
                $('#sys-cron-clazz-list-method').siblings("input[name='methodName']").val(text);
            }
        });
    });


}
