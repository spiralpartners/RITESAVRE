/*!
 * <form>オブジェクトからJSONオブジェクトへのシリアライズ拡張．
 * <form>内の全ての<input>オブジェクトそれぞれに対して，name 属性を付与すること．
 * 
 * shinsuke-m
 * 
 */
$.fn.extend({
	serializeJson:function() {
		var json = new Object;
		
		// 全子要素を走査
		$(this).children().each(function(){
			
			// 再帰呼び出しによる全子孫要素の探索
			$(json).attr($(this).serializeJson());
			
			var tag = $(this).get(0).tagName;
			// <input> と <select> と <textarea> を探す
			if (tag.match(/^input/i) || tag.match(/^select/i) || tag.match(/^textarea/i)) {

				var type = $(this).attr('type')
				if (type != undefined && type.match(/^button/i)) {
					return; // type=buttonは無視
				}
				var name = $(this).attr('name');
				if(name == undefined || name == '') {
					console.error('[JSONシリアライズ警告] type=' + type
							+ 'の入力フォームにはname属性が付与されていません．シリアライズを無視します．');
					return;
				}
				
				if ($(json).attr(name) != undefined) {
					console.error('[JSONシリアライズ警告] name=' + name
							+ 'の入力フォームのname属性が重複しています．シリアライズを無視します．');
					return;
				}
				$(json).attr(name, $(this).val());
			}
		});
		return json;
	}
})