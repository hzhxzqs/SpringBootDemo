let usernameElem = $("#username"),
    roleElem = $("#role"),
    permissionElem = $("#permission"),

    addRoleData = $("#add_role"),
    roleAddBtn = $("#role_add"),
    allRolesElem = $("#all_roles"),

    addPermData = $("#add_perm"),
    permAddBtn = $("#perm_add"),
    allPermsElem = $("#all_perms");

let userRolesPermsBuf;

function initWelcome() {
    roleAddBtn.on("click", function () {
        addRole(addRoleData.val());
    });
    permAddBtn.on("click", function () {
        addPermission(addPermData.val());
    });
    getUserRolesPerms();
    findAllRole();
    findAllPermission();
}

function initUserRolesPerms() {
    if (userRolesPermsBuf !== undefined) {
        usernameElem.text(userRolesPermsBuf.user);
        roleElem.text(userRolesPermsBuf.roles);
        permissionElem.text(userRolesPermsBuf.permissions);
    } else {
        usernameElem.text("暂无");
        roleElem.text("暂无");
        permissionElem.text("暂无");
    }
}

function initRole(data) {
    allRolesElem.empty();
    var text = "";
    data.forEach(function (item, index) {
        text += "<div id='role_" + item.id + "' class='item' title='" + item.name + "'>" + item.name + "</div>";
    });
    allRolesElem.append(text);

    allRolesElem.find("div").on("click", function (event) {
        let elem = $(event.target);
        if (elem.is(".item_selected")) {
            elem.removeClass("item_selected");
        } else {
            elem.addClass("item_selected");
        }
        let roleIds = [];
        allRolesElem.find(".item_selected").each(function (index, item) {
            roleIds.push($(item).attr("id").split("_")[1]);
        });
        giveUserRole(roleIds);
    });

    if (userRolesPermsBuf !== undefined) {
        userRolesPermsBuf.roles.forEach(function (item, index) {
            allRolesElem.find("div[title='" + item + "']").addClass("item_selected");
        });
    }
}

function initPermission(data) {
    allPermsElem.empty();
    var text = "";
    data.forEach(function (item, index) {
        text += "<div id='perm_" + item.id + "' class='item' title='" + item.name + "'>" + item.name + "</div>";
    });
    allPermsElem.append(text);

    allPermsElem.find("div").on("click", function (event) {
        let elem = $(event.target);
        if (elem.is(".item_selected")) {
            elem.removeClass("item_selected");
        } else {
            elem.addClass("item_selected");
        }
        let permIds = [];
        allPermsElem.find(".item_selected").each(function (index, item) {
            permIds.push($(item).attr("id").split("_")[1]);
        });
        giveRolePerm(permIds);
    });

    if (userRolesPermsBuf !== undefined) {
        userRolesPermsBuf.permissions.forEach(function (item, index) {
            allPermsElem.find("div[title='" + item + "']").addClass("item_selected");
        });
    }
}

function getUserRolesPerms() {
    $.ajax({
        type: "POST",
        url: "/user/getRolesPerms",
        success: function (result) {
            if (result.code === "0000") {
                userRolesPermsBuf = result.data;
                initUserRolesPerms();
            } else {
                userRolesPermsBuf = undefined;
                initUserRolesPerms();
            }
        },
        error: function () {
            userRolesPermsBuf = undefined;
            initUserRolesPerms();
        }
    });
}

function clearCache() {
    $.ajax({
        type: "POST",
        url: "/shiro/clearCache"
    });
}

function findAllRole() {
    $.ajax({
        type: "POST",
        url: "/role/findAll",
        success: function (result) {
            if (result.code === "0000") {
                initRole(result.data);
            }
        }
    });
}

function addRole(name) {
    $.ajax({
        type: "POST",
        url: "/role/add",
        dataType: "JSON",
        data: {name: name},
        success: function (result) {
            if (result.code === "0000") {
                findAllRole();
            }
        }
    });
}

function giveUserRole(roleIds) {
    $.ajax({
        type: "POST",
        url: "/role/give",
        dataType: "JSON",
        data: {roleIds: roleIds},
        success: function (result) {
            if (result.code === "0000") {
                getUserRolesPerms();
                clearCache();
            }
        }
    });
}

function findAllPermission() {
    $.ajax({
        type: "POST",
        url: "/permission/findAll",
        success: function (result) {
            if (result.code === "0000") {
                initPermission(result.data);
            }
        }
    });
}

function addPermission(name) {
    $.ajax({
        type: "POST",
        url: "/permission/add",
        dataType: "JSON",
        data: {name: name},
        success: function (result) {
            if (result.code === "0000") {
                findAllPermission();
            }
        }
    });
}

function giveRolePerm(permissionIds) {
    $.ajax({
        type: "POST",
        url: "/permission/give",
        dataType: "JSON",
        data: {permissionIds: permissionIds},
        success: function (result) {
            if (result.code === "0000") {
                getUserRolesPerms();
                clearCache();
            }
        }
    });
}

$(function () {
    initWelcome();
});