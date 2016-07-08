function formatTime(val,row,index){
    if (val == undefined || val == "")
        return "";
    var date;
    if(val instanceof Date){
        date = val;
    }else{
        date = new Date(val);
    }
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();

    var h = date.getHours();
    var mm = date.getMinutes();
    var s = date.getSeconds();

    var dateStr = y + '-' + (m < 10 ? ('0' + m) : m) + '-'
        + (d < 10 ? ('0' + d) : d);
    var TimeStr = h + ':' + (mm < 10 ? ('0' + mm) : mm) + ':'
        + (s < 10 ? ('0' + s) : s);

    return dateStr + ' ' + TimeStr;
}