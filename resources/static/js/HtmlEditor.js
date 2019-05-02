function HtmlEditor(baseobj, codeParams) {

    var that = this;
    this.width = baseobj[0].offsetWidth;
    this.height = baseobj[0].offsetHeight;

    if (!codeParams.hasOwnProperty("isDark")) {
        this.isDark = false;
        codeParams["theme"] = "default";
    } else {
        this.isDark = codeParams["isDark"];
        codeParams["theme"] = this.isDark ? "darcula": "default";
    }

    border = element("div");
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
    language.innerHTML = "HTML";
    type.appendChild(language);

    var run = element("div");
    attr(run, {class: "div-code-run"});
    run.style.width = "123px";
    bar.appendChild(run);

    var imgRun = element("img");
    attr(imgRun, {class: "img-oper", src: this.isDark ? "../static/images/code-run-dark.png": "../static/images/code-run.png"});
    imgRun.onclick = runCode;
    run.appendChild(imgRun);

    var spanRun = element("span");
    attr(spanRun, {class: "span-oper"});
    spanRun.innerHTML = "run";
    spanRun.onclick = runCode;
    run.appendChild(spanRun);

    var imgCopy = element("img");
    attr(imgCopy, {class: "img-oper", src: this.isDark ? "../static/images/code-copy-dark.png": "../static/images/code-copy.png"});
    imgCopy.onclick = copyCode;
    run.appendChild(imgCopy);

    var spanCopy = element("span");
    attr(spanCopy, {class: "span-oper"});
    spanCopy.innerHTML = "copy";
    spanCopy.onclick = copyCode;
    run.appendChild(spanCopy);

    var divLine = element("div");
    attr(divLine, {class: this.isDark ? "div-line-dark": "div-line"});
    border.appendChild(divLine);

    var divIDE = element("div");
    size(divIDE, {width: this.width + "px", height: (this.height - 41) + "px"});
    border.appendChild(divIDE);

    var ww = Math.floor(this.width / 2);
    var divCode = element("div");
    attr(divCode, {class: "div-code-container"});
    size(divCode, {width: ww + "px", height: divIDE.style.height});
    divIDE.appendChild(divCode);

    var divVLine = element("div");
    attr(divVLine, {class: this.isDark ? "div-v-line-dark": "div-v-line"});
    size(divVLine, {width: "1px", height: divIDE.style.height});
    divIDE.appendChild(divVLine);

    var divResult = element("div");
    attr(divResult, {class: "div-html-result"});
    size(divResult, {width: (this.width - 1 - ww) + "px", height: divIDE.style.height});
    divIDE.appendChild(divResult);

    var divOutput = element("div");
    attr(divOutput, {class: "div-html-output"});
    divResult.appendChild(divOutput);

    var hh = this.height - 41 /* 操作栏 */ - 4 /* 4 条线 */ - 40 /* 语言小标题 */;
    var hitem = Math.floor(hh / 3);

    // divCode
    var divHtml = element("div");
    size(divHtml, {width: "100%", height: hitem + "px"});
    divCode.append(divHtml);
    var line1 = element("div");
    attr(line1, {class: this.isDark ? "div-line-dark": "div-line"});
    divCode.append(line1);
    var divJs = element("div");
    attr(divJs, {class: "div-sub-language"});
    divJs.innerHTML = "JavaScript";
    divCode.append(divJs);
    var line2 = element("div");
    attr(line2, {class: this.isDark ? "div-line-dark": "div-line"});
    divCode.append(line2);
    var divJavascript = element("div");
    size(divJavascript, {width: "100%", height: hitem + "px"});
    divCode.append(divJavascript);
    var line3 = element("div");
    attr(line3, {class: this.isDark ? "div-line-dark": "div-line"});
    divCode.append(line3);
    var divCss = element("div");
    attr(divCss, {class: "div-sub-language"});
    divCss.innerHTML = "CSS";
    divCode.append(divCss);
    var line4 = element("div");
    attr(line4, {class: this.isDark ? "div-line-dark": "div-line"});
    divCode.append(line4);
    var divCSSCode = element("div");
    size(divCSSCode, {width:"100%", heigh:(hh - hitem - hitem) + "px"});
    divCode.append(divCSSCode);

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

    codeParams["mode"] = "text/html";
    var htmlEditor = CodeMirror(divHtml, codeParams);
    htmlEditor.setSize("100%", hitem);
    codeParams["mode"] = "text/javascript";
    var JavascriptEditor = CodeMirror(divJavascript, codeParams);
    JavascriptEditor.setSize("100%", hitem);
    codeParams["mode"] = "text/css";
    var cssEditor = CodeMirror(divCSSCode, codeParams);
    cssEditor.setSize("100%", hh - hitem - hitem);

    this.setHtmlCode = function (code) {
        htmlEditor.setValue(code);
    };
    this.setJavsscriptCode = function (code) {
        JavascriptEditor.setValue(code);
    };
    this.setCssCode = function (code) {
        cssEditor.setValue(code);
    };

    this.getHtmlCode = function () {
        return htmlEditor.getValue();
    };
    this.getJavascriptCode = function () {
        return JavascriptEditor.getValue();
    };
    this.getCssCode = function () {
        return cssEditor.getValue();
    };

    this.resize = function (width, height) {
        this.width = width;
        this.height = height;
        size(border, {width: this.width + "px", height: this.height + "px"});
        size(divIDE, {width: this.width + "px", height: (this.height - 41) + "px"});
        var ww = Math.floor(this.width / 2);
        size(divCode, {width: ww + "px", height: divIDE.style.height});
        var hh = this.height - 41 /* 操作栏 */ - 4 /* 4 条线 */ - 40 /* 语言小标题 */;
        var hitem = Math.floor(hh / 3);
        size(divHtml, {width: "100%", height: hitem + "px"});
        size(divJavascript, {width: "100%", height: hitem + "px"});
        size(divCSSCode, {width:"100%", heigh:(hh - hitem - hitem) + "px"});
        htmlEditor.setSize("100%", hitem);
        JavascriptEditor.setSize("100%", hitem);
        cssEditor.setSize("100%", hh - hitem - hitem);
    };

    var customScripotNode = null;

    function runCode() {
        var js = JavascriptEditor.getValue();
        if (customScripotNode != null) {
            document.head.removeChild(customScripotNode);
        }
        customScripotNode = document.createElement("script");
        customScripotNode.type = "text/javascript";
        customScripotNode.appendChild(document.createTextNode(js));
        document.head.appendChild(customScripotNode);
        var css = cssEditor.getValue();
        var cssCode = "<style>\n" + css + "\n</style>\n";
        var html = htmlEditor.getValue();
        divOutput.innerHTML = "<html lang='en'><head><title></title>" + cssCode + "</head><body>" + html + "</body></html>";
    }

    this.execute = function () {
        runCode();
    };

    /**
     * 复制代码到剪贴板
     */
    function copyCode() {
        var cssCode = "<style>\n" + cssEditor.getValue() + "\n</style>\n";
        var jsCode = "<script type='text/javascript'>\n" + JavascriptEditor.getValue() + "\n</script>";
        var htmlCode = htmlEditor.getValue();
        var code = "<html lang='en'><head><title></title>" + cssCode + jsCode + "</head><body>" + htmlCode + "</body></html>";
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
        attr(imgRun, {src: this.isDark ? "../static/images/code-run-dark.png": "../static/images/code-run.png"});
        attr(imgCopy, {src: this.isDark ? "../static/images/code-copy-dark.png": "../static/images/code-copy.png"});
        attr(divLine, {class: this.isDark ? "div-line-dark": "div-line"});
        attr(divVLine, {class: this.isDark ? "div-v-line-dark": "div-v-line"});
        attr(line1, {class: this.isDark ? "div-line-dark": "div-line"});
        attr(line2, {class: this.isDark ? "div-line-dark": "div-line"});
        attr(line3, {class: this.isDark ? "div-line-dark": "div-line"});
        attr(line4, {class: this.isDark ? "div-line-dark": "div-line"});
        htmlEditor.setOption("theme", this.isDark ? "darcula": "default");
        JavascriptEditor.setOption("theme", this.isDark ? "darcula": "default");
        cssEditor.setOption("theme", this.isDark ? "darcula": "default");
    };

}
