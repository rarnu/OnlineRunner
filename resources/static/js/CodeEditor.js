var languageList = ["Python", "Java", "JavaScript", "C#", "PHP", "C", "C++", "Swift", "Kotlin", "Go", "Rust", "ObjC", "Pascal", "Ruby", "Julia", "R", "TypeScript", "Scala", "Lua", "Perl", "Dart"];

function CodeEditor(baseobj, codeParams, outputParams) {

    var that = this;
    this.width = baseobj[0].offsetWidth;
    this.height = baseobj[0].offsetHeight;

    this.border = element("div");
    attr(this.border, {class: "div-code-border"});
    size(this.border, {width: this.width + "px", height:this.height + "px"});
    baseobj[0].appendChild(this.border);

    var bar = element("div");
    attr(bar, {class: "div-code-bar"});
    this.border.appendChild(bar);
    var type = element("div");
    attr(type, {class: "div-code-type"});
    bar.appendChild(type);

    this.language = element("span");
    attr(this.language, {class: "span-code-type"});
    type.appendChild(this.language);

    this.run = element("div");
    attr(this.run, {class: "div-code-run"});
    bar.appendChild(this.run);

    this.imgParam = element("img");
    attr(this.imgParam, {class: "img-oper", src: "../static/images/code-param.png"});
    this.imgParam.onclick = toggleParam;
    this.run.appendChild(this.imgParam);

    this.spanParam = element("span");
    attr(this.spanParam, {class: "span-oper"});
    this.spanParam.innerHTML = "param";
    this.spanParam.onclick = toggleParam;
    this.run.appendChild(this.spanParam);

    this.imgRun = element("img");
    attr(this.imgRun, {class: "img-oper", src: "../static/images/code-run.png"});
    this.imgRun.onclick = runCode;
    this.run.appendChild(this.imgRun);

    this.spanRun = element("span");
    attr(this.spanRun, {class: "span-oper"});
    this.spanRun.innerHTML = "run";
    this.spanRun.onclick = runCode;
    this.run.appendChild(this.spanRun);

    var imgCopy = element("img");
    attr(imgCopy, {class: "img-oper", src: "../static/images/code-copy.png"});
    imgCopy.onclick = copyCode;
    this.run.appendChild(imgCopy);

    var spanCopy = element("span");
    attr(spanCopy, {class:"span-oper"});
    spanCopy.innerHTML = "copy";
    spanCopy.onclick = copyCode;
    this.run.appendChild(spanCopy);

    var divLine = element("div");
    attr(divLine, {class:"div-line"});
    this.border.appendChild(divLine);

    var divCode = element("div");
    this.border.appendChild(divCode);

    var divLine2 = element("div");
    attr(divLine2, {class:"div-line"});
    this.border.appendChild(divLine2);

    // params
    this.divParam = element("div");
    attr(this.divParam, {class: "div-param"});
    this.border.appendChild(this.divParam);

    var titleParam = element("span");
    attr(titleParam, {class: "title-param"});
    titleParam.innerHTML = "params";
    this.divParam.appendChild(titleParam);

    this.edtParam = element("input");
    attr(this.edtParam, {type: "input", class: "input-param"});
    this.divParam.appendChild(this.edtParam);

    this.divLine3 = element("div");
    attr(this.divLine3, {class:"div-line-param"});
    this.border.appendChild(this.divLine3);

    var divOutput = element("div");
    this.border.appendChild(divOutput);

    var h = Math.floor(this.height / 3) * 2;

    // params

    if (!codeParams.hasOwnProperty("matchBrackets")) {
        codeParams["matchBrackets"] = true;
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
    if (!codeParams.hasOwnProperty("theme")) {
        codeParams["theme"] = "default";
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

    this.editor = CodeMirror(divCode, codeParams);

    this.editor.setSize(this.width, h);

    if (!outputParams.hasOwnProperty("theme")) {
        outputParams["theme"] = "default";
    }
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

    this.output = CodeMirror(divOutput, outputParams);
    this.output.setSize(this.width, this.height - 40 - h - 2);

    /**
     * 设置代码
     * @param code
     */
    this.setCode = function(code) {
        this.editor.setValue(code);
    };

    /**
     * 获取代码
     * @returns {string}
     */
    this.getCode = function () {
        return this.editor.getValue();
    };

    /**
     * 更换语言
     * @param language
     */
    this.setLanguage = function (language) {
        this.language.innerHTML = language;
        switch (language) {
            case "JavaScript":
                this.editor.setOption("mode", "text/javascript");
                break;
            case "Python":
                this.editor.setOption("mode", "text/x-python");
                break;
            case "Java":
                this.editor.setOption("mode", "text/x-java");
                break;
            case "C#":
                this.editor.setOption("mode", "text/x-csharp");
                break;
            case "PHP":
                this.editor.setOption("mode", "text/x-php");
                break;
            case "C":
                this.editor.setOption("mode", "text/x-c++src");
                break;
            case "C++":
                this.editor.setOption("mode", "text/x-c++src");
                break;
            case "Swift":
                this.editor.setOption("mode", "text/x-swift");
                break;
            case "Kotlin":
                this.editor.setOption("mode", "text/x-kotlin");
                break;
            case "Go":
                this.editor.setOption("mode", "text/x-go");
                break;
            case "Rust":
                this.editor.setOption("mode", "text/x-rustsrc");
                break;
            case "ObjC":
                this.editor.setOption("mode", "text/x-objectivec");
                break;
            case "Pascal":
                this.editor.setOption("mode", "text/x-pascal");
                break;
            case "Ruby":
                this.editor.setOption("mode", "text/x-ruby");
                break;
            case "Julia":
                this.editor.setOption("mode", "text/x-julia");
                break;
            case "R":
                this.editor.setOption("mode", "text/x-rsrc");
                break;
            case "TypeScript":
                this.editor.setOption("mode", "text/typescript");
                break;
            case "Scala":
                this.editor.setOption("mode", "text/x-scala");
                break;
            case "Lua":
                this.editor.setOption("mode", "text/x-lua");
                break;
            case "Perl":
                this.editor.setOption("mode", "text/x-perl");
                break;
            case "Dart":
                this.editor.setOption("mode", "text/x-dart");
                break;
        }
    };

    function toggleParam() {
        var h = Math.floor(that.height / 3) * 2;
        if (that.divParam.style.display === "flex") {
            that.divParam.style.display = "none";
            that.divLine3.style.display = "none";
            that.output.setSize(that.width, that.height - 40 - h - 2);
        } else {
            that.divParam.style.display = "flex";
            that.divLine3.style.display = "block";
            that.output.setSize(that.width, that.height - 70 - h - 3);
            that.edtParam.focus();
        }
    }

    this.resize = function (width, height) {
        this.width = width;
        this.height = height;
        var h = Math.floor(this.height / 3) * 2;
        size(this.border, {width: this.width + "px", height:this.height + "px"});
        this.editor.setSize(this.width, h);
        this.output.setSize(this.width, this.height - 40 - h - 2);
    };

    /**
     * 运行代码(以回调的形式)
     */
    function runCode() {
        that.output.setValue("executing...");
        var lng = that.language.innerHTML;
        var code = that.getCode();
        reqPostJson("/execute", { language: lng, code: code }, function (data) {
            if (data.result ===  0) {
                that.output.setValue(data.output + "\n" + data.error);
            } else {
                toastr.error("Execute code error.");
            }
        });
    }

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
        // TODO: set theme
        console.log("theme => " + isDark);
    };

    this.setCanRun = function (canrun) {
        if (canrun) {
            this.run.style.width = "190px";
            this.imgParam.style.display = "block";
            this.spanParam.style.display = "block";
            this.imgRun.style.display = "block";
            this.spanRun.style.display = "block";
        } else {
            this.run.style.width = "73px";
            this.imgParam.style.display = "none";
            this.spanParam.style.display = "none";
            this.imgRun.style.display = "none";
            this.spanRun.style.display = "none";
        }
    };

    this.setLanguage(codeParams.language);
}