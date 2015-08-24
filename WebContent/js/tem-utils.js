var DATE_SEP = '/';
var TIME_SEP = ':';

function formatDate(d) {
	var month = (d.getMonth()+1) < 10 ? '0'+(d.getMonth()+1) : (d.getMonth()+1);
	var date = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
	var hour = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
	var minute = '00';
	return d.getFullYear() + DATE_SEP + month + DATE_SEP + date + ' ' + hour + TIME_SEP + minute;
}

function getUrlVars() {
	var vars = new Object;
	var params = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for(var i in params) {
		var h = params[i].split('=');
		vars[h[0]] = h[1];
	}
	return vars;
}


function getMyFileName() {
	var url = window.location.href;
	var length = (url.lastIndexOf('?') < 0) ? url.length : url.lastIndexOf('?');
	return url.substring(url.lastIndexOf('/')+1, length)
}

var d;
function cl(d) {console.log(d)}
