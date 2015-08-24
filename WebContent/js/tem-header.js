/**
 * UC: ログインする
 * initHeader()
 * login(uid, pass)
 * 
 * UC: ログアウトする
 * logout()
 * LogoutController.js を読み込むようにした
 */
document.write('<script type="text/javascript" src="/TEM/dwr/interface/LoginController.js"></script>');
document.write('<script type="text/javascript" src="js/jquery.cookie.js"></script>');
document.write('<script type="text/javascript" src="js/jquery.serialize.js"></script>');
document.write('<script type="text/javascript" src="/TEM/dwr/interface/LogoutController.js"></script>');

var header
	= '<div id="logo"></div>'
	+ '<div id="login_wrapper" />'
	+ '<hr>'
	+ '<div id="menu_wrapper" />'
	+ '<hr>';

var defaultForm
	= '<form id="login_form">'
	+ '  ユーザID <input type="text" name="userId"/>'
	+ '  パスワード <input type="password" name="pass" />'
	+ '  <input type="button" onclick="login()" value="ログイン" />'
	+ '  <span id="header_error_msg" class="error"></span>'
	+ '</form>';

var loggedInForm
	= '<form id="login_form">'
	+ '  <span>ようこそ <span class="uid"></span></span>'
	+ '  <input type="button" onclick="logout()" value="ログアウト" />'
	+ '</form>';
	

var MENU_SEP = ' | ';

// メニューの各リンク
var a_eventList           = '<a href="./">イベント一覧</a>';         
var a_boughtTicketList    = '<a href="boughtTicketList.html">購入済みチケット一覧</a>'
var a_registerEvent       = '<a href="registerEvent.html">新規イベント登録</a>'
var a_registeredEventList = '<a href="registeredEventList.html">登録済みイベント一覧</a>';
var a_signup              = '<a href="signup.html">新規アカウント登録</a>';
var a_signupPromoter      = '<a href="signupPromoter.html">新規興行主登録</a>';

// ロールごとのメニューバー
var defaultMenu  = a_eventList + MENU_SEP + a_signup;
var userMenu     = a_eventList + MENU_SEP + a_boughtTicketList;
var promoterMenu = a_eventList + MENU_SEP + a_boughtTicketList + MENU_SEP + a_registeredEventList + MENU_SEP + a_registerEvent;
var adminMenu    = a_eventList + MENU_SEP + a_boughtTicketList + MENU_SEP + a_signupPromoter;

// ヘッダ初期化処理
function initHeader() {
	$('#header').html(header);
	
	// 非ログイン時
	if ($.cookie('userId') == undefined) {
		$('#login_wrapper').html(defaultForm);
		$('#menu_wrapper').html(defaultMenu);
	// ログイン時
	} else {
		$('#login_wrapper').html(loggedInForm);
		$('[class="uid"]').html($.cookie('userId'));
		
		if ($.cookie('role') == 'user') {
			$('#menu_wrapper').append(userMenu);
		} else if ($.cookie('role') == 'promoter') {
			$('#menu_wrapper').append(promoterMenu);
		} else if ($.cookie('role') == 'administrator') {
			$('#menu_wrapper').append(adminMenu);
		}
	}
}

// ログイン処理
// dwrLogin():フォームからログインのケースと
// dwrLogin(uid, pass):引数ログインのケースのオーバーロード
function login(uid, pass) {
	var form;
	if (uid == null && pass == null) {
		form = $('#login_form').serializeJson();
	} else {
		form = {userId:uid, pass:pass};
	}
	
	LoginController.execute(form, {
		callback: function(data) {
			$.cookie('userId', form.userId);
			$.cookie('role', data.role);
			
			// トップへリダイレクト
			//document.location = './';
			location.reload();
		},
		exceptionHandler: function(msg, err) {
			$('#header_error_msg').text(msg);
		}
	});
}

// ログアウト処理．クッキーを削除しヘッダを更新
function logout(){
	LogoutController.execute({
		callback: function() {
			$.cookie('userId', null);
			$.cookie('role', null);
			
			//document.location = './';
			location.reload();
		}
	});
}