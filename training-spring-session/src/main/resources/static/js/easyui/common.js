
function importJS(path){
    var a=document.createElement("script");
    a.type = "text/javascript";
    a.src=path;
    var head=document.getElementsByTagName("head")[0];
    head.appendChild(a);
}

function ajaxPost(url,data,callback){
    $.ajax({
        type : "post",
        url : url,
        data : data,
        error:function(data){
            msg.alert("Ajax调用出错！");
            msg.closeProgress();
        },
        success :callback
    });
};

String.prototype.endWith=function(str){
    var reg=new RegExp(str+"$");
    return reg.test(this);
};

String.prototype.startWith=function(str){
    var reg=new RegExp("^"+str);
    return reg.test(this);
};

String.prototype.isBlank=function(str){
    if( str == null || str == "" )
        return true ;
    return false ;
};
$.fn.serializeObjectToJson = function() {
    /**
     * 此方法代码参考：http://css-tricks.com/snippets/jquery/serialize-form-to-json/
     */
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            o[this.name] += "," + this.value;
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;

};