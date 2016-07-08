var dg = new DATAGRID();
function DATAGRID() {

    /**
     * datagrid
     */
    this.datagrid = _datagrid;
    /**
     *查询数据
     */
    this.queryRecord = _queryRecord;
    /**
     *重置查询
     */
    this.resetRecord = _resetRecord;
    /**
     * 新增一条数据
     */
    this.insertRecord = _insertRecord;
    /**
     * 修改一条数据
     */
    this.updateRecord = _updateRecord;
    /**
     * 删除一条或者多条记录
     */
    this.deleteRecord = _deleteRecord;
    /**
     * 查看一条记录
     */
    this.viewRecord = _viewRecord;
    /**
     * 根据datagrid中选中的记录执行文件下载
     */
    this.downloadRecord = _downloadRecord;
    /**
     * 根据form中的条件参数，提交form进行文件下载
     */
    this.downloadForm = _downloadForm;
    /**
     *  取得选中唯一行中指定列的值
     */
    this.getSelection = _getSelection;
    /**
     *  取得选中的多行中指定列的值的数组
     */
    this.getSelections = _getSelections;
    /**
     *  取得选中的唯一一行，返回唯一一行指定列值，不满足条件的时候返回null
     */
    this.singleSelect = _singleSelect;

    //增=============timer
    function _insertRecord(timer, url, title){
        var action = url+'?timer=' + timer;
        if(viewSkipMode == 'tab'){
            tab.openTab($('#tabs'),action, title);
        }else{
            dialog.openDialog($('dialog_'+timer), action, title, 800, 600);
        }
    }

    //修=============timer
    function _updateRecord(timer,url,id,title){
        var $datagrid = $('#datagrid_'+timer);
        var rowid = _singleSelect($datagrid, id);
        if(rowid){
            var action = url+'?timer=' + timer + '&id=' + rowid ;
            if(viewSkipMode == 'tab'){
                tab.openTab($('#tabs'),action, title);
            }else{
                dialog.openDialog($('dialog_'+timer), action, title, 800, 600);
            }
        }
    }

    //查=============timer
    function _viewRecord(timer,url,id,title){
        _updateRecord(timer, url, id, title);
    }

    //删=============timer
    function _deleteRecord($datagrid, action, id) {

        var ids = _getSelections($datagrid,id);

        if(ids.length < 1) {
            msg.alert("必须选择一行！");
            return;
        } else if (ids.length >= 1) {

            var param = {};

            $.each(ids, function(i,n) {

                if (param["ids"]) {

                    param["ids"] += "," + n;
                } else {
                    param["ids"] = n || '';
                }
            });
            $.messager.confirm('确认', "确定删除指定记录？",
                function(r){
                    if(r){
                        msg.showProcess('正在删除...');
                        $.post(action, param,function(result){
                            msg.closeProgress();
                            if(result.isSuccess == 'true'){
                                msg.message("<b>删除成功！共删除记录"+ids.length+"条");
                                $datagrid.datagrid('reload');
                            }else{
                                msg.alert('删除失败!');
                            }
                        });
                    }
                });
            return;
        }

    }

    //查询=============timer
    function _queryRecord($form,$datagrid) {
        _query($form,$datagrid);
    }

    //重置=============timer
    function _resetRecord($form,$datagrid) {
        $form.form('reset');
        _query($form,$datagrid);
    }

    function _datagrid($datagrid,toolbar,url,frozenColumns,columns){
        var datagrid = $datagrid.datagrid({
            url:url,
            fit:true,
            rownumbers:true,
            pagination:true,
            toolbar:toolbar,
            remoteSort: false,
            pageSize:20,
            method:'post',
            onLoadError:function(){
                msg.alert("加载错误！请刷新页面或者重新登录！");
            },
            onLoadSuccess:function(){
                $(this).datagrid('unselectAll');
            },
            frozenColumns :frozenColumns,
            columns:columns
        });
        return datagrid;
    }

    /** 设置标志表明当前查询加载是否完成，如果没有完成不进行下一次查询* */
    var isLoad = false;
    function _query($form,$datagrid) {

        var data = $form.serializeObjectToJson();
        if (!isLoad) {
            isLoad = true;
            $datagrid.datagrid("load",data);
            isLoad=false;
        }
    }

    function _downloadForm($form,action){
        $form.attr("action",action);
        $form.submit();
    }

    function _downloadRecord($datagrid,action,id){
        var ids = _getSelection($datagrid,id);
        var $form = $("<form>");
        form.attr("style","display:none");
        form.attr("target","");
        form.attr("method","post");
        form.attr("action",action);
        var input = "<input type='hidden' value="+ids+" name='ids'/>";
        form.append($(input));
        form.submit();
        form.remove;
    }

    function _singleSelect($datagrid,id){
        if(_getSelections($datagrid,id).length < 1) {
            msg.alert("必须选择一行！");
            return;
        } else if (_getSelections($datagrid,id).length > 1) {
            msg.alert("只能选择一行, 不允许多选！");
            return;
        }
        return _getSelection($datagrid,id);
    }

    function _getSelection($datagrid,id) {
        var row = $datagrid.datagrid('getSelections');
        if (row)
            return row[0][id];
        else
            return null;
    }

    function _getSelections($datagrid, id) {
        var ids = [];
        var rows = $datagrid.datagrid('getSelections');

        for ( var i = 0; i < rows.length; i++) {

            var row = rows[i];
            ids[i] = row[id];

        }
        return ids;
    }

}