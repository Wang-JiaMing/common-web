/**author:wangjiaming**/
(function ($) {
    var resourse = {loadingImg: '../../images/loading/boLoading.gif'};
    /**
     _options={

          size:,//'lg','sm','xs'
      }
     **/

    $.fn.boButton = function (options) {
        if ($.fn.boButton.methods[options]) {
            return $.fn.boButton.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boButton.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };

    $.fn.boButton.methods = {
        init: function (options) {
            var _this = this;
            /*button*/
            $(_this).addClass('btn btn-' + options.scene);
            if (options.size && options.size != '') {
                var sizeClass;
                if (options.size == 'lg') {
                    sizeClass = 'btn-lg';
                } else if (options.size == 'sm') {
                    sizeClass = 'btn-sm';
                } else {
                    sizeClass = 'btn-xs';
                }
                $(_this).addClass(sizeClass);
            }
            if ($(_this)[0].outerHTML.toLowerCase().indexOf('<button') > -1) {
                $(_this).append(options.text).attr('type', options.type);
                /*<a>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<a') > -1) {
                $(_this).append(options.text);
                /*<input>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<input') > -1) {
                $(_this).attr('value', options.text).attr('type', options.type);
            } else {
                console.error("please use <button>,<a> or <input>!");
            }
        },
        disabled: function () {
            var _this = this;
            if ($(_this)[0].outerHTML.toLowerCase().indexOf('<button') > -1) {
                $(_this).attr('disabled', 'disabled');
                /*<a>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<a') > -1) {
                $(_this).addClass('disabled');
                /*<input>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<input') > -1) {
                $$(_this).attr('disabled', 'disabled');
            }
        },
        enable: function () {
            var _this = this;
            if ($(_this)[0].outerHTML.toLowerCase().indexOf('<button') > -1) {
                $(_this).removeAttr('disabled', 'disabled');
                /*<a>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<a') > -1) {
                $(_this).removeClass('disabled');
                /*<input>*/
            } else if ($(_this)[0].outerHTML.toLowerCase().indexOf('<input') > -1) {
                $$(_this).removeAttr('disabled', 'disabled');
            }
        }
    };

    /**
     下拉多选
     <div id="demo"><div>

     $('#demo').boCheckBox({
            url:,

        });
     */
    var defaultCheckBoxOptions = {}
    $.fn.boCheckBox = function (options) {
        if ($.fn.boCheckBox.methods[options]) {
            return $.fn.boCheckBox.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boCheckBox.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };

    $.fn.boCheckBox.methods = {
        getParams: function (options) {
            if (defaultCheckBoxOptions[this[0].id] == undefined) {
                defaultCheckBoxOptions[this[0].id] = $.extend({}, {
                    dropup: false,
                    queryParams: "",
                    namespace: this[0].id
                }, options);
            } else {
                defaultCheckBoxOptions[this[0].id] = $.extend({}, defaultCheckBoxOptions[this[0].id], options);
            }
            return defaultCheckBoxOptions[this[0].id];
        },
        init: function (options) {
            var _this = this;
            var _options = _this.boCheckBox('getParams', options);
            if (_options.dropup) {
                $(_this).addClass('dropup');
            } else {
                $(_this).addClass('dropdown');
            }
            var $button = $('<button class="btn btn-default dropdown-toggle" type="button" id="_' + _options.namespace + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width:150px"><div  id="_checkBoxText-' + _options.namespace + '" style="width:100px;float:left;overflow:hidden">---请选择---</div><span class="caret"></span></button>');
            var $ul = $('<ul class="dropdown-menu" aria-labelledby="_' + _options.namespace + '"></ul>');
            var $checkAll = $('<li><input type="checkbox" id="checkAll-' + _options.namespace + '"/>全选</li><li role="separator" class="divider"></li>'),
                $checkBoxContent = $('<div id="_checkBox-' + _options.namespace + '"></div>');
            if (options.data) {
                for (var i = 0; i < options.data.length; i++) {
                    $checkBoxContent.append('<li><input type="checkbox" id="' + _options.namespace + '-' + i + '" name="' + _options.namespace + '" value="' + _options.data[i].value + '" text="' + _options.data[i].name + '"/>' + _options.data[i].name + '</li>');
                }
                $ul.append($checkAll);
                $ul.append($checkBoxContent);
                $(_this).append($button);
                $(_this).append($ul);
                $('#checkAll-' + _options.namespace).on("change", function () {
                    if ($(this)[0].checked) {
                        $(_this).boCheckBox('checkAll');
                    } else {
                        $(_this).boCheckBox('enCheckAll');
                    }
                    var content = "";
                    for (var i = 0; i < $(_this).boCheckBox('getCheckTexts').length; i++) {
                        content += $(_this).boCheckBox('getCheckTexts')[i];
                        if (i < $(_this).boCheckBox('getCheckTexts').length - 1) {
                            content += ",";
                        }
                    }
                    if (content != "") {
                        $('#_checkBoxText-' + _options.namespace).html(content);
                    } else {
                        $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                    }
                    if (options.onChecked && typeof options.onChecked == "function") {
                        options.onChecked($(_this).boCheckBox('getCheckTexts'), $(_this).boCheckBox('getCheckValues'));
                    }
                });

                $('#_checkBox-' + _options.namespace).on("change", function () {
                    var content = "";
                    for (var i = 0; i < $(_this).boCheckBox('getCheckTexts').length; i++) {
                        content += $(_this).boCheckBox('getCheckTexts')[i];
                        if (i < $(_this).boCheckBox('getCheckTexts').length - 1) {
                            content += ",";
                        }
                    }
                    if (content != "") {
                        $('#_checkBoxText-' + _options.namespace).html(content);
                    } else {
                        $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                    }
                    if (_options.onChecked && typeof _options.onChecked == "function") {
                        _options.onChecked($(_this).boCheckBox('getCheckTexts'), $(_this).boCheckBox('getCheckValues'));
                    }
                });
            } else {
                $.ajax({
                    type: "POST",
                    url: _options.url,
                    data: _options.queryParams,
                    dataType: "json",
                    success: function (data) {
                        var _value = "value";
                        var _name = "_name";
                        if (_options.value) {
                            _value = _options.value;
                        }
                        if (_options.name) {
                            _name = _options.name;
                        }
                        for (var i = 0; i < data.rows.length; i++) {
                            $checkBoxContent.append('<li><input type="checkbox" id="' + _options.namespace + '-' + i + '" name="' + _options.namespace + '" value="' + data.rows[i][_value] + '" text="' + data.rows[i][_name] + '"/>' + data.rows[i][_name] + '</li>');
                        }
                        $ul.append($checkAll);
                        $ul.append($checkBoxContent);
                        $(_this).append($button);
                        $(_this).append($ul);
                        $('#checkAll-' + _options.namespace).on("change", function () {
                            if ($(this)[0].checked) {
                                $(_this).boCheckBox('checkAll');
                            } else {
                                $(_this).boCheckBox('enCheckAll');
                            }
                            var content = "";
                            for (var i = 0; i < $(_this).boCheckBox('getCheckTexts').length; i++) {
                                content += $(_this).boCheckBox('getCheckTexts')[i];
                                if (i < $(_this).boCheckBox('getCheckTexts').length - 1) {
                                    content += ",";
                                }
                            }
                            if (content != "") {
                                $('#_checkBoxText-' + _options.namespace).html(content);
                            } else {
                                $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                            }
                            if (_options.onChecked && typeof _options.onChecked == "function") {
                                _options.onChecked($(_this).boCheckBox('getCheckTexts'), $(_this).boCheckBox('getCheckValues'));
                            }
                        });

                        $('#_checkBox-' + _options.namespace).on("change", function () {
                            var content = "";
                            for (var i = 0; i < $(_this).boCheckBox('getCheckTexts').length; i++) {
                                content += $(_this).boCheckBox('getCheckTexts')[i];
                                if (i < $(_this).boCheckBox('getCheckTexts').length - 1) {
                                    content += ",";
                                }
                            }
                            if (content != "") {
                                $('#_checkBoxText-' + _options.namespace).html(content);
                            } else {
                                $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                            }
                            if (_options.onChecked && typeof _options.onChecked == "function") {
                                _options.onChecked($(_this).boCheckBox('getCheckTexts'), $(_this).boCheckBox('getCheckValues'));
                            }
                        });
                    }
                });
            }

        },
        checkAll: function () {
            var _this = this;
            var _options = _this.boCheckBox('getParams');
            $('#_checkBox-' + _options.namespace + ' input[type=checkbox]').each(function () {
                $(this).prop("checked", true);
            });
        },
        enCheckAll: function () {
            var _this = this;
            var _options = _this.boCheckBox('getParams');
            $('#_checkBox-' + _options.namespace + ' input[type=checkbox]').each(function () {
                $(this).prop("checked", false);
            });
        },
        getCheckTexts: function () {
            var _this = this;
            var texts = new Array();
            var _options = _this.boCheckBox('getParams');
            $('#_checkBox-' + _options.namespace + ' input[type=checkbox]').each(function () {
                if ($(this)[0].checked) {
                    texts.push($(this).attr("text"));
                }
            });
            return texts;
        },
        getCheckValues: function () {
            var _this = this;
            var texts = new Array();
            var _options = _this.boCheckBox('getParams');
            $('#_checkBox-' + _options.namespace + ' input[type=checkbox]').each(function () {
                if ($(this)[0].checked) {
                    texts.push($(this).val());
                }
            });
            return texts;
        },
        reload: function (options) {
            var _this = this;
            var _options = _this.boCheckBox('getParams', options);
            $('#_checkBoxText-' + _options.namespace).html("加载中...");
            $('#_checkBox-' + _options.namespace).empty();
            $.ajax({
                type: "POST",
                url: _options.url,
                data: _options.queryParams,
                dataType: "json",
                success: function (data) {
                    var _value = "value";
                    var _name = "_name";
                    if (_options.value) {
                        _value = _options.value;
                    }
                    if (_options.name) {
                        _name = _options.name;
                    }
                    var $checkBoxContent = $('#_checkBox-' + _options.namespace)
                    for (var i = 0; i < data.rows.length; i++) {
                        $checkBoxContent.append('<li><input type="checkbox" id="' + _options.namespace + '-' + i + '" name="' + _options.namespace + '" value="' + data.rows[i][_value] + '" text="' + data.rows[i][_name] + '"/>' + data.rows[i][_name] + '</li>');
                    }
                    $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                    $('#_checkBox-' + _options.namespace).on("change", function () {
                        var content = "";
                        for (var i = 0; i < $(_this).boCheckBox('getCheckTexts').length; i++) {
                            content += $(_this).boCheckBox('getCheckTexts')[i];
                            if (i < $(_this).boCheckBox('getCheckTexts').length - 1) {
                                content += ",";
                            }
                        }
                        if (content != "") {
                            $('#_checkBoxText-' + _options.namespace).html(content);
                        } else {
                            $('#_checkBoxText-' + _options.namespace).html("---请选择---");
                        }
                        if (options.onChecked && typeof options.onChecked == "function") {
                            options.onChecked($(_this).boCheckBox('getCheckTexts'), $(_this).boCheckBox('getCheckValues'));
                        }
                    });
                }
            });

        }

    };

    /**
     弹窗 dialog
     */
    var defaultDialogOptions = {}
    $.fn.boDialog = function (options) {
        if ($.fn.boDialog.methods[options]) {
            return $.fn.boDialog.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boDialog.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };

    $.fn.boDialog.methods = {
        getParams: function (options) {
            if (defaultDialogOptions[this[0].id] == undefined) {
                defaultDialogOptions[this[0].id] = $.extend({}, {
                    height: 450,
                    init: false,
                    title: "提示信息",
                    size: "sm",
                    open: false,
                    namespace: this[0].id
                }, options);
            } else {
                defaultDialogOptions[this[0].id] = $.extend({}, defaultDialogOptions[this[0].id], options);
            }
            return defaultDialogOptions[this[0].id];
        },
        init: function (options) {
            var _this = this;
            var _options = $(_this).boDialog('getParams', options);
            var _content = $(_this).html();
            $(_this).empty();
            $(_this).addClass("modal fade bs-example-modal-" + _options.size).attr('tabindex', '-1').attr('role', 'dialog').attr('aria-labelledby', 'dialog-' + _options.namespace);
            var $dialogDocument = $('<div class="modal-dialog modal-' + _options.size + '" role="document"></div>');
            var $dialogHeader = $('<div class="modal-header" style="padding:5px"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>')
            var $dialogTitle;
            if (_options.title) {
                $dialogTitle = $('<h4 class="modal-title" id="dialog-' + _options.namespace + '">' + _options.title + '</h4 >');
                $dialogHeader.append($dialogTitle);
            }
            var $dialogContent = $('<div class="modal-content" style="height:' + _options.height + 'px"></div>');
            var $dialogBody = $('<div class="modal-body" style="height:' + (_options.height - 50) + 'px;overflow:auto"></div>');
            $dialogContent.append($dialogHeader);
            $dialogBody.append(_content);
            $dialogContent.append($dialogBody);
            $dialogDocument.append($dialogContent);
            $(_this).append($dialogDocument);
            $('#dialog-' + _options.namespace).modal({
                backdrop: 'static',
                keyboard: false,
                show: _options.open
            });
            if (typeof _options.onLoadSuccess == "function") {
                _options.onLoadSuccess();
            }
            $(_this).boDialog('getParams', {init: true});
        },
        open: function () {
            var _this = this;
            var _options = _this.boDialog('getParams', {open: true});
            $(_this).modal({
                backdrop: 'static',
                keyboard: false,
                show: _options.open
            });
        },
        close: function () {
            var _this = this;
            var _options = _this.boDialog('getParams', {open: false});
            $(_this).modal({
                backdrop: 'static',
                keyboard: false,
                show: _options.open
            });
        }
    };
    /**
     * 基于Bootstrap下拉通用插件
     * @author wjm
     * @date 2017-04-27
     * 用法:
     * $('#xxx').getBootstrapSelect({
     *  url:,//后台json请求地址,可用通用字典action [${ctx}/pages/quyjsonpages.action?qid=DIC_GENERAL&typeid=__]
     *  hasnull:false,//是否增加一个空选择
     *  name:,//select name
     *  cache:true,
     *  selectValueName:value,//默认GCODE
     *  selectKeyName:key,//默认GNAME
     *  onLoadSuccess:function(data){//加载成功后触发的方法
     *
     *  },
     *  onSelected:function(data){//加载成功后触发的方法
     *
     *  }
     * });
     */

    $.fn.boSelect = function (option) {
        if ($.fn.boSelect.methods[option]) {
            return $.fn.boSelect.methods[option].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof option === 'object' || !option) {
            return $.fn.boSelect.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };

    $.fn.boSelect.methods = {
        init: function (option) {
            var v_selector = this;
            var v_cache = true;
            if (option.cache != undefined && option.cache != '') {
                v_cache = option.cache
            }
            ;
            var v_url = option.url, v_value = "", v_key = "", hasnull = false;
            if (option.selectValueName != undefined && option.selectValueName != '') {
                v_value = option.selectValueName;
            } else {
                v_value = 'GCODE';
            }
            if (option.selectKeyName != undefined && option.selectKeyName != '') {
                v_key = option.selectKeyName;
            } else {
                v_key = 'GNAME';
            }
            if (option.hasnull != undefined || option.selectKeyName != '') {
                hasnull = option.hasnull;
            }
            if (v_cache) {
                if ($(v_selector).attr('rownum') == undefined || $(v_selector).attr('rownum') == '0') {
                    $(v_selector).attr("class", "form-control");
                    if (option.name != undefined && option.name != '') {
                        $(v_selector).attr("name", option.name);
                    } else {
                        if (v_selector.selector.substr(1) && v_selector.selector.substr(1) != '') {
                            $(v_selector).attr("name", v_selector.selector.substr(1));
                        } else {
                            $(v_selector).attr("name", v_selector[0].id);
                        }
                    }
                    $.ajax({
                        type: "POST",
                        url: v_url,
                        dataType: "json",
                        success: function (data) {
                            $(v_selector).attr("rownum", data.rows.length);
                            var oid = 0;
                            if (hasnull) {
                                $(v_selector).append('<option value="" selected="">---请选择---</option>');
                                oid++;
                            }
                            for (var i = 0; i < data.rows.length; i++) {
                                $(v_selector).append('<option  value="' + data.rows[i][v_value] + '">' + data.rows[i][v_key] + '</option>');
                                oid++;
                            }
                            if (typeof option.onLoadSuccess == "function") {
                                option.onLoadSuccess(data);
                            }

                            $(v_selector).change(function () {
                                if (typeof option.onSelected == "function") {
                                    option.onSelected();
                                }
                            });
                        }
                    });
                }
            } else {
                $(v_selector).empty();
                $(v_selector).attr("class", "form-control");
                if (option.name != undefined && option.name != '') {
                    $(v_selector).attr("name", option.name);
                } else {
                    $(v_selector).attr("name", v_selector.substr(1));
                }
                $.ajax({
                    type: "POST",
                    url: v_url,
                    dataType: "json",
                    success: function (data) {
                        $(v_selector).attr("rownum", data.rows.length);
                        var oid = 0;
                        if (hasnull) {
                            $(v_selector).append('<option value="" selected="">---请选择---</option>');
                            oid++;
                        }
                        for (var i = 0; i < data.rows.length; i++) {
                            $(v_selector).append('<option  value="' + data.rows[i][v_value] + '">' + data.rows[i][v_key] + '</option>');
                            oid++;
                        }
                        if (typeof option.onLoadSuccess == "function") {
                            option.onLoadSuccess(data);
                        }

                        $(v_selector).change(function () {
                            if (typeof option.onSelected == "function") {
                                option.onSelected();
                            }
                        });
                    }
                });
            }
        },
        setValue: function (value) {
            var v_id = $(this).attr("id");
            $("#" + v_id + ' option[selected]').removeAttr("selected");
            $("#" + v_id + ' option[value="' + value + '"]').prop("selected", 'selected');
        }
    }


    /**
     form表单
     **/
    $.fn.boForm = function (options) {
        if ($.fn.boForm.methods[options]) {
            return $.fn.boForm.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boForm.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    }
    $.fn.boForm.methods = {
        init: function () {
        },
        reset: function (options) {
            var _this = this;
            var selector = this.selector;
            $(selector + " input[type=hidden]").each(function () {
                $(selector + " input[id=" + $(this).attr('id') + "]").val('');
            });
            $(selector + " input[type=text]").each(function () {
                $(selector + " input[id=" + $(this).attr('id') + "]").val('');
            });
            $(selector + " input[type=radio]").each(function () {
                $(selector + " input[id=" + $(this).attr('id') + "]").removeAttr("checked");
            });
            $(this.selector + " select").each(function () {
                $(selector + " select[id=" + $(this).attr('id') + "] option:first").prop("selected", 'selected');
            });
            $(this.selector + " select").each(function () {
                $(selector + " select[id=" + $(this).attr('id') + "] option:first").prop("selected", 'selected');
            });
        },
        setData: function (options) {
            var _this = this;
            var selector = this.selector;
            var setType = "id";
            if (options.isId != undefined || options.isId != '') {
                if (options.isId) {
                    setType = "id";
                } else {
                    setType = "name";
                }
            }
            $(selector + " input[type=text]").each(function () {
                if ($(this).attr(setType) != undefined && options.json[$(this).attr(setType).toUpperCase()] != null) {
                    $(this).val(options.json[$(this).attr(setType).toUpperCase()]);
                }
            });
            var tmpList = new Array();//临时遍历
            $(selector + " input[type=checkbox]").each(function () {
                var isSet = true;
                var key = '';
                if (setType == 'id') {
                    key = $(this).attr(setType).toUpperCase().split("-")[0];
                } else {
                    key = $(this).attr(setType).toUpperCase();
                }
                if (key != 'CHECKALL') {
                    for (var i = 0; i < tmpList.length; i++) {
                        if (tmpList[i] == key) {
                            isSet = false;
                            break;
                        }
                    }
                } else {
                    isSet = false;
                }
                if (isSet) {
                    var content='';
                    if(options.json[key]!=null){
                        var tmpValue = options.json[key].split(",");
                        for (var j = 0; j < tmpValue.length; j++) {
                            $('input[type=checkbox][name="'+$(this).attr('name')+'"][value="'+tmpValue[j]+'"]').prop("checked", true);
                            if(content==''){
                                content=$('input[type=checkbox][name="'+$(this).attr('name')+'"][value="'+tmpValue[j]+'"]').attr("text");
                            }else{
                                content=content+","+$('input[type=checkbox][name="'+$(this).attr('name')+'"][value="'+tmpValue[j]+'"]').attr("text");
                            }
                        }
                        $('#_checkBoxText-'+$(this).attr(setType).split("-")[0]).html(content);
                    }
                     tmpList.push(key);
                }
            });
            $(selector + " input[type=hidden]").each(function () {
                if ($(this).attr(setType) != undefined && options.json[$(this).attr(setType).toUpperCase()] != null) {
                    $(this).val(options.json[$(this).attr(setType).toUpperCase()]);
                }
            });
            $(selector + " select").each(function () {
                if($(this).attr('stype')==undefined){
                    if ($(this).attr(setType) != undefined && options.json[$(this).attr(setType).toUpperCase()] != null) {
                        $(this).boSelect("setValue", options.json[$(this).attr(setType).toUpperCase()]);
                    } else {
                        $('#' + this.id + ' option:first').prop("selected", 'selected');
                    }
                }
            });
            $(selector + " input[type=radio]").each(function () {
                if (options.json[$(this).attr('name').toUpperCase()] == $(this).val()) {
                    $('#' + $(this).attr('id')).prop("checked", 'checked');
                }
            });
            $(selector + " textarea").each(function () {
                if ($(this).attr(setType) != undefined && options.json[$(this).attr(setType).toUpperCase()] != null) {
                    $('#' + $(this).attr('id')).val(options.json[$(this).attr(setType).toUpperCase()]);
                }
            });
            if (typeof onLoadSuccess == "function") {
                onLoadSuccess();
            }
        }
    }


    /**
     form表单
     **/
    $.fn.boTable = function (options) {
        if ($.fn.boTable.methods[options]) {
            return $.fn.boTable.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boTable.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    }

    $.fn.boTable.methods = {
        init: function (options) {
            var _this = this;
            if ($(_this).html().length > 0) return;
            $(_this).append('<div id="boTableLoading-' + _this[0].id + '" class="modal fade bs-example-modal-sm" tabindex="-1"' +
                'role="dialog" aria-labelledby="mySmallModalLabel">' +
                '<div class="modal-dialog modal-sm" role="document"><div class="modal-content">' +
                '<div style="text-align: center"><img src="' + resourse.loadingImg + '" style="width:30px">正在处理中，请稍等...<div></div></div></div>');
            $('#boTableLoading-' + _this[0].id).modal({backdrop: 'static', keyboard: false});
            $('#boTableLoading-' + _this[0].id).modal('hide');
            var $topFormDiv = $('<div style="width:100%;height:100%;overflow:auto"></div>');
            var $buttonDiv = $('<div style="overflow:auto"><div class="btn-group" role="group" aria-label="anz"></div></div>');
            var $form = $('<form id=' + options.formId + ' method=' + options.method + '></form>');
            for (var i = 0; i < options.table.length; i++) {
                var _scene = '';
                if (options.table[i].scene) {
                    _scene = options.table[i].scene;
                } else {
                    _scene = 'primary';
                }
                var $tableTitel = $('<div id="tableTitle-' + i + '" class="panel panel-' + _scene + '" style="margin-bottom:5px"><div class="panel-heading">' + options.table[i].title + '</div><div>');
                var $table = $('<table id="table-' + i + '" class="table"><tbody></tbody></table>');
                var hiddenNum = 0;
                for (var j = 0; j < options.table[i].content.length; j++) {//处理hidden
                    if (options.table[i].content[j].hidden == true) {
                        hiddenNum++;
                        var _name = options.table[i].content[j].id;
                        if (options.table[i].content[j].name) {
                            _name = options.table[i].content[j].name;
                        }
                        $table.append('<input type="hidden" id="' + options.table[i].content[j].id + '" name="' + _name + '"/>');
                    }
                }
                var $tr = '', jsq = 0, tdNum = 0;
                for (var j = 0; j < options.table[i].content.length; j++) {
                    if (tdNum == 0 || tdNum == options.table[i].colnum || tdNum > options.table[i].colnum - 1) {//判断换行时机
                        tdNum = 0;
                        $tr = $('<tr></tr>');
                    }
                    if (options.table[i].content[j].hidden == undefined || options.table[i].content[j].hidden == false) {
                        var _title = '';
                        if (options.table[i].content[j].required) {
                            _title = '<span style="color:red">' + options.table[i].content[j].title + '&nbsp;&nbsp;*</span>'
                        } else {
                            _title = options.table[i].content[j].title;
                        }
                        var _required = "";
                        if (options.table[i].content[j].required) {
                            _required = 'required="true"'
                        }

                        var $th = $('<th>' + _title + '</th>');
                        tdNum = tdNum + 1;
                        var colspanHtml = '';
                        if (options.table[i].content[j].colspan != undefined) {
                            colspanHtml = 'colspan="' + options.table[i].content[j].colspan + '"';
                            tdNum = tdNum + parseInt(options.table[i].content[j].colspan);
                        } else {
                            tdNum = tdNum + 1;
                        }
                        var $td = $('<td ' + colspanHtml + '></td>');
                        if (options.table[i].content[j].formatter == undefined) {
                            var _assType = 'text';
                            if (options.table[i].content[j].assType) {//控件类型
                                _assType = options.table[i].content[j].assType
                            }
                            if (_assType == 'radio') {//单选控件
                                var radioHtml = '';
                                $(options.table[i].content[j].data).each(function () {
                                    var _radioThis = this, _checkedHtml = '';
                                    if (_radioThis.checked) {
                                        _checkedHtml = 'checked="checked"';
                                    }

                                    radioHtml += _radioThis.title + '<input ' + _required + ' type="radio" ' + _checkedHtml + ' name="' + options.table[i].content[j].name + '" id="' + options.table[i].content[j].id + '" value="' + options.table[i].content[j].value + '">';
                                });
                                $td.append(radioHtml);
                            } else if (_assType == 'text') {
                                var _name = options.table[i].content[j].id;
                                if (options.table[i].content[j].name) {
                                    _name = options.table[i].content[j].name;
                                }
                                var _placeholderHtml = '';
                                if (options.table[i].content[j].placeholder) {
                                    _placeholderHtml = 'placeholder="' + options.table[i].content[j].placeholder + '"';
                                }
                                var $ass = $('<input ' + _required + ' type="text" id="' + options.table[i].content[j].id + '" name="' + _name + '" ' + _placeholderHtml + ' class="form-control">');
                                $td.append($ass);
                            } else if (_assType == 'select') {
                                var $select = $('<select ' + _required + ' id="' + options.table[i].content[j].id + '"></select>');
                                if (options.table[i].content[j].data) {
                                    if (options.table[i].content[j].name) {
                                        options.table[i].content[j].data.name = options.table[i].content[j].name;
                                    }
                                    $select.boSelect(options.table[i].content[j].data);
                                }
                                $td.append($select);
                            } else if (_assType == 'checkBox') {
                                var $checkbox = $('<div ' + _required + ' id="' + options.table[i].content[j].id + '" name="' + _name + '"></div>');
                                if (options.table[i].content[j].data) {
                                    $checkbox.boCheckBox(options.table[i].content[j].data);
                                }
                                $td.append($checkbox);
                            }
                        } else {
                            $td.append(options.table[i].content[j].formatter());
                        }
                        $tr.append($th).append($td);
                        $table.append($tr);

                    }
                    if (j == options.table[i].content.length - 1 && tdNum < options.table[i].colnum) {
                        var bTdHtml = '';
                        for (var ii = tdNum; ii < options.table[i].colnum; ii++) {
                            bTdHtml += '<td></td>';
                        }
                        $tr.append(bTdHtml);
                    }
                }
                $tableTitel.append($table);
                $form.append($tableTitel);
            }
            $topFormDiv.append($form);
            _this.append($topFormDiv);
            $(options.toolbar).each(function (i, o) {
                var _btnThis = this;
                var $btn = $('<button type="button" id="' + _btnThis.id + '" class="btn btn-' + _btnThis.btnScene + '">' + _btnThis.text + '</button>');
                if (_btnThis.handler && typeof _btnThis.handler == "function") {
                    $btn.click(function () {
                        _btnThis.handler();
                    });
                }
                $buttonDiv.append($btn);
            });
            _this.append($buttonDiv);
            if (typeof options.onLoadSuccess == "function") {
                options.onLoadSuccess();
            }
        },
        loading: function () {
            $('#boTableLoading-' + this[0].id).modal('show');
        },
        loaded: function () {
            $('#boTableLoading-' + this[0].id).modal('hide');
        }
    }

    /**
     数据表单
     **/
    var defaultDataGridParams = {}, tmpBoDataGridData = {};
    $.fn.boDataGrid = function (options) {
        if ($.fn.boDataGrid.methods[options]) {
            return $.fn.boDataGrid.methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof options === 'object' || !options) {
            return $.fn.boDataGrid.methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };

    $.fn.boDataGrid.methods = {
        getParams: function (options) {
            if (defaultDataGridParams[this[0].id] == undefined) {
                defaultDataGridParams[this[0].id] = $.extend({}, {
                    init: false,
                    namespace: this[0].id,
                    width: '100%',
                    height: '100%',
                    scene: 'primary',
                    pagesList: [10, 30, 50, 100],
                    rows: 10,
                    page: 1,
                    total: ''
                }, options);
            }
            return defaultDataGridParams[this[0].id];
        },
        init: function (options) {
            var _this = this;
            var _options = _this.boDataGrid('getParams', options);

            if (_options.init == false) {
                $(_this).attr('style', 'width:100%;height:100%');
                $(_this).attr('class', 'panel panel-primary');
                $(_this).append('<div id="boDataGridLoading-' + _options.namespace + '" class="modal fade bs-example-modal-sm" tabindex="-1"' +
                    'role="dialog" aria-labelledby="mySmallModalLabel">' +
                    '<div class="modal-dialog modal-sm" role="document"><div class="modal-content">' +
                    '<div style="text-align: center"><img src="' + resourse.loadingImg + '" style="width:30px">正在加载，请稍等...<div></div></div></div>');
                $('#boDataGridLoading-' + _options.namespace).modal({backdrop: 'static', keyboard: false});

                if (_options.title) {
                    $(_this).append('<div class="panel-heading">' + _options.title + '</div>');
                }
                if (_options.toolbar != undefined && _options.toolbar.length > 0) {
                    var $toolbar = $('<div class="panel-body" style="padding:3px"></div>');
                    var $toolbarButton = $('<div class="btn-group" role="group" aria-label=""></div>');
                    $(_options.toolbar).each(function (i, o) {
                        var _btnScene = 'default';
                        if (o.btnScene) {
                            _btnScene = o.btnScene;
                        }
                        if (o.type) {
                            if (o.type == 'list') {
                                $btnlist = $('<div class="btn-group" role="group"></div>');
                                $topBtnList = $('<button type="button" class="btn btn-' + _btnScene + ' dropdown-toggle" ' +
                                    'data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
                                    o.text + '&nbsp;&nbsp;<span class="caret"></span></button>');
                                $ul = $('<ul class="dropdown-menu"></ul>');
                                $(o.btns).each(function (i, o) {
                                    $li = $('<li><a href="#">' + o.text + '</li>');
                                    $ul.append($li);
                                    $li.click(function () {
                                        o.handler()
                                    });
                                });
                                $btnlist.append($topBtnList);
                                $btnlist.append($ul);
                                $toolbarButton.append($btnlist);
                            }
                        } else {
                            $btn = $('<button type="button" class="btn btn-' + _btnScene + '">' + o.text + '</button>');
                            $toolbarButton.append($btn);
                            if (o.handler) {
                                $btn.click(function () {
                                    o.handler()
                                });
                            }
                        }
                    });
                    $toolbar.append($toolbarButton);
                    $(_this).append($toolbar);
                }

                var $table = $('<table id="dgTable-' + _options.namespace + '" class="table table-bordered table-hover"><tbody></tbody></table>'),
                    $tableTitle;
                if (_options.tableTitle && _options.tableTitle != '') {
                    $tableTitle = $(_options.tableTitle);
                    $tableTitle.addClass("success");
                } else {
                    $tr = $('<tr id="titleTr-' + _options.namespace + '" class="success"></tr>');
                    if (_options.checkbox) {
                        var $thCheck = $('<th width="10px"></th>');
                        var $checkBox = $('<input id="allCheckBox-' + _options.namespace + '" type="checkbox"/>');
                        $thCheck.append($checkBox);
                        $tr.append($thCheck);
                        $checkBox.change(function () {
                            $('#allCheckBox-' + _options.namespace)[0].checked
                            $('#tableData-' + _options.namespace + ' input[type="checkbox"]').each(function () {

                            });

                        });
                    }
                    for (var i = 0; i < _options.columns[0].length; i++) {
                        var thWidth = '100px';
                        if (_options.columns[0][i].width && _options.columns[0][i].width != '') {
                            thWidth = _options.columns[0][i].width;
                        }
                        var $th = $('<th width="' + thWidth + '" title="' + _options.columns[0][i].title + '"><div style="width:' + thWidth + ';overflow:hidden">' + _options.columns[0][i].title + '</div></th>')
                        if (_options.columns[0][i].hidden) {
                            $th.hide();
                        }
                        $tr.append($th);
                    }
                    $tableTitle = $tr;
                }
                $table.append($tableTitle);
                $dataDiv = $('<tbody id="tableData-' + _options.namespace + '"></tbody>');
                $table.append($dataDiv);
                var $tableDiv = $('<div style="overflow:auto"></div>');
                $tableDiv.append($table);
                $(_this).append($tableDiv);
                $buttonFooter = $('<div class="panel-footer">' +
                    '<div style="float:right" class="bg-danger" id="pageContent-' + _options.namespace + '"></div>' +
                    '</div>');
                var pagesListHtml = '<div id="fyList-' + _options.namespace + '" class="btn-group dropup" role="group">' +
                    '<button id="_nowPageBtn-' + _options.namespace + '" class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown" ' +
                    'aria-haspopup="true" aria-expanded="false">';

                for (var i = 0; i < _options.pagesList.length; i++) {
                    if (i == 0) {
                        pagesListHtml += _options.pagesList[i] + '<span class="caret"></span></button><ul  class="dropdown-menu">';
                    }
                    pagesListHtml += '<li><a id="pagesNo-"' + _options.pagesList[i] + '>' + _options.pagesList[i] + '</a></li>';
                    if (i == _options.pagesList.length - 1) {
                        pagesListHtml += '</ul><div>';
                    }
                }

                $fyButtons = $('<div class="btn-group-sm" role="group" aria-label="fy">' +
                    '<button type="button" class="btn btn-default" id="theTopPage-' + _options.namespace + '"><span class="glyphicon glyphicon-step-backward" aria-hidden="true"></button>' +
                    '<button type="button" class="btn btn-default" id="topPage-' + _options.namespace + '"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></button>' +
                    '<button type="button" class="btn btn-default" id="nowPage-' + _options.namespace + '">1</button>' +
                    '<button type="button" class="btn btn-default" id="lastPage-' + _options.namespace + '"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></button>' +
                    '<button type="button" class="btn btn-default" id="theLastPage-' + _options.namespace + '"><span class="glyphicon glyphicon-step-forward" aria-hidden="true"></button>' +
                    pagesListHtml +
                    '</div>');
                $buttonFooter.append($fyButtons);
                $(_this).append($buttonFooter);
                if (typeof _options.onBeforeLoad == "function") {
                    if (_options.onBeforeLoad() != false) {
                        _this.boDataGrid('queryData');
                    }
                } else {
                    _this.boDataGrid('queryData');
                }
                $('li').on('click', function () {
                    defaultDataGridParams.rows = $(this)[0].textContent;
                    $('#_nowPageBtn-' + _options.namespace).html($(this)[0].textContent + '<span class="caret"></span>');
                });

                $('#theTopPage-' + _options.namespace + ',#topPage-' + _options.namespace + ',#lastPage-' + _options.namespace + ',#theLastPage-' + _options.namespace + '').on('click', function () {
                    if (defaultDataGridParams[_this[0].id].page == undefined) {
                        defaultDataGridParams[_this[0].id].page = '1'
                    }
                    if ($(this).attr('id') == 'theTopPage-' + _options.namespace) {
                        defaultDataGridParams[_this[0].id].page = 1;
                    } else if ($(this).attr('id') == 'topPage-' + _options.namespace) {
                        if (defaultDataGridParams[_this[0].id].page != '1') {
                            defaultDataGridParams[_this[0].id].page = eval(defaultDataGridParams[_this[0].id].page - 1);
                        }
                    } else if ($(this).attr('id') == 'lastPage-' + _options.namespace) {
                        var matchPage = Math.ceil(defaultDataGridParams[_this[0].id].total / defaultDataGridParams[_this[0].id].rows);
                        if (defaultDataGridParams[_this[0].id].page < matchPage) {
                            defaultDataGridParams[_this[0].id].page = eval(defaultDataGridParams[_this[0].id].page + 1);
                        }
                    } else if ($(this).attr('id') == 'theLastPage-' + _options.namespace) {
                        var matchPage = Math.ceil(defaultDataGridParams[_this[0].id].total / defaultDataGridParams[_this[0].id].rows);
                        defaultDataGridParams[_this[0].id].page = matchPage;
                    }
                    $('#nowPage-' + _options.namespace).html(defaultDataGridParams[_this[0].id].page);
                    _this.boDataGrid('queryData');
                });
                _options.init = true;
            }
        },
        queryData: function () {
            var _this = this;
            var _options = _this.boDataGrid('getParams');
            $('#boDataGridLoading-' + _options.namespace).modal('show');
            var $tableData = $('#tableData-' + _options.namespace);
            $('#tableData-' + _options.namespace + ' tr').remove();
            var $dataDiv = $('#tableData-' + _options.namespace);
            var _nowRows = $('#_nowPageBtn-' + _options.namespace).html().split("<")[0];
            if (_nowRows == undefined || _nowRows == '') {
                _nowRows = _options.rows
            } else {
                if (isNaN(_nowRows)) {
                    var _nowRows = $('#_nowPageBtn-' + _options.namespace).html(_options.rows + '<span class="caret"></span>');
                    _nowRows = _options.rows
                }
                _options.rows = _nowRows;
            }
            var params = 'rows=' + _nowRows + '&page=' + _options.page;
            if (_options.formId && _options.formId != '' && $('#' + _options.formId).serialize().length > '0') {
                params = params + '&' + $('#' + _options.formId).serialize();
            }
            if (_options.queryParams) {
                params = params + '&' + $.param(_options.queryParams);
            }
            $.post(_options.url, params,
                function (data) {
                    tmpBoDataGridData[_this[0].id] = data;
                    defaultDataGridParams[_this[0].id].total = data.total;
                    $('#pageContent-' + _options.namespace).html('当前显示第' + _options.page + '页/每页显示' + _nowRows + '条数据/共' + defaultDataGridParams[_this[0].id].total + '条数据');
                    for (var i = 0; i < data.rows.length; i++) {
                        $tr = $('<tr index="index-' + i + '"></tr>');
                        if (_options.checkbox) {
                            var $dataCheckBox = $('<td><input id="dataCheckBox-' + _options.namespace + '-' + i + '" type="checkbox"/></td>');
                            $tr.append($dataCheckBox);
                        }
                        var thWidth = '100px';
                        for (var j = 0; j < _options.columns[0].length; j++) {
                            if (_options.columns[0][j].width && _options.columns[0][j].width != '') {
                                thWidth = _options.columns[0][j].width;
                            }
                            if (_options.columns[0][j].formatter && typeof _options.columns[0][j].formatter == "function") {
                                var _val = _options.columns[0][j].formatter(data.rows[i][_options.columns[0][j].field], data.rows[i])
                                var $td = $('<td><div style="width:' + thWidth + ';overflow:hidden">' + _val + '</div></td>');
                                if (_options.columns[0][j].hidden) {
                                    $td.hide();
                                }
                                $tr.append($td);
                            } else {
                                var tableValue = data.rows[i][_options.columns[0][j].field];
                                if (tableValue == null) {
                                    tableValue = '';
                                }
                                var $td = $('<td title="' + tableValue + '"><div style="width:' + thWidth + ';overflow:hidden">' + tableValue + '</div></td>');
                                if (_options.columns[0][j].hidden) {
                                    $td.hide();
                                }
                                $tr.append($td);
                            }
                        }
                        $dataDiv.append($tr);
                    }
                    $tableData.append($dataDiv);
                    $('#boDataGridLoading-' + _options.namespace).modal('hide');
                    if (typeof _options.onLoadSuccess == "function") {
                        _options.onLoadSuccess(data);
                    }

                    $('#tableData-' + _options.namespace + ' tr').click(function () {
                        var _index = $(this).attr('index').slice(6);
                        var _row = tmpBoDataGridData[_this[0].id].rows[_index];
                        if (typeof _options.onClickRow == "function") {
                            _options.onClickRow(_index, _row);
                        }
                    });

                    $('#tableData-' + _options.namespace + ' tr').dblclick(function () {
                        var _index = $(this).attr('index').slice(6);
                        var _row = tmpBoDataGridData[_this[0].id].rows[_index];
                        if (typeof _options.onDblClickRow == "function") {
                            _options.onDblClickRow(_index, _row);
                        }
                    });
                }, "json");
        },

        loading: function () {
            $('#boDataGridLoading-' + _options.namespace).modal('show');
        },
        loaded: function () {
            $('#boDataGridLoading-' + _options.namespace).modal('hide');
        },
        getRows: function () {
            return tmpBoDataGridData.data.rows;
        }

    }


    $.extend({
    /**
     * 联动下拉框
     * @param option={
     * id:["xxx","xxx"],
     * name:["xxx","xxx"],
     * url:["______","______"],
     * hasnull:false,
     * valueName:"",默认GNAME
     * valueKey:"",默认GCODE
     */
        boSelectLinkage: function (option) {
            var v_value, v_key, hasnull = false, async = true;
            var ajaxData = new Array();

            if (option.async != undefined) {
                async = option.async;
            }
            if (option.valueName != undefined && option.valueName != '') {
                v_value = option.valueName;
            } else {
                v_value = 'GCODE';
            }
            if (option.valueKey != undefined && option.valueKey != '') {
                v_key = option.valueKey;
            } else {
                v_key = 'GNAME';
            }
            if (option.hasnull != undefined || option.selectKeyName != '') {
                hasnull = option.hasnull;
            }
            if (option.id.length != option.url.length) {
                alert("getBootstrapLinkageSelecte控件ID数组与url数组数量不一致");
                return;
            } else {
                if (option.json==undefined) {
                    $.ajax({
                        type: "POST",
                        url: option.url[0],
                        dataType: "json",
                        success: function (data) {
                            ajaxData[0] = data;
                            var oid = 0;
                            $("#" + option.id[0]).empty();
                            $("#" + option.id[0]).attr("class", "form-control");
                            $("#" + option.id[0]).attr("rownum", data.rows.length);
                            if (option.name) {
                                $("#" + option.id[0]).attr("name", option.name[0]);
                            } else {
                                $("#" + option.id[0]).attr("name", option.id[0]);
                            }
                            if (hasnull) {
                                $("#" + option.id[0]).append('<option  value="" selected="">---请选择---</option>');
                                oid++;
                            }
                            for (var ii = 0; ii < data.rows.length; ii++) {
                                $("#" + option.id[0]).append('<option pid="' + data.rows[ii].PID + '"  value="' + data.rows[ii][v_value] + '">' + data.rows[ii][v_key] + '</option>');
                                oid++;
                            };
                        }
                    });
                    for (var j = 1; j < option.id.length; j++) {
                        $("#" + option.id[j]).empty();
                        $("#" + option.id[j]).attr("class", "form-control");
                        $("#" + option.id[j]).attr("stype", "link-"+j);
                        if (option.name) {
                            $("#" + option.id[j]).attr("name", option.name[j]);
                        } else {
                            $("#" + option.id[j]).attr("name", option.id[j]);
                        }
                        $("#" + option.id[j]).append('<option value="" selected="">---请选择---</option>');
                    }
                    //绑定联动事件
                    for (var rn = 0; rn < option.id.length - 1; rn++) {
                        $("#" + option.id[rn]).change(function () {
                            var selectValue = $(this).val();
                            if (selectValue != "") {
                                var _i = 0, urlParam = "&pid=" + selectValue;
                                for (var i = 0; i < option.id.length; i++) {
                                    if (option.id[i] == $(this).attr('id')) {
                                        _i = i + 1;
                                        break;
                                    }
                                }
                                $("#" + option.id[_i]).empty();
                                $("#" + option.id[_i]).append('<option  value="" selected="">加载中...</option>');
                                $.ajax({
                                    type: "POST",
                                    url: option.url[_i] + urlParam,
                                    dataType: "json",
                                    success: function (data) {
                                        ajaxData[_i] = data;
                                        var oid = 0;
                                        $("#" + option.id[_i]).empty();
                                        if (hasnull) {
                                            $("#" + option.id[_i]).append('<option  value="" selected="">---请选择---</option>');
                                            oid++;
                                        }
                                        for (var ii = 0; ii < data.rows.length; ii++) {
                                            $("#" + option.id[_i]).append('<option pid="' + data.rows[ii].PID + '" value="' + data.rows[ii][v_value] + '">' + data.rows[ii][v_key] + '</option>');
                                            oid++;
                                        }
                                        $("#" + option.id[_i]).attr("rownum",data.rows.length);
                                        return true;
                                    }
                                });
                            }
                        });
                    }
                } else {
                    for (var i = 0; i < option.id.length; i++) {
                        $("#" + option.id[i]).empty();
                        $("#" + option.id[i]).attr("class", "form-control");
                        var tmpName='';
                        if (option.name) {
                           $("#" + option.id[i]).attr("name", option.name[i]);
                            tmpName=option.name[i].toUpperCase()
                        } else {
                            tmpName=$("#" + option.id[i]).attr("name", option.id[i]);
                            tmpName=option.id[i].toUpperCase()
                        }
                        var loopType=true;
                        if(i>0){
                            if(option.url[i].split("=")[3]==undefined){
                                loopType=false;
                            }
                        }
                       if(loopType){
                           $.ajax({
                               type: "POST",
                               url: option.url[i],
                               dataType: "json",
                               async: false,
                               success: function (data) {
                                   ajaxData[i] = data;
                                   var oid = 0;
                                   if (hasnull) {
                                       $("#" + option.id[i]).append('<option  value="">---请选择---</option>');
                                       oid++;
                                   }
                                   for (var ii = 0; ii < data.rows.length; ii++) {
                                       if(data.rows[ii][v_value]==option.json[tmpName]){
                                           $("#" + option.id[i]).append('<option pid="' + data.rows[ii].PID + '"  value="' + data.rows[ii][v_value] + '" selected="selected">' + data.rows[ii][v_key] + '</option>');
                                       }else{
                                           $("#" + option.id[i]).append('<option pid="' + data.rows[ii].PID + '"  value="' + data.rows[ii][v_value] + '">' + data.rows[ii][v_key] + '</option>');
                                       }

                                       oid++;
                                   }
                                   $("#" + option.id[i]).attr("rownum", data.rows.length);
                               }
                           });
                       }else{
                           if (hasnull) {
                               $("#" + option.id[i]).append('<option  value="">---请选择---</option>');
                               oid++;
                           }
                       }

                        //绑定联动事件
                        for (var rn = 0; rn < option.id.length - 1; rn++) {
                            $("#" + option.id[rn]).change(function () {
                                var selectValue = $(this).val();
                                var _i = 0;
                                for (var i = 0; i < option.id.length; i++) {
                                    if (option.id[i] == $(this).attr('id')) {
                                        _i = i + 1;
                                        break;
                                    }
                                }
                                $("#" + option.id[_i]).empty();
                                var oid = 0;
                                $("#" + option.id[_i]).append('<option rownum="' + oid + '" value="" selected="">---请选择---</option>');
                                oid++;
                                for (var ii = 0; ii < ajaxData[_i].rows.length; ii++) {
                                    if (ajaxData[_i].rows[ii].PID == selectValue) {
                                        $("#" + option.id[_i]).append('<option pid="' + ajaxData[_i].rows[ii].PID + '" rownum="' + oid + '" value="' + ajaxData[_i].rows[ii][v_value] + '">' + ajaxData[_i].rows[ii][v_key] + '</option>');
                                        oid++;
                                    }
                                }
                            });
                        }
                    }
                }

            }
        }
    });

})(jQuery);