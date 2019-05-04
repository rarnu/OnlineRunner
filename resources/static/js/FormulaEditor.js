function FormulaEditor(baseobj, codeParams) {

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
    border.style.width = this.width + "px";
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
    run.style.width = "123px";
    bar.appendChild(run);

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

    var divResult = element("div");
    attr(divResult, {class: "div-formula-result"});
    divResult.style.width = this.width + "px";
    border.appendChild(divResult);

    var output = element("div");
    attr(output, {class: "div-formula-output"});
    divResult.appendChild(output);

    // params
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
    var editorHeight = "150px";
    if (codeParams.hasOwnProperty("editorHeight")) {
        editorHeight = codeParams["editorHeight"];
    }
    editor.setSize(this.width, editorHeight);

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

    /**
     * 更换语言
     * @param lng
     */
    this.setLanguage = function (lng) {
        language.innerHTML = lng;
        switch (lng) {
            case "LaTeX":
                editor.setOption("mode", "text/x-latex");
                break;
            case "Mermaid":
                editor.setOption("mode", "text/plain");
                break;
            case "Function":
                editor.setOption("mode", "text/x-latex");
                break;
        }
    };

    this.resize = function (width, height) {
        this.width = width;
        this.height = height;
        border.style.width = this.width + "px";
        divResult.style.width = this.width + "px";
        editor.setSize(this.width, editorHeight);
    };

    /**
     * 运行代码(以回调的形式)
     */
    function runCode() {
        var l = language.innerHTML;
        var code = that.getCode();
        if (l === "LaTeX") {
            output.innerHTML = "$$" + code + "$$";
            MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
        } else if (l === "Mermaid") {
            output.removeAttribute('data-processed');
            mermaid.initialize({
                theme: that.isDark ? "dark" : "default",
                flowchart: {curve: 'linear'},
                gantt: {axisFormat: '%m/%d/%Y'},
                sequence: {actorMargin: 50}
            });
            output.innerHTML = code;
            mermaid.parse(code);
            mermaid.init({}, output);
        } else if (l === "Function") {
            if (XCalc.properBrackets(code)) {
                var graph = XCalc.graphExpression(that.isDark, code, that.width, that.width);
                output.innerHTML = "";
                output.appendChild(graph.getCanvas());
            } else {
                output.innerHTML = "Error";
            }
        }
    }

    this.execute = function () {
        runCode();
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
        attr(imgRun, {src: this.isDark ? "../static/images/code-run-dark.png" : "../static/images/code-run.png"});
        attr(imgCopy, {src: this.isDark ? "../static/images/code-copy-dark.png" : "../static/images/code-copy.png"});
        attr(divLine, {class: this.isDark ? "div-line-dark" : "div-line"});
        attr(divLine2, {class: this.isDark ? "div-line-dark" : "div-line"});
        editor.setOption("theme", this.isDark ? "darcula" : "default");
        if (language.innerHTML === "Mermaid" || language.innerHTML === "Function") {
            runCode();
        }
    };

    this.setLanguage(codeParams.language);
}