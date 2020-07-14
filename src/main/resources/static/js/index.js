window.onload=function(){

	bg_n=1;
	$('#left1').on('click',
		function(){
			if(bg_n>1){
				bg_n-=1;		
			}
			$('body').css('background','url(/img/th'+bg_n+'.jpg)');
		}
	)
	
	$('#right1').on('click',
		function(){
			if(bg_n<5){
				bg_n+=1;		
			}
			$('body').css('background','url(/img/th'+bg_n+'.jpg)');
		}
	)
	
	$('#f1').on('click',
		function(){
			
			$('#f2').css('background','#dfe6e9');
			$('#s2').css('background','#dfe6e9');
			$('#f1').css('background','white');
			$('#s1').css('background','white');
			$('#f2').css('z-index','-1');
			$('#s2').css('z-index','-1');
			$('#f1').css('z-index','3');
			$('#s1').css('z-index','3');
		}
	)
	
	$('#f2').on('click',
		function(){
			
			$('#f1').css('background','#dfe6e9');
			$('#s1').css('background','#dfe6e9');
			$('#f2').css('background','white');
			$('#s2').css('background','white');
			$('#f1').css('z-index','-1');
			$('#s1').css('z-index','-1');
			$('#f2').css('z-index','3');
			$('#s2').css('z-index','3');
		}
	);

	$("#tg_btn").on('click',
		function(){

			$("tj").click();

		}
	);

	function getKeyWords() {

		return $('#in').val();

	}

	$('#tg_btn').on('click',
		function(){

			window.location.href='http://localhost:8181/html/find.html?keywords='+getKeyWords();
		}
	);

	$('#in').bind('keydown',function(event){
		if(event.keyCode == "13") {
			$('#tg_btn').click();
		}
	});

}