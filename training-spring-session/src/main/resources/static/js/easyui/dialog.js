var dialog = new DIALOG();
function DIALOG(){

    this.openDialog = _openDialog;

    function _openDialog($dialog,url,title,width,height){
        $dialog.dialog({
            title: title,
            width: width,
            height: height,
            closed: false,
            cache: false,
            href: url,
            modal: true
        });
    }

}