$(function () {
    //动态菜单数据
    var menu = [{
        text : "基础管理",
        state : "open",
        children : [
            {
                text : "用户管理",
                iconCls:"icon-gears",
                attributes : {
                    url : "/admin/users/index"
                }
            }
            ,
            {
                text : "Objects",
                iconCls:"icon-gears",
                attributes : {
                    url : "objects/index"
                }
            }
        ]
    }
    ];
    //实例化树形菜单
    $("#tree").tree({
        data : menu,
//	        lines : true,
        onClick : function (node) {
            if (node.attributes) {
                Open(node.text, node.attributes.url);
            }
        }
    });
    //绑定tabs的右键菜单
    $("#tabs").tabs({
        onContextMenu : function (e, title) {
            e.preventDefault();
            $('#tabsMenu').menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data("tabTitle", title);
        }
    });
    //实例化menu的onClick事件
    $("#tabsMenu").menu({
        onClick : function (item) {
            CloseTab(this, item.name);
        }
    });
});
//在右边center区域打开菜单，新增tab
function Open(text, url) {
    if ($("#tabs").tabs('exists', text)) {
        $('#tabs').tabs('select', text);
    } else {
        $('#tabs').tabs('add', {
            title : text,
            closable : true,
            href : url
        });
    }
}
//几个关闭事件的实现
function CloseTab(menu, type) {
    var curTabTitle = $(menu).data("tabTitle");
    var tabs = $("#tabs");

    if (type === "close") {
        tabs.tabs("close", curTabTitle);
        return;
    }

    var allTabs = tabs.tabs("tabs");
    var closeTabsTitle = [];

    $.each(allTabs, function () {
        var opt = $(this).panel("options");
        if (opt.closable && opt.title != curTabTitle && type === "Other") {
            closeTabsTitle.push(opt.title);
        } else if (opt.closable && type === "All") {
            closeTabsTitle.push(opt.title);
        }
    });

    for (var i = 0; i < closeTabsTitle.length; i++) {
        tabs.tabs("close", closeTabsTitle[i]);
    }
}