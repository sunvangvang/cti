$(document).ready(function() {
    /* collapse */
    $(".toggle").on("click", function(e) {
        var hasCollapse = $(".side").is(".collapse");
        if(!hasCollapse) {
            $(".console-frame .side").addClass("collapse");
            $(".console-frame .side .toggle .iconfont").removeClass("icon-sanjiao4");
            $(".console-frame .side .toggle .iconfont").addClass("icon-sanjiao3");
        }else {
            $(".console-frame .side").removeClass("collapse");
            $(".console-frame .side .toggle .iconfont").removeClass("icon-sanjiao3");
            $(".console-frame .side .toggle .iconfont").addClass("icon-sanjiao4");
        }
   });

    
});