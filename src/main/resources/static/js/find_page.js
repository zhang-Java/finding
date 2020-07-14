window.onload=function(){

	var page_n=1;
	var keywords='';
	var tag=[-1,-1,-1];

	var t1='全部/剧情/喜剧/动作/爱情/科幻/动画/悬疑/惊悚/恐怖/犯罪/同性/音乐/歌舞/传记/历史/战争/西部/奇幻/冒险/灾难/武侠/情色';
	var t2='全部/中国大陆/美国/中国香港/中国台湾/日本/韩国/英国/法国/德国/意大利/西班牙/印度/泰国/俄罗斯/伊朗/加拿大/澳大利亚/爱尔兰/瑞典/巴西/丹麦';
	var t3='全部/2020/2019/2010年代/2000年代/90年代/80年代/70年代/60年代/更早';

	var t1s=t1.split('/');
	var t2s=t2.split('/');
	var t3s=t3.split('/');

	var vm = new Vue({
		el: "#find1",
		data: {
			movies: [],
			page_num:0
		}
	});

	$('#find').on('click','#f1',
		function(){
			$('#tag').css('display','none');
			$('#f2').css('background','#f1f2f6');
			$('#s2').css('background','#f1f2f6');
			$('#f1').css('background','white');
			$('#s1').css('background','white');
			$('#f2').css('z-index','3');
			$('#s2').css('z-index','3');
			$('#f1').css('z-index','5');
			$('#s1').css('z-index','5');
		}
	);

	$('#find').on('click','#f2',
		function(){
			$('#tag').css('display','block');
			$('#f1').css('background','#f1f2f6');
			$('#s1').css('background','#f1f2f6');
			$('#f2').css('background','white');
			$('#s2').css('background','white');
			$('#f1').css('z-index','3');
			$('#s1').css('z-index','3');
			$('#f2').css('z-index','5');
			$('#s2').css('z-index','5');
		}
	);

	function changePage(){
		page_n = this.value;
		show_movies(page_n);
		pages_width();
	}

	function changeTag1(){
		tag[0] = this.value;
		alert(tag[0]);
		initTags();
	}
	function changeTag2(){
		tag[1] = this.value;
		initTags();
	}
	function changeTag3(){
		tag[2] = this.value;
		initTags();
	}

	function initKeywords() {

		var url_keywords = window.location.href;
		var keywords = decodeURI(url_keywords.split('?')[1].split('=')[1]);
		$('#in').val(keywords);

		return keywords;

	}

	function create_tag(s) {
		var p = document.createElement("div");
		p.innerText = s;
		return p;
	}

	function initTags(){

		$('#t1').html('');
		$('#t2').html('');
		$('#t3').html('');
		for (var i = 0; i < t1s.length; i++) {
			var p=create_tag(t1s[i]);
			p.onclick=changeTag1;
			p.value=i;
			if (i==tag[0]||(i==0&&tag[0]==-1)){
				p.className="tag at-tag hand";
			}else {
				p.className="tag hand";
			}
			$('#t1').append(p);
		}

		for (var i = 0; i < t2s.length; i++) {
			var p=create_tag(t2s[i]);
			p.onclick=changeTag2;
			p.value=i;
			if (i==tag[1]||(i==0&&tag[1]==-1)){
				p.className="tag at-tag hand";
			}else {
				p.className="tag hand";
			}
			$('#t2').append(p);
		}

		for (var i = 0; i < t3s.length; i++) {
			var p=create_tag(t3s[i]);
			p.onclick=changeTag3;
			p.value=i;
			if (i==tag[2]||(i==0&&tag[2]==-1)){
				p.className="tag at-tag hand";
			}else {
				p.className="tag hand";
			}
			$('#t3').append(p);
		}

	}

	function tag_to_str(){
		var s='';

		if (tag[0]==-1){
			s+=-1+'/';
		}else {
			s+=(t1s.length-tag[0]-1)+'/';
		}

		if (tag[1]==-1){
			s+=-1+'/';
		}else {
			s+=(t2s.length-tag[1]-1)+'/';
		}

		if (tag[2]==-1){
			s+=-1;
		}else {
			s+=(t3s.length-tag[2]-1);
		}


		return s;
	}


	$('#tg_btn').on('click',
		function(){
			// window.location.href='http://localhost:8181/html/find.html?keywords='+getKeyWords();
			keywords=initKeywords();
			do_find(keywords,tag_to_str(tag));

			initTags();
		}
	);

	function getMId(url) {

		var s=url.split("/");

		console.log(s);

		return s[4];

	}

	function getKeyWords() {

		return $('#in').val();

	}


	function pages_width() {

		var n = (vm.page_num*5+100)+'px';
		$('#to-center').css('width',n);
		var s='';

		$('#page').html('');
		for (var i = 1; i <= vm.page_num/10; i++) {

			var p = document.createElement("div");
			p.innerText = i;
			if (i==page_n){
				p.className="btn page hand at-this";
			}else {
				p.className="btn page hand";
			}
			p.onclick=changePage;
			p.value=i;
			$('#page').append(p);

		}

	}

	$('#in').bind('keydown',function(event){
		if(event.keyCode == "13") {
			$('#tg_btn').click();
		}
	});

	function show_movies(page) {

		var movies = vm.movies;
		var s='';

		for (var i = (page-1)*10; i < page*10; i++) {

			var mId=getMId(movies[i].url);

			s+= '<div class="result">'+
				'<div class="url"> '+
				'<a href="http://localhost:8181/html/details.html?mId='+mId+'">'+movies[i].title+'</a> '+
				'</div>'+
				'<div class="r_text"> '+
				'导演：' + movies[i].director+
				'<br>'+
				'上映时间：' + movies[i].release+
				'<br>'+
				'剧情简介:'+ movies[i].introduction+
				'</div>'+
				'</div>';


		}
		$('#show').html(s);

	}

	function do_find(keywords,tag){

		$.ajax({
		url: 'http://localhost:8181/find/doFind',
		method: 'POST',
		dataType:'json',
		contentType: "application/json; charset=utf-8",
		data:JSON.stringify({"keywords":keywords,"tag":tag}),
		beforeSend:function(XMLHttpRequest){
			$("#show").css('display','none');
			$("#change").css('display','none');
			$("#loading").html("<img src='../img/loading.gif' style='width: 100px;height: 100px'/>");
		},
		success: function(res) {
			vm.movies=res;
			vm.page_num=res.length;

			console.log(res);

			pages_width();
			show_movies(1)
		},
		complete:function(XMLHttpRequest,textStatus){
			$("#loading").css('display','none');
			$("#show").css('display','block');
			$("#change").css('display','block');
			$("#loading").empty();
		},
	});}

	$('#tg_btn').on('click',
		function(){
			keywords=getKeyWords();
			do_find(keywords,tag_to_str(tag));
		}
	);

	keywords=initKeywords();
	do_find(keywords,tag_to_str(tag));

	initTags();


};
