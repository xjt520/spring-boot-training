var msg = new MSG();
function MSG(){

    this.alert = _alert;
    this.message = _message;
    this.showProcess = _showProcess;
    this.closeProgress = _closeProgress;
    
    function _alert(msg){
        $.messager.alert("提示",msg,"info");
    }
    function _message(info){
        $.messager.show({
            title:'提示信息',
            msg:"<b>"+info,
            timeout:2000,
            showType:'slide'
        });
        return;
    }

    function _showProcess(msg) {
        $.messager.progress({
            title: "提示",
            msg: msg
        });
    }

    function _closeProgress(){
        $.messager.progress('close');
    }
}