layui.use(["form", "layer", "jquery"], function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $(".loginBody .seraph").click(function() {
        layer.msg("测试样式", {
            time:5000
        })
    });

    // 登录按钮
    form.on("submit(login)", function(data) {
        $(this).text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
        var param = data.field;
        var dataJS = JSON.stringify(param);
        $.post(
            "/auth",
            $("#login_form").serialize(),
            function(res) {
                console.log(res);
                var result = JSON.parse(res);
                var status = result.status;
                console.log(status);
                var token = result.token;
                console.log(token);
                if("200" == status) {
                    window.location.href = "/index";
                //     $.ajax({
                //         url: "/index",
                //         type: "get",
                //         dataType: "html",
                //         timeout: 10000,
                //         beforeSend: function(request) {
                //             request.setRequestHeader("Authorization", token);
                //         },
                //         success: function(res) {
                //             console.log(res);
                //         }
                //     });

                }
            }
        );
        /*
        setTimeout(function() {
            window.location.href = "/index";
        }, 1000) */
        // return false;
    })

    // 表单输入效果
    $(".loginBody .input-item").click(function(e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function() {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function() {
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != "") {
            $(this).parent().addClass("layui-input-active");
        }else {
            $(this).parent().removeClass("layui-input-active");
        }
    })
})