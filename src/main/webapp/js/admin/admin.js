/*
 * Fieldset伸展函数 @params 控件ID
 */
function FieldsetClick(objId){
	var obj = document.getElementById(objId);
	var objChildNodes = obj.childNodes;
	for (var i = 0; i < objChildNodes.length; i++){
		if (objChildNodes[i].tagName == "LEGEND"){
			objChildNodes[i].childNodes[0].src = (objChildNodes[i].childNodes[0].src.lastIndexOf("/images/admin/arrow_r.gif") > 0) ? "/images/admin/arrow_d.gif" : "/images/admin/arrow_r.gif";
			objChildNodes[i].childNodes[0].title = (objChildNodes[i].childNodes[0].title == "隐藏") ? "显示" : "隐藏";
		}
		if (objChildNodes[i].tagName == "UL"){
			objChildNodes[i].style.display = (objChildNodes[i].style.display == "none") ? "" : "none";
		}
	}
	obj.className = (obj.className == "fieldset") ? "fieldset-closed" : "fieldset";
	
	mini.layout();
}

function onSkinChange(skin) {
	mini.Cookie.set('miniuiSkin', skin);
	window.location.reload();
}
	var skin = getCookie("miniuiSkin");
	document.write('<link href="/js/miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');

function getCookie(sName) {
var aCookie = document.cookie.split("; ");
var lastMatch = null;
for (var i = 0; i < aCookie.length; i++) {
    var aCrumb = aCookie[i].split("=");
    if (sName == aCrumb[0]) {
        lastMatch = aCrumb;
    }
}
if (lastMatch) {
    var v = lastMatch[1];
    if (v === undefined) return v;
    return unescape(v);
}
return null;
}

$(function () {
var skin = mini.Cookie.get("miniuiSkin");
if (skin) {
    var selectSkin = document.getElementById("selectSkin");
    selectSkin.value = skin;
}
});