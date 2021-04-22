let usernameData = $("#username"),
    passwordData = $("#password"),
    addBtn = $("#add"),
    loginBtn = $("#login"),
    loginOutBtn = $("#login_out"),
    loginTipElem = $("#login_tip");

function initLogin() {
    addBtn.on("click", function () {
        add(usernameData.val(), passwordData.val());
    });

    loginBtn.on("click", function () {
        login(usernameData.val(), passwordData.val());
    });

    loginOutBtn.on("click", function () {
        loginOut();
    });
}

function add(username, password) {
    $.ajax({
        type: "POST",
        url: "/user/add",
        dataType: "JSON",
        data: {username: username, password: password},
        success: function (result) {
            if (result.code === "0000") {
                loginTipElem.text("添加成功");
            } else {
                loginTipElem.text(result.message);
            }
        },
        error: function () {
            loginTipElem.text("添加失败");
        }
    });
}

function login(username, password) {
    $.ajax({
        type: "POST",
        url: "/shiro/login",
        dataType: "JSON",
        data: {username: username, password: password},
        success: function (result) {
            if (result.code === "0000") {
                loginTipElem.text("登录成功");
                reloadWelcomeFrame();
            } else {
                loginTipElem.text(result.message);
            }
        },
        error: function () {
            loginTipElem.text("登录失败");
        }
    });
}

function loginOut() {
    $.ajax({
        type: "POST",
        url: "/shiro/loginOut",
        success: function (result) {
            if (result.code === "0000") {
                loginTipElem.text("退出成功");
                reloadWelcomeFrame();
            } else {
                loginTipElem.text(result.message);
            }
        },
        error: function () {
            loginTipElem.text("退出失败");
        }
    });
}

function reloadWelcomeFrame() {
    parent.window["welcomeFrame"].location.reload();
}

$(function () {
    initLogin();
});