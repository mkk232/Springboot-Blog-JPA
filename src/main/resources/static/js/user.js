let index = {
    init: function () {
        $("#btn-save").on("click", () => { // function(){}, this를 바인딩하기 위해서 !
            this.save();
        });

        // $("#btn-login").on("click", () => { // function(){}, this를 바인딩하기 위해서 !
        //     this.login();
        // });

        $("#btn-update").on("click", () => { // function(){}, this를 바인딩하기 위해서 !
            this.update();
        });
    },

    save: function () {
        let data = { 
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        // console.log(data);

        // ajax 호출 시 default가 비동기 호출
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다..
        $.ajax({
            // 회원가입 수행 요청
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터의 mime 타입
            dataType: "json" // json으로 응답을 받을 때 json -> javascript 오브젝트로 변경해서 받겠다.
        }).done(function(resp){
			console.log(resp);
			if(resp.tatus === 500) {
				alert('회원가입에 실패하였습니다.');
			} else{
	            alert("회원가입이 완료 되었습니다.");
			}
            //console.log(resp);
            location.href="/";
        }).fail(function(error) {
			console.log(error);
			console.log(JSON.stringify(error));
            alert('회원가입에 실패하였습니다.!!');
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    },
    /*
    login: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        }

        $.ajax({
            // 회원가입 수행 요청
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터의 mime 타입
            dataType: "json" // json으로 응답을 받을 때 json -> javascript 오브젝트로 변경해서 받겠다.
        }).done(function(resp){
            alert("로그인이 완료 되었습니다.");
            console.log(resp);
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    }

     */

    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("회원수정이 완료 되었습니다.");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();