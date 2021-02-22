let layer = layui.layer,
    layTable = layui.table;

let addDemoBtn = $("#add_demo"),
    addMultiDemoBtn = $("#add_multi_demo"),
    listDemoTable = $("#list_demo"),

    demoInfoPanel = $("#demo_info"),
    demoIdData = $("#demo_id"),
    demoDescData = $("#demo_desc"),
    demoDateData = $("#demo_date"),

    multiDemoInfoPanel = $("#multi_demo_info"),
    demoDesc1Data = $("#demo_desc1"),
    demoDesc2Data = $("#demo_desc2"),
    demoDesc3Data = $("#demo_desc3");

let demoTable;

function initPage() {
    addDemoBtn.on("click", function () {
        openDemoPanel("添加");
    });

    addMultiDemoBtn.on("click", function () {
        openMultiDemoPanel();
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
            openDemoPanel("修改", data.id);
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

function openDemoPanel(title, id) {
    layer.open({
        type: 1,
        area: ["400px", "300px"],
        resize: false,
        title: title,
        content: demoInfoPanel,
        btn: ["确定", "取消"],
        success: function () {
            if (id !== undefined) {
                findDemoById(id);
            }
        },
        yes: function (index) {
            if (id === undefined) {
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

function openMultiDemoPanel() {
    layer.open({
        type: 1,
        area: ["400px", "300px"],
        resize: false,
        title: "批量添加",
        content: multiDemoInfoPanel,
        btn: ["确定", "取消"],
        yes: function (index) {
            addMultiDemo();
            layer.close(index);
        },
        end: function () {
            demoDesc1Data.val("");
            demoDesc2Data.val("");
            demoDesc3Data.val("");
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

function addMultiDemo() {
    var demos = [
        {demoDesc: demoDesc1Data.val()},
        {demoDesc: demoDesc2Data.val()},
        {demoDesc: demoDesc3Data.val()}
    ];
    $.ajax({
        type: "POST",
        url: "/demo/addList",
        contentType: "application/json",
        dataType: "JSON",
        data: JSON.stringify(demos),
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

function findDemoById(id) {
    $.ajax({
        type: "POST",
        url: "/demo/findById",
        dataType: "JSON",
        data: {id: id},
        success: function (result) {
            if (result.code === "0000") {
                let data = result.data;
                demoIdData.val(data.id);
                demoDescData.val(data.demoDesc);
                demoDateData.val(data.createDate);
            } else {
                layer.msg(result.message);
            }
        },
        error: function () {
            layer.msg("获取失败");
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