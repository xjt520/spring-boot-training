var tab = new TAB();
function TAB(){

    this.openTab = _openTab;
    this.closeTab = _closeTab;
    this.closeCurrentTab = _closeCurrentTab;

    function _closeTab($tab,title){
        $tab.tabs("close", title);
    }

    function _closeCurrentTab($tab){
        var tab = $tab.tabs("getSelected");
        var index = $tab.tabs("getTabIndex",tab);
        $tab.tabs("close", index);
    }

    function _openTab($tab,url,title){
        if ($tab.tabs("exists", title)){
            _closeTab($tab,title);
        }
        $tab.tabs("add",{
            title:title,
            href:url,
            closable:true
        });
    }
}