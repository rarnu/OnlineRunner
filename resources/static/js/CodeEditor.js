function CodeEditor(baseobj, codeParams, outputParams) {

    var that = this;
    this.width = baseobj[0].offsetWidth;
    this.height = baseobj[0].offsetHeight;

    if (!codeParams.hasOwnProperty("isDark")) {
        this.isDark = false;
        codeParams["theme"] = "default";
    } else {
        this.isDark = codeParams["isDark"];
        codeParams["theme"] = this.isDark ? "darcula" : "default";
    }

    var border = element("div");
    attr(border, {class: this.isDark ? "div-code-border-dark" : "div-code-border"});
    size(border, {width: this.width + "px", height: this.height + "px"});
    baseobj[0].appendChild(border);

    var bar = element("div");
    attr(bar, {class: "div-code-bar"});
    border.appendChild(bar);
    var type = element("div");
    attr(type, {class: "div-code-type"});
    bar.appendChild(type);

    var language = element("span");
    attr(language, {class: "span-code-type"});
    type.appendChild(language);

    var run = element("div");
    attr(run, {class: "div-code-run"});
    bar.appendChild(run);

    var imgParam = element("img");
    attr(imgParam, {
        class: "img-oper",
        src: this.isDark ? "../static/images/code-param-dark.png" : "../static/images/code-param.png"
    });
    imgParam.onclick = toggleParam;
    run.appendChild(imgParam);

    var spanParam = element("span");
    attr(spanParam, {class: "span-oper"});
    spanParam.innerHTML = "param";
    spanParam.onclick = toggleParam;
    run.appendChild(spanParam);

    var imgRun = element("img");
    attr(imgRun, {
        class: "img-oper",
        src: this.isDark ? "../static/images/code-run-dark.png" : "../static/images/code-run.png"
    });
    imgRun.onclick = runCode;
    run.appendChild(imgRun);

    var spanRun = element("span");
    attr(spanRun, {class: "span-oper"});
    spanRun.innerHTML = "run";
    spanRun.onclick = runCode;
    run.appendChild(spanRun);

    var imgCopy = element("img");
    attr(imgCopy, {
        class: "img-oper",
        src: this.isDark ? "../static/images/code-copy-dark.png" : "../static/images/code-copy.png"
    });
    imgCopy.onclick = copyCode;
    run.appendChild(imgCopy);

    var spanCopy = element("span");
    attr(spanCopy, {class: "span-oper"});
    spanCopy.innerHTML = "copy";
    spanCopy.onclick = copyCode;
    run.appendChild(spanCopy);

    var divLine = element("div");
    attr(divLine, {class: this.isDark ? "div-line-dark" : "div-line"});
    border.appendChild(divLine);

    var divCode = element("div");
    border.appendChild(divCode);

    var divLine2 = element("div");
    attr(divLine2, {class: this.isDark ? "div-line-dark" : "div-line"});
    border.appendChild(divLine2);

    // params
    var divParam = element("div");
    attr(divParam, {class: "div-param"});
    border.appendChild(divParam);

    var titleParam = element("span");
    attr(titleParam, {class: "title-param"});
    titleParam.innerHTML = "params";
    divParam.appendChild(titleParam);

    var edtParam = element("input");
    attr(edtParam, {type: "input", class: this.isDark ? "input-param-dark" : "input-param"});
    divParam.appendChild(edtParam);

    var divLine3 = element("div");
    attr(divLine3, {class: this.isDark ? "div-line-param-dark" : "div-line-param"});
    border.appendChild(divLine3);

    var divOutput = element("div");
    border.appendChild(divOutput);

    var h = Math.floor(this.height / 3) * 2;

    // params
    if (!codeParams.hasOwnProperty("canRun")) {
        codeParams["canRun"] = true;
    }
    if (!codeParams.hasOwnProperty("matchBrackets")) {
        codeParams["matchBrackets"] = true;
    }
    if (!codeParams.hasOwnProperty("styleActiveLine")) {
        codeParams["styleActiveLine"] = true;
    }
    if (!codeParams.hasOwnProperty("lineNumbers")) {
        codeParams["lineNumbers"] = true;
    }
    if (!codeParams.hasOwnProperty("lineWrapping")) {
        codeParams["lineWrapping"] = true;
    }
    if (!codeParams.hasOwnProperty("indentUnit")) {
        codeParams["indentUnit"] = 4;
    }
    if (!codeParams.hasOwnProperty("smartIndent")) {
        codeParams["smartIndent"] = true;
    }
    if (!codeParams.hasOwnProperty("tabSize")) {
        codeParams["tabSize"] = 4;
    }
    if (!codeParams.hasOwnProperty("readOnly")) {
        codeParams["readOnly"] = false;
    }

    if (!codeParams.hasOwnProperty("showCursorWhenSelecting")) {
        codeParams["showCursorWhenSelecting"] = true;
    }
    if (!codeParams.hasOwnProperty("extraKeys")) {
        codeParams["extraKeys"] = {
            "Ctrl-Space": "autocomplete",
            "Alt-/": "autocomplete",
            "Cmd-Space": "autocomplete"
        };
    }

    var editor = CodeMirror(divCode, codeParams);
    editor.setSize(this.width, h);

    outputParams["theme"] = this.isDark ? "darcula" : "default";

    if (!outputParams.hasOwnProperty("indentUnit")) {
        outputParams["indentUnit"] = 4;
    }
    if (!outputParams.hasOwnProperty("smartIndent")) {
        outputParams["smartIndent"] = true;
    }
    if (!outputParams.hasOwnProperty("tabSize")) {
        outputParams["tabSize"] = 4;
    }
    if (!outputParams.hasOwnProperty("readOnly")) {
        outputParams["readOnly"] = true;
    }
    if (!outputParams.hasOwnProperty("lineWrapping")) {
        outputParams["lineWrapping"] = true;
    }

    var output = CodeMirror(divOutput, outputParams);
    output.setSize(this.width, this.height - 40 - h - 2);

    /**
     * 设置代码
     * @param code
     */
    this.setCode = function (code) {
        editor.setValue(code);
    };

    /**
     * 获取代码
     * @returns {string}
     */
    this.getCode = function () {
        return editor.getValue();
    };

    this.setParam = function (p) {
        edtParam.value = p;
    };
    this.getParam = function () {
        return edtParam.value;
    };

    /**
     * 更换语言
     * @param language
     */
    this.setLanguage = function (lng) {
        language.innerHTML = lng;
        switch (lng) {
            case "JavaScript":
                editor.setOption("mode", "text/javascript");
                break;
            case "Python":
                editor.setOption("mode", "text/x-python");
                break;
            case "Java":
                editor.setOption("mode", "text/x-java");
                break;
            case "C#":
                editor.setOption("mode", "text/x-csharp");
                break;
            case "PHP":
                editor.setOption("mode", "text/x-php");
                break;
            case "C":
                editor.setOption("mode", "text/x-c++src");
                break;
            case "C++":
                editor.setOption("mode", "text/x-c++src");
                break;
            case "Swift":
                editor.setOption("mode", "text/x-swift");
                break;
            case "Kotlin":
                editor.setOption("mode", "text/x-kotlin");
                break;
            case "Go":
                editor.setOption("mode", "text/x-go");
                break;
            case "Rust":
                editor.setOption("mode", "text/x-rustsrc");
                break;
            case "ObjC":
                editor.setOption("mode", "text/x-objectivec");
                break;
            case "Pascal":
                editor.setOption("mode", "text/x-pascal");
                break;
            case "Ruby":
                editor.setOption("mode", "text/x-ruby");
                break;
            case "Julia":
                editor.setOption("mode", "text/x-julia");
                break;
            case "R":
                editor.setOption("mode", "text/x-rsrc");
                break;
            case "TypeScript":
                editor.setOption("mode", "text/typescript");
                break;
            case "Scala":
                editor.setOption("mode", "text/x-scala");
                break;
            case "Lua":
                editor.setOption("mode", "text/x-lua");
                break;
            case "Perl":
                editor.setOption("mode", "text/x-perl");
                break;
            case "Dart":
                editor.setOption("mode", "dart");
                break;
            case "F#":
                editor.setOption("mode", "text/x-fsharp");
                break;
        }
    };

    function toggleParam() {
        var h = Math.floor(that.height / 3) * 2;
        if (divParam.style.display === "flex") {
            divParam.style.display = "none";
            divLine3.style.display = "none";
            output.setSize(that.width, that.height - 40 - h - 2);
        } else {
            divParam.style.display = "flex";
            divLine3.style.display = "block";
            output.setSize(that.width, that.height - 70 - h - 3);
            edtParam.focus();
        }
    }

    this.resize = function (width, height) {
        this.width = width;
        this.height = height;
        var h = Math.floor(this.height / 3) * 2;
        size(border, {width: this.width + "px", height: this.height + "px"});
        editor.setSize(this.width, h);
        output.setSize(this.width, this.height - 40 - h - 2);
    };

    /**
     * 运行代码(以回调的形式)
     */
    function runCode() {
        if (that.onRun) {
            output.setValue("executing...");
            var lng = language.innerHTML;
            var code = that.getCode();
            var param = that.getParam();
            that.onRun(lng, code, param);
        }
    }

    this.showResult = function(data) {
        if (data.result === 0) {
            output.setValue(data.output + "\n" + data.error);
        } else {
            toastr.error("Execute code error.");
        }
    };

    /**
     *
     * @type function(lng, code, param)
     */
    this.onRun = null;

    this.execute = function () {
        if (this.canrun) {
            runCode();
        }
    };

    /**
     * 复制代码到剪贴板
     */
    function copyCode() {
        var code = that.getCode();
        var objCopy = document.createElement("input");
        objCopy.value = code;
        document.body.appendChild(objCopy);
        objCopy.select();
        document.execCommand("Copy");
        objCopy.style.display = "none";
        document.body.removeChild(objCopy);
        toastr.options.closeDuration = 300;
        toastr.options.timeOut = 2000;
        toastr.options.extendedTimeOut = 60;
        toastr.options.positionClass = "toast-bottom-center";
        toastr.success("Code Copied.");
    }

    baseobj.resize(function () {
        that.resize(baseobj[0].offsetWidth, baseobj[0].offsetHeight);
    });

    this.setTheme = function (isDark) {
        this.isDark = isDark;
        attr(border, {class: this.isDark ? "div-code-border-dark" : "div-code-border"});
        attr(divLine, {class: this.isDark ? "div-line-dark" : "div-line"});
        attr(divLine2, {class: this.isDark ? "div-line-dark" : "div-line"});
        attr(edtParam, {type: "input", class: this.isDark ? "input-param-dark" : "input-param"});
        attr(divLine3, {class: this.isDark ? "div-line-param-dark" : "div-line-param"});
        attr(imgParam, {src: this.isDark ? "../static/images/code-param-dark.png" : "../static/images/code-param.png"});
        attr(imgRun, {src: this.isDark ? "../static/images/code-run-dark.png" : "../static/images/code-run.png"});
        attr(imgCopy, {src: this.isDark ? "../static/images/code-copy-dark.png" : "../static/images/code-copy.png"});
        editor.setOption("theme", this.isDark ? "darcula" : "default");
        output.setOption("theme", this.isDark ? "darcula" : "default");
    };

    this.canrun = true;

    this.setCanRun = function (canrun) {
        this.canrun = canrun;
        var h = Math.floor(this.height / 3) * 2;
        if (canrun) {
            run.style.width = "196px";
            imgParam.style.display = "block";
            spanParam.style.display = "block";
            imgRun.style.display = "block";
            spanRun.style.display = "block";
            editor.setSize(this.width, h);
            output.setSize(this.width, this.height - 40 - h - 2);
            divLine2.style.display = "block";
        } else {
            run.style.width = "72px";
            imgParam.style.display = "none";
            spanParam.style.display = "none";
            imgRun.style.display = "none";
            spanRun.style.display = "none";
            divLine2.style.display = "none";
            editor.setSize(this.width, this.height - 40 - 1);
            output.setSize(this.width, 0);
        }
    };

    this.setLanguage(codeParams.language);

    this.setCanRun(codeParams.canRun);
}