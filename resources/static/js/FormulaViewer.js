/**
 *
 * @param baseobj
 * @param codeParams
 * {
 *     isDark: Boolean,
 *     language: [LaTeX|Mermaid],
 *     code: [],
 * }
 * @constructor
 */
function FormulaViewer(baseobj, codeParams) {

    var that = this;
    this.width = baseobj[0].offsetWidth;

    if (!codeParams.hasOwnProperty("isDark")) {
        this.isDark = false;
        codeParams["theme"] = "default";
    } else {
        this.isDark = codeParams["isDark"];
        codeParams["theme"] = this.isDark ? "darcula" : "default";
    }

    var output = element("div");
    attr(output, {class: this.isDark ? "div-formula-output-dark": "div-formula-output"});
    baseobj[0].appendChild(output);

    this.resize = function (width) {
        this.width = width;
        output.style.width = width + "px";
    };

    var lng = codeParams["language"];
    var code = codeParams["code"];

    function runCode() {
        if (lng === "LaTeX") {
            output.innerHTML = "$$" + code + "$$";
            MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
        } else if (lng === "Mermaid") {
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
        } else if (lng === "Function") {
            if (XCalc.properBrackets(code)) {
                var graph = XCalc.graphExpression(that.isDark, code, that.width, that.width);
                output.innerHTML = "";
                output.appendChild(graph.getCanvas());
            } else {
                output.innerHTML = "Error";
            }
        }
    }

    runCode();

    baseobj.resize(function () {
        that.resize(baseobj[0].offsetWidth);
    });

    this.setCode = function (c) {
        code = c;
        runCode();
    };
    this.setTheme = function (isDark) {
        this.isDark = isDark;
        attr(output, {class: this.isDark ? "div-formula-output-dark": "div-formula-output"});
        if (lng === "Mermaid" || lng === "Function") {
            runCode();
        }
    };


}