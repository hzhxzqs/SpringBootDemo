let layer = layui.layer,
    layTable = layui.table;

let addDemoBtn = $("#add_demo"),
    listDemoTable = $("#list_demo"),

    demoInfoPanel = $("#demo_info"),
    demoIdData = $("#demo_id"),
    demoDescData = $("#demo_desc"),
    demoDateData = $("#demo_date");

let demoTable;

function initPage() {
    addDemoBtn.on("click", function () {
        openDemoPanel("添加", true);
    });

    demoTable = layTable.render({
        elem: listDemoTable,
        data: [],
        cols: [[
            {field: "id", title: "ID"},
            {field: "demoDesc", title: "描述"},
            {field: "createDate", title: "创建日期"},
            {title: "操作", templet: "#op_demo"}
        ]]
    });

    layTable.on("tool(" + listDemoTable.attr("id") + ")", function (obj) {
        let event = obj.event,
            data = obj.data;
        if (event === "update") {
            openDemoPanel("修改", false, data);
        } else if (event === "delete") {
            layer.confirm("确定删除？", function () {
                deleteDemo(data.id);
            });
        }
    });

    findAllDemo();
}

function reloadDemoTable(data) {
    if (demoTable !== undefined) {
        demoTable.reload({
            data: data,
            limit: data.length
        });
    }
}

function openDemoPanel(title, isAdd, data) {
    layer.open({
        type: 1,
        area: ["400px", "300px"],
        resize: false,
        title: title,
        content: demoInfoPanel,
        btn: ["确定", "取消"],
        success: function () {
            if (!isAdd) {
                demoIdData.val(data.id);
                demoDescData.val(data.demoDesc);
                demoDateData.val(data.createDate);
            }
        },
        yes: function (index) {
            if (isAdd) {
                addDemo();
            } else {
                updateDemo();
            }
            layer.close(index);
        },
        end: function () {
            demoIdData.val("");
            demoDescData.val("");
            demoDateData.val("");
            demoInfoPanel.css("display", "none");
        }
    });
}

function addDemo() {
    $.ajax({
        type: "POST",
        url: "/demo/add",
        dataType: "JSON",
        data: {demoDesc: demoDescData.val()},
        success: function (result) {
            if (result.code === "0000") {
                layer.msg("添加成功");
            } else {
                layer.msg(result.message);
            }
            findAllDemo();
        },
        error: function () {
            layer.msg("添加失败");
        }
    });
}

function deleteDemo(id) {
    $.ajax({
        type: "POST",
        url: "/demo/delete",
        dataType: "JSON",
        data: {id: id},
        success: function (result) {
            if (result.code === "0000") {
                layer.msg("删除成功");
            } else {
                layer.msg(result.message);
            }
            findAllDemo();
        },
        error: function () {
            layer.msg("删除失败");
        }
    });
}

function findAllDemo() {
    $.ajax({
        type: "POST",
        url: "/demo/findAll",
        success: function (result) {
            if (result.code === "0000") {
                reloadDemoTable(result.data);
            } else {
                layer.msg(result.message);
            }
        },
        error: function () {
            layer.msg("获取列表失败");
        }
    });
}

function updateDemo() {
    $.ajax({
        type: "POST",
        url: "/demo/update",
        dataType: "JSON",
        data: {id: demoIdData.val(), demoDesc: demoDescData.val()},
        success: function (result) {
            if (result.code === "0000") {
                layer.msg("修改成功");
            } else {
                layer.msg(result.message);
            }
            findAllDemo();
        },
        error: function () {
            layer.msg("修改失败");
        }
    });
}

$(function () {
    initPage();
});