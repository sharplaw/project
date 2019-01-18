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
    var $form = $('#job-add');
    $form.modal();
    $('#addID').append("<input type='hidden' name='id'/>")
        $("#job-add-modal-title").html('修改任务');
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
        $("#job-add-button").attr("name", "update");


}