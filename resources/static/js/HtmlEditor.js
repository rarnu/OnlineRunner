function HtmlEditor(baseobj, codeParams) {

    var that = this;
    this.width = baseobj[0].offsetWidth;
    this.height = baseobj[0].offsetHeight;

    this.border = element("div");
    attr(this.border, {class: "div-code-border"});
    size(this.border, {width: this.width + "px", height: this.height + "px"});
    baseobj[0].appendChild(this.border);

    var bar = element("div");
    attr(bar, {class: "div-code-bar"});
    this.border.appendChild(bar);

    var type = element("div");
    attr(type, {class: "div-code-type"});
    bar.appendChild(type);

    this.language = element("span");
    attr(this.language, {class: "span-code-type"});
    this.language.innerHTML = "HTML";
    type.appendChild(this.language);

    var run = element("div");
    attr(run, {class: "div-code-run"});
    run.style.width = "123px";
    bar.appendChild(run);

    var imgRun = element("img");
    attr(imgRun, {class: "img-oper", src: "../static/images/code-run.png"});
    imgRun.onclick = runCode;
    run.appendChild(imgRun);

    var spanRun = element("span");
    attr(spanRun, {class: "span-oper"});
    spanRun.innerHTML = "run";
    spanRun.onclick = runCode;
    run.appendChild(spanRun);

    var imgCopy = element("img");
    attr(imgCopy, {class: "img-oper", src: "../static/images/code-copy.png"});
    imgCopy.onclick = copyCode;
    run.appendChild(imgCopy);

    var spanCopy = element("span");
    attr(spanCopy, {class: "span-oper"});
    spanCopy.innerHTML = "copy";
    spanCopy.onclick = copyCode;
    run.appendChild(spanCopy);

    var divLine = element("div");
    attr(divLine, {class: "div-line"});
    this.border.appendChild(divLine);

    this.divIDE = element("div");
    size(this.divIDE, {width: this.width + "px", height: (this.height - 41) + "px"});
    this.border.appendChild(this.divIDE);

    var ww = Math.floor(this.width / 2);
    this.divCode = element("div");
    attr(this.divCode, {class: "div-code-container"});
    size(this.divCode, {width: ww + "px", height: this.divIDE.style.height});
    this.divIDE.appendChild(this.divCode);

    var divVLine = element("div");
    attr(divVLine, {class: "div-v-line"});
    size(divVLine, {width: "1px", height: this.divIDE.style.height});
    this.divIDE.appendChild(divVLine);

    var divResult = element("div");
    attr(divResult, {class: "div-html-result"});
    size(divResult, {width: (this.width - 1 - ww) + "px", height: this.divIDE.style.height});
    this.divIDE.appendChild(divResult);

    this.divOutput = element("div");
    attr(this.divOutput, {class: "div-html-output"});
    divResult.appendChild(this.divOutput);

    var hh = this.height - 41 /* 操作栏 */ - 4 /* 4 条线 */ - 40 /* 语言小标题 */;
    var hitem = Math.floor(hh / 3);

    // divCode
    this.divHtml = element("div");
    size(this.divHtml, {width: "100%", height: hitem + "px"});
    this.divCode.append(this.divHtml);
    var line1 = element("div");
    attr(line1, {class: "div-line"});
    this.divCode.append(line1);
    var divJs = element("div");
    attr(divJs, {class: "div-sub-language"});
    divJs.innerHTML = "JavaScript";
    this.divCode.append(divJs);
    var line2 = element("div");
    attr(line2, {class: "div-line"});
    this.divCode.append(line2);
    this.divJavascript = element("div");
    size(this.divJavascript, {width: "100%", height: hitem + "px"});
    this.divCode.append(this.divJavascript);
    var line3 = element("div");
    attr(line3, {class: "div-line"});
    this.divCode.append(line3);
    var divCss = element("div");
    attr(divCss, {class: "div-sub-language"});
    divCss.innerHTML = "CSS";
    this.divCode.append(divCss);
    var line4 = element("div");
    attr(line4, {class: "div-line"});
    this.divCode.append(line4);
    this.divCSSCode = element("div");
    size(this.divCSSCode, {width:"100%", heigh:(hh - hitem - hitem) + "px"});
    this.divCode.append(this.divCSSCode);

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

    codeParams["mode"] = "text/html";
    this.htmlEditor = CodeMirror(this.divHtml, codeParams);
    this.htmlEditor.setSize("100%", hitem);
    codeParams["mode"] = "text/javascript";
    this.JavascriptEditor = CodeMirror(this.divJavascript, codeParams);
    this.JavascriptEditor.setSize("100%", hitem);
    codeParams["mode"] = "text/css";
    this.cssEditor = CodeMirror(this.divCSSCode, codeParams);
    this.cssEditor.setSize("100%", hh - hitem - hitem);

    this.setHtmlCode = function (code) {
        this.htmlEditor.setValue(code);
    };
    this.setJavsscriptCode = function (code) {
        this.JavascriptEditor.setValue(code);
    };
    this.setCssCode = function (code) {
        this.cssEditor.setValue(code);
    };

    this.getHtmlCode = function () {
        return this.htmlEditor.getValue();
    };
    this.getJavascriptCode = function () {
        return this.JavascriptEditor.getValue();
    };
    this.getCssCode = function () {
        return this.cssEditor.getValue();
    };

    this.resize = function (width, height) {
        this.width = width;
        this.height = height;
        size(this.border, {width: this.width + "px", height: this.height + "px"});
        size(this.divIDE, {width: this.width + "px", height: (this.height - 41) + "px"});
        var ww = Math.floor(this.width / 2);
        size(this.divCode, {width: ww + "px", height: this.divIDE.style.height});
        var hh = this.height - 41 /* 操作栏 */ - 4 /* 4 条线 */ - 40 /* 语言小标题 */;
        var hitem = Math.floor(hh / 3);
        size(this.divHtml, {width: "100%", height: hitem + "px"});
        size(this.divJavascript, {width: "100%", height: hitem + "px"});
        size(this.divCSSCode, {width:"100%", heigh:(hh - hitem - hitem) + "px"});
        this.htmlEditor.setSize("100%", hitem);
        this.JavascriptEditor.setSize("100%", hitem);
        this.cssEditor.setSize("100%", hh - hitem - hitem);
    };

    var customScripotNode = null;

    function runCode() {
        var js = that.JavascriptEditor.getValue();
        if (customScripotNode != null) {
            document.head.removeChild(customScripotNode);
        }
        customScripotNode = document.createElement("script");
        customScripotNode.type = "text/javascript";
        customScripotNode.appendChild(document.createTextNode(js));
        document.head.appendChild(customScripotNode);
        var css = that.cssEditor.getValue();
        var cssCode = "<style>\n" + css + "\n</style>\n";
        var html = that.htmlEditor.getValue();
        that.divOutput.innerHTML = "<html lang='en'><head><title></title>" + cssCode + "</head><body>" + html + "</body></html>";
    }

    /**
     * 复制代码到剪贴板
     */
    function copyCode() {
        var cssCode = "<style>\n" + that.cssEditor.getValue() + "\n</style>\n";
        var jsCode = "<script type='text/javascript'>\n" + that.JavascriptEditor.getValue() + "\n</script>";
        var htmlCode = that.htmlEditor.getValue();
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
        // TODO: set theme
        console.log("theme => " + isDark);
    };

}
