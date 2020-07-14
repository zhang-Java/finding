window.onload=function(){

    var message=[];

    function init(){
        var vm = new Vue({
            el: "#m_detail",
            data: {
                movie:[]
            }
        });

        $.ajax({
            url: 'http://localhost:8181/find/getMovie?mId='+getMId(),
            method: 'POST',
            dataType:'json',
            contentType: "application/json; charset=utf-8",
            success: function(res) {
                vm.movie=res;
                message = vm.movie.message.split('/');
                console.log(res);
                showMessage();
            }
        });
    }

    function getMId() {

        var url_keywords = window.location.href;
        var mId = decodeURI(url_keywords.split('?')[1].split('=')[1]);
        return mId;

    }

    function showMessage(){

        var s='<div>精选评论：</div>';
        for (var i = 0; i < message.length; i++) {

            s+= '<div class="message">'+
                message[i]+
                '</div>'

        }
        $('#messages').html(s);

    }

    init();
	
}